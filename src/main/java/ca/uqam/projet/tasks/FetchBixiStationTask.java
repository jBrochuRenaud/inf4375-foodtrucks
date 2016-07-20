package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.*;

import java.sql.*;
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
public class FetchBixiStationTask {

  private static final Logger log = LoggerFactory.getLogger(FetchBixiStationTask.class);
  private static final String URL = "https://secure.bixi.com/data/stations.json";

  @Autowired private BixiStationRepository repository;

  @Scheduled(cron="0 0/10 * * * ?")
  public void execute() {
    Arrays.asList(new RestTemplate().getForObject(URL, Stations.class).stations).stream()
      .map(this::asBixiStation)
      .peek(c -> log.info(c.toString()))
      .forEach(repository::insert)
      ;
  }

  private BixiStation asBixiStation(Station s) {
    return new BixiStation(s.id
        , s.stationName
        , s.terminalName
        , s.latitude
        , s.longitude
        , s.availableBikes
        , s.emptyDocks
        , new java.sql.Timestamp(s.lastUpdate)
    );
  }
}

class Stations {
  @JsonProperty("stations") Station[] stations;
}

class Station {
  @JsonProperty("id") int id;
  @JsonProperty("s") String stationName;
  @JsonProperty("n") int terminalName;
  @JsonProperty("st") int stationStatus;
  @JsonProperty("b") boolean blocked;
  @JsonProperty("su") boolean suspended;
  @JsonProperty("m") boolean outOfService;
  @JsonProperty("lu") long lastUpdate;
  @JsonProperty("lc") long lastConnection;
  @JsonProperty("la") double latitude;
  @JsonProperty("lo") double longitude;
  @JsonProperty("da") int availableDocks;
  @JsonProperty("dx") int emptyDocks;
  @JsonProperty("ba") int availableBikes;
  @JsonProperty("bx") int emptyBikes;
}
