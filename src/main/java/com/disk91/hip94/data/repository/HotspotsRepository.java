package com.disk91.hip94.data.repository;

import com.disk91.hip94.data.object.Hotspot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotsRepository extends MongoRepository<Hotspot, String> {

    public Hotspot findOneHotspotByHotspotId(String hsId);

    @Query("{ 'mongoPosition' : { '$near' : { '$geometry' : { 'type' : 'Point', 'coordinates' : [?0, ?1] }, '$maxDistance' : ?2 } } }")
    public List<Hotspot> findMongoPositionByNearbyDistance(double longitude, double latitude, int distanceInMeters);

    @Query( value = "{ 'mongoPosition' : { '$near' : { '$geometry' : { 'type' : 'Point', 'coordinates' : [?0, ?1] }, '$maxDistance' : ?2 } } }", count = true)
    public Long countMongoPositionNearbyDistance(double longitude, double latitude, int distanceInMeters);

    // To use the geo index, we can't use $box but we need to use the polygon representationo in $geometry
    // the box is a closed polygon requiring 5 points
    @Query("{ 'mongoPosition' : { '$geoWithin' : { '$geometry' : {" +
        " type: \"Polygon\", " +
        " coordinates: " +
        " [ [" +
        "    [?0, ?1], [?0, ?3], [?2, ?3], [?2, ?1], [?0, ?1]" +
        " ] ]" +
        "} } }")
    public List<Hotspot> findByMongoPositionNearbyBox(double bottomLeftLongitude, double bottomLeftLatitude, double topRightLongitude, double topRightLatitude);

    @Query("{ $text : { $search : ?0 } }")
    public Slice<Hotspot> findHotspotByAnimalNameLike(String search, Pageable p);

    // Regex search will do a full scan, better to reduce the scope of the regex with a  $text search to prefilter
    @Query("{ $text : { $search : ?0 }, animalName : { $regex : '^?0' } }")
    public Slice<Hotspot> findHotspotByAnimalNameLikeStarts(String search, Pageable p);

    @Query("{ animalName : { $regex : '^?0' } }")
    public Slice<Hotspot> findHotspotByAnimalNameStarts(String search, Pageable p);


}
