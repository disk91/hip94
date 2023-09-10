package com.disk91.hip94.data.repository;

import com.disk91.hip94.data.object.Hotspot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotsRepository extends MongoRepository<Hotspot, String> {

    public Hotspot findOneHotspotByHotspotId(String hsId);

    @Query("{ 'mongoPosition' : { '$near' : { '$geometry' : { 'type' : 'Point', 'coordinates' : [?0, ?1] }, '$maxDistance' : ?2 } } }")
    List<Hotspot> findMongoPositionByNearbyDistance(double longitude, double latitude, int distanceInMeters);

    @Query( value = "{ 'mongoPosition' : { '$near' : { '$geometry' : { 'type' : 'Point', 'coordinates' : [?0, ?1] }, '$maxDistance' : ?2 } } }", count=true)
    long countByMongoPositionNearbyDistance(double longitude, double latitude, int distanceInMeters);

    @Query("{ 'mongoPosition' : { '$geoWithin' : { '$box' : [ [?0, ?1], [?2, ?3] ] } } }")
    List<Hotspot> findByMongoPositionNearbyBox(double bottomLeftLongitude, double bottomLeftLatitude, double topRightLongitude, double topRightLatitude);

}
