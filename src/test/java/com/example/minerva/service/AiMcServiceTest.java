package com.example.minerva.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "ai-mc-server.enabled=true",
    "ai-mc-server.endpoint=/test-ai-mc"
})
class AiMcServiceTest {

    @Autowired
    private AiMcService aiMcService;

    @Test
    void testProcessRequest() {
        String result = aiMcService.processRequest("test input");
        assertNotNull(result);
        assertTrue(result.contains("test input"));
    }

    @Test
    void testGetStatus() {
        String status = aiMcService.getStatus();
        assertNotNull(status);
        assertTrue(status.contains("enabled"));
        assertTrue(status.contains("/test-ai-mc"));
    }
}
