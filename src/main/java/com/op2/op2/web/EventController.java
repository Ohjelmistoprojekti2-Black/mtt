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
import com.op2.op2.domain.EventRepository;
// import org.springframework.web.bind.annotation.RequestParam;
import com.op2.op2.domain.LocationRepository;
import jakarta.validation.Valid;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private LocationRepository locationRepository;

    // List all Events
    @RequestMapping(path = "/eventlist", method = RequestMethod.GET)
    public String eventList(Model model) {
        List<Event> events = (List<Event>) eventRepository.findAll();
        model.addAttribute("events", events);
        return "eventlist";
    }

    // Add event
    @GetMapping("/addEvent")
    public String addEvent(Model model) {
        Event newEvent = new Event();
        model.addAttribute("newEvent", newEvent);
        model.addAttribute("newevent", new Event());
        model.addAttribute("locations", locationRepository.findAll());
        return "addevent";
    }

    // Edit event
    @GetMapping("/editEvent/{id}")
    public String editEvent(@PathVariable("id") Long eventId, Model model) {
        model.addAttribute("editEvent", eventRepository.findById(eventId));
        model.addAttribute("locations", locationRepository.findAll());
        return "editevent";
    }

    // Save event
    @PostMapping("/saveEvent")
    public String saveEvent(@Valid @ModelAttribute("newevent") Event event, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("locations", locationRepository.findAll());
            return "addevent";
        } else {
            eventRepository.save(event);
            return "redirect:eventlist";
        }
    }

    @RequestMapping(value = "/delete/{eventId}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        eventRepository.deleteById(eventId);
        return "redirect:/eventlist";
    }

}
