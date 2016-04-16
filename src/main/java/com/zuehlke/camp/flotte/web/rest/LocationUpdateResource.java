package com.zuehlke.camp.flotte.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zuehlke.camp.flotte.domain.LocationUpdate;
import com.zuehlke.camp.flotte.repository.LocationUpdateRepository;
import com.zuehlke.camp.flotte.web.rest.util.HeaderUtil;
import com.zuehlke.camp.flotte.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LocationUpdate.
 */
@RestController
@RequestMapping("/api")
public class LocationUpdateResource {

    private final Logger log = LoggerFactory.getLogger(LocationUpdateResource.class);
        
    @Inject
    private LocationUpdateRepository locationUpdateRepository;
    
    /**
     * POST  /location-updates : Create a new locationUpdate.
     *
     * @param locationUpdate the locationUpdate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationUpdate, or with status 400 (Bad Request) if the locationUpdate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/location-updates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocationUpdate> createLocationUpdate(@Valid @RequestBody LocationUpdate locationUpdate) throws URISyntaxException {
        log.debug("REST request to save LocationUpdate : {}", locationUpdate);
        if (locationUpdate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("locationUpdate", "idexists", "A new locationUpdate cannot already have an ID")).body(null);
        }
        LocationUpdate result = locationUpdateRepository.save(locationUpdate);
        return ResponseEntity.created(new URI("/api/location-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("locationUpdate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /location-updates : Updates an existing locationUpdate.
     *
     * @param locationUpdate the locationUpdate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationUpdate,
     * or with status 400 (Bad Request) if the locationUpdate is not valid,
     * or with status 500 (Internal Server Error) if the locationUpdate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/location-updates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocationUpdate> updateLocationUpdate(@Valid @RequestBody LocationUpdate locationUpdate) throws URISyntaxException {
        log.debug("REST request to update LocationUpdate : {}", locationUpdate);
        if (locationUpdate.getId() == null) {
            return createLocationUpdate(locationUpdate);
        }
        LocationUpdate result = locationUpdateRepository.save(locationUpdate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("locationUpdate", locationUpdate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /location-updates : get all the locationUpdates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of locationUpdates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/location-updates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LocationUpdate>> getAllLocationUpdates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LocationUpdates");
        Page<LocationUpdate> page = locationUpdateRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/location-updates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /location-updates/:id : get the "id" locationUpdate.
     *
     * @param id the id of the locationUpdate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationUpdate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/location-updates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocationUpdate> getLocationUpdate(@PathVariable String id) {
        log.debug("REST request to get LocationUpdate : {}", id);
        LocationUpdate locationUpdate = locationUpdateRepository.findOne(id);
        return Optional.ofNullable(locationUpdate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /location-updates/:id : delete the "id" locationUpdate.
     *
     * @param id the id of the locationUpdate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/location-updates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLocationUpdate(@PathVariable String id) {
        log.debug("REST request to delete LocationUpdate : {}", id);
        locationUpdateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("locationUpdate", id.toString())).build();
    }

}
