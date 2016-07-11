package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.*;

import java.sql.Date;
import java.util.*;

import ca.uqam.projet.resources.*;
import ca.uqam.projet.repositories.*;

import java.util.*;
import java.util.stream.*;

import com.fasterxml.jackson.annotation.*;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.*;

@Component
public class FetchFoodTruckTask {

  private static final Logger log = LoggerFactory.getLogger(FetchFoodTruckTask.class);
  private static final String URL = "http://camionderue.com/donneesouvertes/geojson";

  @Autowired private FoodTruckRepository repository;

  @Scheduled(cron="* * 0,12 * * *")
  public void execute() {
    Arrays.asList(new RestTemplate().getForObject(URL, FoodTruckSchedule.class).features).stream()
      .map(this::asFoodTruck)
      .peek(c -> log.info(c.toString()))
      .forEach(repository::insert)
      ;
  }

  private FoodTruck asFoodTruck(Feature f) {
    return new FoodTruck(java.sql.Date.valueOf(f.featureProperties.date)
        , f.featureProperties.startHour
        , f.featureProperties.endHour
        , f.featureProperties.location
        , f.geometry.coordinates[1]
        , f.geometry.coordinates[0]
        , f.featureProperties.truckName
        , f.featureProperties.truckId);
  }
}

class FoodTruckSchedule {
  @JsonProperty("type") String type;
  @JsonProperty("features") Feature[] features;
}

class Feature {
  @JsonProperty("type") String type;
  @JsonProperty("geometry") Geometry geometry;
  @JsonProperty("properties") FeatureProperties featureProperties;
}

class FeatureProperties {
  @JsonProperty("name") String name;
  @JsonProperty("description") String description;
  @JsonProperty("Date") String date;
  @JsonProperty("Heure_debut") String startHour;
  @JsonProperty("Heure_fin") String endHour;
  @JsonProperty("Lieu") String location;
  @JsonProperty("Camion") String truckName;
  @JsonProperty("Truckid") String truckId;
}

class Geometry {
  @JsonProperty("type") String type;
  @JsonProperty("coordinates") double[] coordinates;
}
