package ca.uqam.projet.controllers;

import java.util.*;
import java.sql.Date;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class BixiStationController {

  @Autowired BixiStationRepository repository;

  @RequestMapping("/bixi-stations")
  public List<BixiStation> findAll() {
    return repository.findAll();
  }

  @RequestMapping("/bixi-stations/{id}")
  public BixiStation findById(@PathVariable("id") int id) {
    return repository.findById(id);
  }

}
