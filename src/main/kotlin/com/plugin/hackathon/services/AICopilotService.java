package com.plugin.hackathon.services;

import com.plugin.hackathon.AIModel;

public class AICopilotService {
    private final AIModel aiModel;

    private static AICopilotService instance;

    private AICopilotService() {
        aiModel = new AIModel();
    }

    public static AICopilotService getInstance() {
        if (instance == null) {
            instance = new AICopilotService();
        }
        return instance;
    }

    public String generateCode(String inputCode) {
        return aiModel.generateSnippet(inputCode);
    }
}
