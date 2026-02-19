package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import com.example.demo.Event; 
import com.example.demo.EventRepository; 

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @Autowired
    private EventRepository eventRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Starting Pixie Scraper ---");
        scraperService.scrapeEvents();
        
        List<Event> allEvents = eventRepository.findAll();
        if (!allEvents.isEmpty()) {
            System.out.println("Syncing " + allEvents.size() + " events to Sheets...");
            googleSheetsService.updateSheet(allEvents);
        }
    }
}