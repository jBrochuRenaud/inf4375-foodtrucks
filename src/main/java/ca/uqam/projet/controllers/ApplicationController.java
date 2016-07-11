package ca.uqam.projet.controllers;

import java.util.*;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicationController {

  @Autowired FoodTruckRepository repository;

  @RequestMapping("/")
  public String index(Model model) {
    //model.addAttribute("foodtrucks", repository.findAll());
    return "index";
  }
}
