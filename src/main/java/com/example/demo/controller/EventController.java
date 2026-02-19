package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.Event;
import com.example.demo.EventRepository;
import com.example.demo.ScraperService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Fixes "Loading..." issue
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ScraperService scraperService;

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("city", scraperService.getManualCity());
        return status;
    }

    @PostMapping("/update-location")
    public Map<String, String> updateLocation(@RequestParam String city) {
        scraperService.updateLocation(city);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Location updated to " + city);
        return response;
    }
}