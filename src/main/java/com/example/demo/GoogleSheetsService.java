package com.example.demo;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GoogleSheetsService {

    @Autowired
    private Sheets sheetsService;

    
    private final String SPREADSHEET_ID = "18SKzL9bPAiOQyiFwwshtv93ekTjp9h2CfyoTfwA3VSA";

    public void updateSheet(List<Event> events) {
        try {
            List<List<Object>> values = new ArrayList<>();
            values.add(Arrays.asList("Event Title", "Venue", "Event URL")); // Header

            for (Event event : events) {
                values.add(Arrays.asList(event.getTitle(), event.getVenue(), event.getEventUrl()));
            }

            ValueRange body = new ValueRange().setValues(values);
            sheetsService.spreadsheets().values()
                    .update(SPREADSHEET_ID, "Sheet1!A1", body)
                    .setValueInputOption("RAW")
                    .execute();
            
            System.out.println("🚀 Successfully synced " + events.size() + " events to NEW Sheet!");
        } catch (Exception e) {
            System.err.println("❌ Sync Error: " + e.getMessage());
        }
    }
}