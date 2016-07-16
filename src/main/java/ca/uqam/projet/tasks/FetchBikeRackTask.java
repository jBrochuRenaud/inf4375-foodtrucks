package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.*;

import java.sql.*;

import ca.uqam.projet.resources.*;
import ca.uqam.projet.repositories.*;

import java.util.*;
import java.util.stream.*;
import java.io.InputStreamReader;
import java.net.URL;

import com.fasterxml.jackson.annotation.*;
import org.slf4j.*;

import com.csvreader.CsvReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.*;

@Component
public class FetchBikeRackTask {

  private static final Logger log = LoggerFactory.getLogger(FetchBikeRackTask.class);
  private static final String URL = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";

  @Autowired private BikeRackRepository repository;

  @Scheduled(cron="0 0 0 1 * ?")
  public void execute() {

    try {
      
      CsvReader csv = new CsvReader(new InputStreamReader(new URL(URL).openStream()));
      csv.readHeaders();

      while (csv.readRecord())
      {
        repository.insert(new BikeRack(Integer.parseInt(csv.get("INV_ID")), csv.get("INV_NO"), csv.get("MARQ"), (csv.get("DATE_INSPECTION") != "") ? java.sql.Date.valueOf(csv.get("DATE_INSPECTION")) : null, csv.get("PARC"), Double.parseDouble(csv.get("LAT")), Double.parseDouble(csv.get("LONG"))));
        log.info(csv.toString());
      }

      csv.close();
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

