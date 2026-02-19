This project is a production-ready Full-Stack system designed to scrape, store, and analyze Events (specifically from Jaipur). It features automated data deduplication, real-time Google Sheets synchronization, and a live web dashboard.

Architecture Overview
Backend: Developed using Java 21 with Spring Boot framework.

Database: MySQL (hosted on Aiven) for persistent storage and metadata management.

External Sync: Google Sheets API integration for automated data backup.

Deployment: Fully containerized using Docker and hosted on Render.

Key Features & Logic
Scraping Strategy: Utilizes Jsoup for high-speed parsing of event platforms to extract titles, dates, locations, and descriptions.

Deduplication Logic: Implemented a "check-before-insert" strategy using unique event keys in the database to ensure no duplicate records are stored or synced.

Google Sheets Sync: A background service triggers every time new data is scraped, appending unique entries to the linked Google Sheet via the Google Sheets Java API.

Deployment Workflow: The application is packaged into a JAR file using Maven and deployed via a Docker container on Render to ensure environment consistency (Java 21).
