package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.sql.Date;

public class FoodTruck {

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private String id;

  private Date date;
  private String startHour;
  private String endHour;
  private String location;
  private double latitude;
  private double longitude;
  private String truckName;
  private String truckId;

  public FoodTruck(Date date, String startHour, String endHour, String location, double latitude, double longitude, String truckName, String truckId) {
    this.date = date;
    this.startHour = startHour;
    this.endHour = endHour;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.truckName = truckName;
    this.truckId = truckId;
  }

  @JsonProperty public String getId() { return id; }
  @JsonProperty public Date getDate() { return date; }
  @JsonProperty public String getStartHour() { return startHour; }
  @JsonProperty public String getEndHour() { return endHour; }
  @JsonProperty public String getLocation() { return location; }
  @JsonProperty public double getLatitude() { return latitude; }
  @JsonProperty public double getLongitude() { return longitude; }
  @JsonProperty public String getTruckName() { return truckName; }
  @JsonProperty public String getTruckId() { return truckId; }
}
