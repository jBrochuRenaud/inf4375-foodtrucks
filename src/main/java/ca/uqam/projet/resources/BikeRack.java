package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;
import java.sql.*;

public class BikeRack {

  private int id;
  private String inventoryId;
  private String brand;
  private Date lastInspection;
  private String park;
  private double latitude;
  private double longitude;


  public BikeRack(int id, String inventoryId, String brand, Date lastInspection, String park, double latitude, double longitude) {
      this.id = id;
      this.inventoryId = inventoryId;
      this.brand = brand;
      this.lastInspection = lastInspection;
      this.park = park;
      this.latitude = latitude;
      this.longitude = longitude;
  }

  @JsonProperty public int getId() { return id; }
  @JsonProperty public String getInventoryId() { return inventoryId; }
  @JsonProperty public String getBrand() { return brand; }
  @JsonProperty public Date getLastInspection() { return lastInspection; }
  @JsonProperty public String getPark() { return park; }
  @JsonProperty public double getLatitude() { return latitude; }
  @JsonProperty public double getLongitude() { return longitude; }

}