package com.example.demo;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleConfig {
    @Bean
    public Sheets sheetsService() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials;
        String envJson = System.getenv("GOOGLE_CREDENTIALS"); // Server variable

        if (envJson != null && !envJson.isEmpty()) {
            credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(envJson.getBytes(StandardCharsets.UTF_8)))
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        } else {
            // Local development ke liye
            InputStream in = GoogleConfig.class.getResourceAsStream("/credentials.json");
            credentials = GoogleCredentials.fromStream(in).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        }
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName("Pixie Event Scraper").build();
    }
}