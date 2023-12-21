package com.java.springboot.controllers;

import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ViewController {

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value="/starter")
    public ModelAndView samplePage() {
        return new ModelAndView("starter-template");
    }

    @GetMapping(value="/starter-blank")
    public ModelAndView samplePageBlank() {
        return new ModelAndView("starter-template-empty");
    }


    @GetMapping(value="/countries")
    public ModelAndView showCities() {
        var countries = countryService.getCountries();
        var params = new HashMap<String, Object>();
        params.put("countries", countries);
        return new ModelAndView("countries", params);
    }

    @GetMapping(value="/users")
    public ModelAndView showUsers() {
        var users = userService.getUsers();
        var params = new HashMap<String, Object>();
        params.put("users", users);
        return new ModelAndView("users", params);
    }

}
