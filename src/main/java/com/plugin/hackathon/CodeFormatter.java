package com.plugin.hackathon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.lang.Math.min;

public class CodeFormatter {

//    public static String formatCode(String responseBody) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(responseBody);
//
//            // If the response is an array, remove brackets
//            if (jsonNode.isArray()) {
//                String formattedResponse = jsonNode.toString();
//
//                // Removing square brackets from the start and end
//                formattedResponse = formattedResponse.substring(1, formattedResponse.length() - 1).trim();
//                return formatCodeToString(formattedResponse);
//            }
//
//            // If the response is not an array, return it as is
//            return jsonNode.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseBody;
//        }
//    }
//
//    public static String formatCodeToString(String response) {
//        // Remove '1234' and replace it with a newline
//        String cleanedCode = response.replaceAll("^\\[", "").replaceAll("\\]$", "");
//
//        // Remove any extra newlines or spaces
//        cleanedCode = cleanedCode.replaceAll("\\n{2,}", "\n\n").trim();
//
//        // Split the code into methods based on method declarations
//        String[] methods = cleanedCode.split("(?=\\bpublic static\\b)");
//
//        // Get only the last method
//        String result = "";
//        if (methods.length > 0) {
//            result = methods[methods.length - 1].trim();
//        }
//
//        return result;
//    }

//    public static String formatCode(String response) {
//        // Remove the leading and trailing square brackets and double quotes
//        if (response.startsWith("[\"") && response.endsWith("\"]")) {
//            response = response.substring(2, response.length() - 2);
//        }
//
//        // Unescape characters: replace \\n with \n, \\\\ with \\, and \\" with "
//        String cleanedCode = response.replace("\\\\", "\\");
//        cleanedCode = cleanedCode.replace("\\\"", "\"");
//        cleanedCode = cleanedCode.replace("\\n", "\n");
//
//        // Replace '1234' with a newline to separate code snippets
//        cleanedCode = cleanedCode.replace("1234", "\n");
//
//        // Remove any extra newlines or spaces
//        cleanedCode = cleanedCode.replaceAll("\\n{3,}", "\n\n").trim();
//
//        // Split the code into methods or declarations based on 'private static' or 'public static'
//        String[] methods = cleanedCode.split("(?=\\b(private|public)\\s+static\\b)");
//
//        // Get only the last method or declaration
//        String result = "";
//        if (methods.length > 0) {
//            result = methods[methods.length - 1].trim();
//        }
//
//        return result;
//    }

//    public static String formatCode(String response) {
//        if (response.startsWith("[\"") && response.endsWith("\"]")) {
//            response = response.substring(2, response.length() - 2);
//        }
//
//        String cleanedCode = response.replace("\\\\", "\\");
//        cleanedCode = cleanedCode.replace("\\\"", "\"");
//        cleanedCode = cleanedCode.replace("\\n", "\n");
//
//        cleanedCode = cleanedCode.replace("1234", "\n");
//
//        cleanedCode = cleanedCode.replaceAll("\\n{3,}", "\n\n").trim();
//
//        String[] methods = cleanedCode.split("(?=\\b(private|public)\\s+static\\b)");
//
//        StringBuilder result = new StringBuilder();
//        for (int i = methods.length-1 ; i > methods.length-4; i--) {
//            String method = methods[i].trim();
//            if (i == methods.length-1) {
//                result.append(method).append("\n\n");
//            } else {
//                result.append("/*\n").append(method).append("\n*/\n\n");
//            }
//        }
//
//        return result.toString().trim();
//    }

    public static String formatCode(String response) {
        if (response.startsWith("[\"") && response.endsWith("\"]")) {
            response = response.substring(2, response.length() - 2);
        }

        String cleanedCode = response.replace("\\\\", "\\");
        cleanedCode = cleanedCode.replace("\\\"", "\"");
        cleanedCode = cleanedCode.replace("\\n", "\n");

        cleanedCode = cleanedCode.replace("1234", "\n");

        cleanedCode = cleanedCode.replaceAll("\\n{3,}", "\n\n").trim();

        String[] methods = cleanedCode.split("(?=\\b(private|public)\\s+static\\b)");

        StringBuilder result = new StringBuilder();

        int length = Math.min(methods.length, 4);

        for (int i = length - 1; i > length - 4; i--){
            String method = methods[i].trim();
            if (i == length - 1) {
                result.append(method).append("\n\n");
            } else {
                result.append("/*\n").append(method).append("\n*/\n\n");
            }
        }

        return result.toString().trim();
    }


}