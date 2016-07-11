package ca.uqam.projet.controllers;

import java.util.*;
import java.sql.Date;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodTruckController {

  @Autowired FoodTruckRepository repository;

  @RequestMapping("/foodtrucks")
  public List<FoodTruck> findAll() {
    return repository.findAll();
  }

  @RequestMapping("/foodtrucks/{id}")
  public FoodTruck findById(@PathVariable("id") int id) {
    return repository.findById(id);
  }

  @RequestMapping("/foodtrucks-schedule")
  public List<FoodTruck> findByDateInterval(@RequestParam String from, @RequestParam String to) {
  	java.sql.Date startDate, endDate;
  	try {
  		startDate = java.sql.Date.valueOf(from);
  		endDate = java.sql.Date.valueOf(to);
	} catch (IllegalArgumentException name) {
		return Collections.emptyList();
	}

    return repository.findAllByDateInterval(startDate, endDate);
  }

}
