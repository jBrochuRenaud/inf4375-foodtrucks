package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;
import java.sql.*;

public class BixiStation {

  private int id;
  private String stationName;
  private int terminalName;
  private double latitude;
  private double longitude;
  private int availableBikes;
  private int emptyDocks;
  private Timestamp lastUpdateTime;

  public BixiStation(int id, String stationName, int terminalName, double latitude, double longitude, int availableBikes, int emptyDocks, Timestamp lastUpdateTime) {
    this.id = id;
    this.stationName = stationName;
    this.terminalName = terminalName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.availableBikes = availableBikes;
    this.emptyDocks = emptyDocks;
    this.lastUpdateTime = lastUpdateTime;
  }

  @JsonProperty public int getId() { return id; }
  @JsonProperty public String getStationName() { return stationName; }
  @JsonProperty public int getTerminalName() { return terminalName; }
  @JsonProperty public double getLatitude() { return latitude; }
  @JsonProperty public double getLongitude() { return longitude; }
  @JsonProperty public int getAvailableBikes() { return availableBikes; }
  @JsonProperty public int getEmptyDocks() { return emptyDocks; }
  @JsonProperty public Timestamp getLastUpdateTime() { return lastUpdateTime; }
}