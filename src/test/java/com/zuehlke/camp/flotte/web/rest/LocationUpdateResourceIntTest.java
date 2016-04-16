package com.zuehlke.camp.flotte.web.rest;

import com.zuehlke.camp.flotte.FlotteApp;
import com.zuehlke.camp.flotte.domain.LocationUpdate;
import com.zuehlke.camp.flotte.repository.LocationUpdateRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LocationUpdateResource REST controller.
 *
 * @see LocationUpdateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FlotteApp.class)
@WebAppConfiguration
@IntegrationTest
public class LocationUpdateResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_SOURCE = "AAAAA";
    private static final String UPDATED_SOURCE = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.format(DEFAULT_TIME);

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATTITUDE = 1D;
    private static final Double UPDATED_LATTITUDE = 2D;
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private LocationUpdateRepository locationUpdateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLocationUpdateMockMvc;

    private LocationUpdate locationUpdate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocationUpdateResource locationUpdateResource = new LocationUpdateResource();
        ReflectionTestUtils.setField(locationUpdateResource, "locationUpdateRepository", locationUpdateRepository);
        this.restLocationUpdateMockMvc = MockMvcBuilders.standaloneSetup(locationUpdateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        locationUpdateRepository.deleteAll();
        locationUpdate = new LocationUpdate();
        locationUpdate.setSource(DEFAULT_SOURCE);
        locationUpdate.setTime(DEFAULT_TIME);
        locationUpdate.setLongitude(DEFAULT_LONGITUDE);
        locationUpdate.setLattitude(DEFAULT_LATTITUDE);
        locationUpdate.setComment(DEFAULT_COMMENT);
    }

    @Test
    public void createLocationUpdate() throws Exception {
        int databaseSizeBeforeCreate = locationUpdateRepository.findAll().size();

        // Create the LocationUpdate

        restLocationUpdateMockMvc.perform(post("/api/location-updates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locationUpdate)))
                .andExpect(status().isCreated());

        // Validate the LocationUpdate in the database
        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeCreate + 1);
        LocationUpdate testLocationUpdate = locationUpdates.get(locationUpdates.size() - 1);
        assertThat(testLocationUpdate.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLocationUpdate.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testLocationUpdate.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocationUpdate.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
        assertThat(testLocationUpdate.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationUpdateRepository.findAll().size();
        // set the field null
        locationUpdate.setSource(null);

        // Create the LocationUpdate, which fails.

        restLocationUpdateMockMvc.perform(post("/api/location-updates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locationUpdate)))
                .andExpect(status().isBadRequest());

        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationUpdateRepository.findAll().size();
        // set the field null
        locationUpdate.setTime(null);

        // Create the LocationUpdate, which fails.

        restLocationUpdateMockMvc.perform(post("/api/location-updates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locationUpdate)))
                .andExpect(status().isBadRequest());

        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationUpdateRepository.findAll().size();
        // set the field null
        locationUpdate.setLongitude(null);

        // Create the LocationUpdate, which fails.

        restLocationUpdateMockMvc.perform(post("/api/location-updates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locationUpdate)))
                .andExpect(status().isBadRequest());

        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLattitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationUpdateRepository.findAll().size();
        // set the field null
        locationUpdate.setLattitude(null);

        // Create the LocationUpdate, which fails.

        restLocationUpdateMockMvc.perform(post("/api/location-updates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(locationUpdate)))
                .andExpect(status().isBadRequest());

        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLocationUpdates() throws Exception {
        // Initialize the database
        locationUpdateRepository.save(locationUpdate);

        // Get all the locationUpdates
        restLocationUpdateMockMvc.perform(get("/api/location-updates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(locationUpdate.getId())))
                .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].lattitude").value(hasItem(DEFAULT_LATTITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    public void getLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.save(locationUpdate);

        // Get the locationUpdate
        restLocationUpdateMockMvc.perform(get("/api/location-updates/{id}", locationUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(locationUpdate.getId()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.lattitude").value(DEFAULT_LATTITUDE.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    public void getNonExistingLocationUpdate() throws Exception {
        // Get the locationUpdate
        restLocationUpdateMockMvc.perform(get("/api/location-updates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.save(locationUpdate);
        int databaseSizeBeforeUpdate = locationUpdateRepository.findAll().size();

        // Update the locationUpdate
        LocationUpdate updatedLocationUpdate = new LocationUpdate();
        updatedLocationUpdate.setId(locationUpdate.getId());
        updatedLocationUpdate.setSource(UPDATED_SOURCE);
        updatedLocationUpdate.setTime(UPDATED_TIME);
        updatedLocationUpdate.setLongitude(UPDATED_LONGITUDE);
        updatedLocationUpdate.setLattitude(UPDATED_LATTITUDE);
        updatedLocationUpdate.setComment(UPDATED_COMMENT);

        restLocationUpdateMockMvc.perform(put("/api/location-updates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLocationUpdate)))
                .andExpect(status().isOk());

        // Validate the LocationUpdate in the database
        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeUpdate);
        LocationUpdate testLocationUpdate = locationUpdates.get(locationUpdates.size() - 1);
        assertThat(testLocationUpdate.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLocationUpdate.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testLocationUpdate.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocationUpdate.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testLocationUpdate.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    public void deleteLocationUpdate() throws Exception {
        // Initialize the database
        locationUpdateRepository.save(locationUpdate);
        int databaseSizeBeforeDelete = locationUpdateRepository.findAll().size();

        // Get the locationUpdate
        restLocationUpdateMockMvc.perform(delete("/api/location-updates/{id}", locationUpdate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LocationUpdate> locationUpdates = locationUpdateRepository.findAll();
        assertThat(locationUpdates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
