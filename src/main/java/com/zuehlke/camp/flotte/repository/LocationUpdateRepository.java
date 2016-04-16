package com.zuehlke.camp.flotte.repository;

import com.zuehlke.camp.flotte.domain.LocationUpdate;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the LocationUpdate entity.
 */
public interface LocationUpdateRepository extends MongoRepository<LocationUpdate,String> {

}
