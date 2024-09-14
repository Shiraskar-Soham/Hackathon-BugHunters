package com.plugin.hackathon;

public class AICopilotService {
    private static AICopilotService instance;
    private AIModel aiModel;

    private AICopilotService() {}

    public static synchronized AICopilotService getInstance() {
        if (instance == null) {
            instance = new AICopilotService();
            instance.aiModel = new AIModel();
        }
        return instance;
    }

    public String generateCode(String inputCode) {
        return aiModel.generateSnippet(inputCode);
    }
}
