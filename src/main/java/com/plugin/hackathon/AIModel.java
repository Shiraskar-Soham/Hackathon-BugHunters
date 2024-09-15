package com.plugin.hackathon;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AIModel {

    private static final String API_URL = "http://127.0.0.1:8000/generate_code";

    public AIModel() {
        // Load model (e.g., from a file, cloud service, etc.)
    }

    public String generateSnippet(String input) {
        try {
            // Create URL object
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            conn.setRequestMethod("POST");

            // Set headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON payload
            String jsonInputString = "{\"input\": \"" + input + "\"}";

            // Write the JSON payload to the request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] inputBytes = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(inputBytes, 0, inputBytes.length);
            }

            // Read the response
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
                String responseBody = scanner.useDelimiter("\\A").next();
                scanner.close();
                return responseBody;
            } else {
                return "Error: Received HTTP " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
