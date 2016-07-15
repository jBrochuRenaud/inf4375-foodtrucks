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
public class BixiStationRepository {

  @Autowired private JdbcTemplate jdbcTemplate;

  private static final String FIND_ALL_STMT =
      " select"
    + "     id"
    + "   , stationName"
    + "   , terminalName"
    + "   , latitude"
    + "   , longitude"
    + "   , availableBikes"
    + "   , emptyDocks"
    + "   , lastUpdateTime"
    + " from"
    + "   bixistations"
    ;

  public List<BixiStation> findAll() {
    return jdbcTemplate.query(FIND_ALL_STMT, new BixiStationRowMapper());
  }

  private static final String FIND_BY_ID_STMT =
      " select"
    + "     id"
    + "   , stationName"
    + "   , terminalName"
    + "   , latitude"
    + "   , longitude"
    + "   , availableBikes"
    + "   , emptyDocks"
    + "   , lastUpdateTime"
    + " from"
    + "   bixistations"
    + " where"
    + "   id = ?"
    ;

  public BixiStation findById(int id) {
    return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new BixiStationRowMapper());
  }

  private static final String INSERT_STMT =
      " insert into bixistations (id, stationName, terminalName, latitude, longitude, availableBikes, emptyDocks, lastUpdateTime)"
    + " values (?, ?, ?, ?, ?, ?, ?, ?)"
    + " on conflict do nothing"
    ;

  public int insert(BixiStation bixiStation) {
    return jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
      ps.setInt(1, bixiStation.getId());
      ps.setString(2, bixiStation.getStationName());
      ps.setInt(3, bixiStation.getTerminalName());
      ps.setDouble(4, bixiStation.getLatitude());
      ps.setDouble(5, bixiStation.getLongitude());
      ps.setInt(6, bixiStation.getAvailableBikes());
      ps.setInt(7, bixiStation.getEmptyDocks());
      ps.setTimestamp(8, bixiStation.getLastUpdateTime());
      return ps;
    });
  }
}

class BixiStationRowMapper implements RowMapper<BixiStation> {
  public BixiStation mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new BixiStation(rs.getInt("id")
      , rs.getString("stationName")
      , rs.getInt("terminalName")
      , rs.getDouble("latitude")
      , rs.getDouble("longitude")
      , rs.getInt("availableBikes")
      , rs.getInt("emptyDocks")
      , rs.getTimestamp("lastUpdateTime")
    );
  }
}
