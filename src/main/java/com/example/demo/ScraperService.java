package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScraperService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GoogleSheetsService googleSheetsService;

    
    private String manualCity = "jaipur"; 

  
    @Scheduled(cron = "0 0 * * * *") 
    public void scheduledTask() {
        System.out.println("🚀 Auto-sync started for: " + manualCity);
        scrapeEvents();
        syncToSheets();
    }

    
    public void updateLocation(String newCity) {
        this.manualCity = newCity.toLowerCase().trim();
        System.out.println("📍 Location updated to: " + manualCity);
        
       
        eventRepository.deleteAll(); 
        
        
        scrapeEvents();
        syncToSheets();
    }

    public void scrapeEvents() {
        try {
            String url = "https://allevents.in/" + manualCity + "/all";
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Elements eventCards = doc.select("li.event-card, li.event-item");

            for (Element card : eventCards) {
                String title = card.select("h3, .title").text();
                String eventUrl = card.attr("data-url");
                if(eventUrl.isEmpty()) eventUrl = card.select("a").attr("href");
                String venue = card.select(".location").text();

                // Deduplication logic using Event URL
                if (!eventUrl.isEmpty() && !eventRepository.existsByEventUrl(eventUrl)) {
                    Event event = new Event();
                    event.setTitle(title);
                    event.setEventUrl(eventUrl);
                    event.setVenue(venue.isEmpty() ? manualCity : venue);
                    eventRepository.save(event);
                }
            }
            System.out.println("✅ Scraping Task Finished for " + manualCity + " ---");
        } catch (Exception e) {
            System.err.println("❌ Scraper Error: " + e.getMessage());
        }
    }

    public void syncToSheets() {
        List<Event> allEvents = eventRepository.findAll();
        if (!allEvents.isEmpty()) {
           
            googleSheetsService.updateSheet(allEvents); 
            System.out.println("🚀 Successfully synced " + allEvents.size() + " events to Google Sheets!");
        }
    }

    public String getManualCity() { 
        return manualCity; 
    }
}