package com.example.minerva.controller;

import com.example.minerva.service.AiMcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MinervaController {

    private final AiMcService aiMcService;

    @Autowired
    public MinervaController(AiMcService aiMcService) {
        this.aiMcService = aiMcService;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Minerva PoC is running! " + aiMcService.getStatus();
    }
    
    @PostMapping("/process")
    public String processRequest(@RequestBody String input) {
        return aiMcService.processRequest(input);
    }
}
