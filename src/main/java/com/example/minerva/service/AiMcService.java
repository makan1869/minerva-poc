package com.example.minerva.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to interact with AI-MC-Server
 */
@Service
public class AiMcService {

    @Value("${ai-mc-server.enabled:false}")
    private boolean enabled;
    
    @Value("${ai-mc-server.endpoint:/ai-mc}")
    private String endpoint;
    
    /**
     * Process a request through the AI-MC-Server
     * 
     * @param input The input data to process
     * @return The processed result
     */
    public String processRequest(String input) {
        if (!enabled) {
            return "AI-MC-Server is disabled. Please enable it in application.properties.";
        }
        
        // This is a placeholder for the actual AI-MC-Server integration
        // In a real implementation, you would use the appropriate client from the ai-mc-server dependency
        return "AI-MC-Server processed: " + input;
    }
    
    /**
     * Get the status of the AI-MC-Server connection
     * 
     * @return Status information
     */
    public String getStatus() {
        return "AI-MC-Server is " + (enabled ? "enabled" : "disabled") + 
               " with endpoint " + endpoint;
    }
}
