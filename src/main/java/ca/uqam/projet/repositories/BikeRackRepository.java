package ca.uqam.projet.repositories;

import java.sql.*;
import java.util.*;

import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;

@Component
public class BikeRackRepository {

  @Autowired private JdbcTemplate jdbcTemplate;

  private static final String FIND_ALL_STMT =
      " select"
    + "     id"
    + "   , inventoryId"
    + "   , brand"
    + "   , lastInspection"
    + "   , park"
    + "   , latitude"
    + "   , longitude"
    + " from"
    + "   bikeracks"
    ;

  public List<BikeRack> findAll() {
    return jdbcTemplate.query(FIND_ALL_STMT, new BikeRackRowMapper());
  }

  private static final String FIND_BY_ID_STMT =
      " select"
    + "     id"
    + "   , inventoryId"
    + "   , brand"
    + "   , lastInspection"
    + "   , park"
    + "   , latitude"
    + "   , longitude"
    + " from"
    + "   bikeracks"
    + " where"
    + "   id = ?"
    ;

  public BikeRack findById(int id) {
    return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new BikeRackRowMapper());
  }

  private static final String INSERT_STMT =
      " insert into bikeracks (id, inventoryId, brand, lastInspection, park, latitude, longitude)"
    + " values (?, ?, ?, ?, ?, ?, ?)"
    + " on conflict do nothing"
    ;

  public int insert(BikeRack bikeRack) {
    return jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
      ps.setInt(1, bikeRack.getId());
      ps.setString(2, bikeRack.getInventoryId());
      ps.setString(3, bikeRack.getBrand());
      ps.setDate(4, bikeRack.getLastInspection());
      ps.setString(5, bikeRack.getPark());
      ps.setDouble(6, bikeRack.getLatitude());
      ps.setDouble(7, bikeRack.getLongitude());
      return ps;
    });
  }
}

class BikeRackRowMapper implements RowMapper<BikeRack> {
  public BikeRack mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new BikeRack(rs.getInt("id")
      , rs.getString("inventoryId")
      , rs.getString("brand")
      , rs.getDate("lastInspection")
      , rs.getString("park")
      , rs.getDouble("latitude")
      , rs.getDouble("longitude")
    );
  }
}
