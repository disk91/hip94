package com.disk91.hip94.data.repository;

import com.disk91.hip94.data.object.Hotspot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotspotsRepository extends MongoRepository<Hotspot, String> {

    public Hotspot findOneHotspotByHotspotId(String hsId);

}
