package com.op2.op2.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.op2.op2.domain.Event;
import com.op2.op2.domain.Location;
import com.op2.op2.domain.LocationRepository;
import jakarta.validation.Valid;

@Controller
public class LocationController {
    
    @Autowired
    private LocationRepository locationRepository;

    //List all locations
    @RequestMapping(value = "/locationlist", method = RequestMethod.GET)
    public String locationList(Model model) {
        List<Location> locations = (List<Location>) locationRepository.findAll();
        model.addAttribute("locations", locations);
        return "locationlist";
    }

    //Add new location
    @GetMapping("/addLocation")
    public String addLocation(Model model) {
        model.addAttribute("newlocation", new Location());
        return "addlocation";
    }

    //Save location
    @PostMapping("/saveLocation")
    public String saveLocation(@Valid @ModelAttribute("newlocation") Location location, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "addlocation";
        } else {
            locationRepository.save(location);
            return "redirect:locationlist";
        }
    }

    //Delete location
    @RequestMapping(value = "/deleteLocation/{locationId}", method = RequestMethod.GET)
    public String deleteLocation(@PathVariable("locationId") Long locationId) {
        var location = locationRepository.findById(locationId).get();
        var events = location.getEvents();
        for (Event event : events) {
            event.setLocation(null);
        }
        locationRepository.save(location);
        locationRepository.deleteById(locationId);
        return "redirect:/locationlist";
    }

}
