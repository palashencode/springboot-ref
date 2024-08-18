package com.java.springboot.controllers;

import com.java.springboot.model.RowData;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import com.java.springboot.service.ViewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ViewController {

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Autowired
    ViewService viewService;

    @GetMapping(value = "/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = "/home")
    public String home(ModelMap model, HttpServletRequest request) {
        try {
            model.put("msg", "Home page of springboot-ref, ts="+ viewService.getTime());
            model.put("features", viewService.getFeatures());
        }catch(Exception e){
            model.put("errmsg", e.getMessage());
            return "error";
        }
        return "home";
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
