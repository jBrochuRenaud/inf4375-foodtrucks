package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;
import java.sql.*;

public class FoodTruck {

  private int id;
  private Date date;
  private Timestamp startDate;
  private Timestamp endDate;
  private String location;
  private double latitude;
  private double longitude;
  private String truckName;
  private String truckId;

  public FoodTruck(Date date, Timestamp startDate, Timestamp endDate, String location, double latitude, double longitude, String truckName, String truckId) {
    this.date = date;
    this.startDate = startDate;
    this.endDate = endDate;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.truckName = truckName;
    this.truckId = truckId;
  }

  public FoodTruck(int id, Date date, Timestamp startDate, Timestamp endDate, String location, double latitude, double longitude, String truckName, String truckId) {
    this.id = id;
    this.date = date;
    this.startDate = startDate;
    this.endDate = endDate;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.truckName = truckName;
    this.truckId = truckId;
  }

  @JsonProperty public int getId() { return id; }
  @JsonProperty public Date getDate() { return date; }
  @JsonProperty public Timestamp getStartDate() { return startDate; }
  @JsonProperty public Timestamp getEndDate() { return endDate; }
  @JsonProperty public String getLocation() { return location; }
  @JsonProperty public double getLatitude() { return latitude; }
  @JsonProperty public double getLongitude() { return longitude; }
  @JsonProperty public String getTruckName() { return truckName; }
  @JsonProperty public String getTruckId() { return truckId; }
}
