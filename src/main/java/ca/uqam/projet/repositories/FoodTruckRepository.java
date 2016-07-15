package ca.uqam.projet.repositories;

import java.util.*;
import java.sql.*;

import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;

@Component
public class FoodTruckRepository {

  @Autowired private JdbcTemplate jdbcTemplate;

  private static final String FIND_ALL_STMT =
      " select"
    + "     id"
    + "   , date"
    + "   , startDate"
    + "   , endDate"
    + "   , location"
    + "   , latitude"
    + "   , longitude"
    + "   , truckName"
    + "   , truckId"
    + " from"
    + "   foodtrucks"
    ;

  public List<FoodTruck> findAll() {
    return jdbcTemplate.query(FIND_ALL_STMT, new FoodTruckRowMapper());
  }

    private static final String FIND_ALL_BY_DATE_INTERVAL_STMT =
      " select"
    + "     id"
    + "   , date"
    + "   , startDate"
    + "   , endDate"
    + "   , location"
    + "   , latitude"
    + "   , longitude"
    + "   , truckName"
    + "   , truckId"
    + " from"
    + "   foodtrucks"
    + " where"
    + "   date >= ? and date <= ?"
    ;

  public List<FoodTruck> findAllByDateInterval(java.sql.Date startDate, java.sql.Date endDate) {
    return jdbcTemplate.query(FIND_ALL_BY_DATE_INTERVAL_STMT, new Object[]{startDate, endDate}, new FoodTruckRowMapper());
  }

  private static final String FIND_BY_ID_STMT =
      " select"
    + "     id"
    + "   , date"
    + "   , startDate"
    + "   , endDate"
    + "   , location"
    + "   , latitude"
    + "   , longitude"
    + "   , truckName"
    + "   , truckId"
    + " from"
    + "   foodtrucks"
    + " where"
    + "   id = ?"
    ;

  public FoodTruck findById(int id) {
    return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new FoodTruckRowMapper());
  }

  private static final String INSERT_STMT =
      " insert into foodtrucks (date, startDate, endDate, location, latitude, longitude, truckName, truckId)"
    + " values (?, ?, ?, ?, ?, ?, ?, ?)"
    + " on conflict do nothing"
    ;

  public int insert(FoodTruck foodtruck) {
    return jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
      ps.setDate(1, new java.sql.Date(foodtruck.getDate().getTime()));
      ps.setTimestamp(2, foodtruck.getStartDate());
      ps.setTimestamp(3, foodtruck.getEndDate());
      ps.setString(4, foodtruck.getLocation());
      ps.setDouble(5, foodtruck.getLatitude());
      ps.setDouble(6, foodtruck.getLongitude());
      ps.setString(7, foodtruck.getTruckName());
      ps.setString(8, foodtruck.getTruckId());
      return ps;
    });
  }
}

class FoodTruckRowMapper implements RowMapper<FoodTruck> {
  public FoodTruck mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new FoodTruck(rs.getInt("id")
      , rs.getDate("date")
      , rs.getTimestamp("startDate")
      , rs.getTimestamp("endDate")
      , rs.getString("location")
      , rs.getDouble("latitude")
      , rs.getDouble("longitude")
      , rs.getString("truckName")
      , rs.getString("truckId")
    );
  }
}
