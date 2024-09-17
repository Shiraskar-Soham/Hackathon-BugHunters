package com.plugin.hackathon;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.plugin.hackathon.CodeFormatter.formatCode;

public class AIModel {

    private static final String API_URL = "http://192.168.103.130:8000/generate_code";

    public AIModel() {
        // Load model (e.g., from a file, cloud service, etc.)
    }

    public String generateSnippet(String input) {
        try {

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{\"input\": \"" + input + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] inputBytes = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(inputBytes, 0, inputBytes.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
                String responseBody = scanner.useDelimiter("\\A").next();
                scanner.close();
                return formatCode(responseBody);
            } else {
                return "Error: Received HTTP " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String refactorAndCorrectOutput(String input) {
        StringBuilder correctedOutput = new StringBuilder();
        String[] lines = input.split("\\n");
        boolean insideMethod = false;
        int braceCount = 0;

        for (String line : lines) {
            String trimmedLine = line.trim();

            if (trimmedLine.isEmpty()
                    || trimmedLine.startsWith("You said:")
                    || trimmedLine.startsWith("System:")) {
                continue;
            }

            // Handle lines that start with "Human:"
            if (trimmedLine.startsWith("Human:")) {
                trimmedLine = trimmedLine.substring("Human:".length()).trim();
                line = trimmedLine; // Update the line to be processed

                // Check if it's a method signature missing access modifier and return type
                if (isMethodSignature(line) && !line.matches(".\\b(public|private|protected)\\b.")) {
                    // Insert 'public void' at the start
                    line = "public void " + line;
                }
            }

            // Check if the line is a method signature
            if (isMethodSignature(line)) {
                insideMethod = true;
                braceCount = 0; // Initialize brace count

                correctedOutput.append(line).append("\n");

                // Count braces in the method signature line
                braceCount += countCharOccurrences(line, '{');
                braceCount -= countCharOccurrences(line, '}');
                continue;
            }

            if (insideMethod) {
                correctedOutput.append(line).append("\n");

                // Update brace count
                braceCount += countCharOccurrences(line, '{');
                braceCount -= countCharOccurrences(line, '}');

                // Check if we've reached the end of the method
                if (braceCount <= 0) {
                    insideMethod = false;
                    correctedOutput.append("\n");
                }
            }
        }

        return correctedOutput.toString();
    }

    private static boolean isMethodSignature(String line) {
        String trimmedLine = line.trim();
        // Regex to match method signatures
        String methodRegex = "^(public|private|protected)?\\s*(static\\s+)?\\s*(<.>\\s+)?\\w+(<.>)?\\s+(\\w+)\\s*\\(.\\)\\s(throws\\s+\\w+(\\s*,\\s*\\w+))?\\s\\{?\\s*$";
        return trimmedLine.matches(methodRegex);
    }

    private static int countCharOccurrences(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }
}
