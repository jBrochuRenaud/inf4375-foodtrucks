package ca.uqam.projet.controllers;

import java.util.*;
import java.sql.Date;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class BikeRackController {

  @Autowired BikeRackRepository repository;

  @RequestMapping("/bike-racks")
  public List<BikeRack> findAll() {
    return repository.findAll();
  }

  @RequestMapping("/bike-racks/{id}")
  public BikeRack findById(@PathVariable("id") int id) {
    return repository.findById(id);
  }

}
