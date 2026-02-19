package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "events") 
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true, length = 500) 
    private String eventUrl;

    private String venue;
    private String eventDate;
    private String category;

   

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEventUrl() { return eventUrl; }
    public void setEventUrl(String eventUrl) { this.eventUrl = eventUrl; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}