package com.example.nftclubcoinapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ConfigController {
    @Autowired
    private Environment env;

    // These read keys from Azure App Configuration
    @Value("${name1:default-value}")
    private String name1;
    
    @Value("${name2:default-value}")
    private String name2;

    @GetMapping("/config")
    public String getConfig() {
        return "Value from Azure App Configuration: " + name1;
    }
    
    @GetMapping("/env")
    public String getEnv() {
        return "Environment property 'name1': " + env.getProperty("name1", "NOT_FOUND");
    }
    
    // NEW: JSON endpoint to show all Azure properties
    @GetMapping("/config/json")
    public Map<String, String> getConfigJson() {
        Map<String, String> config = new HashMap<>();
        config.put("name1", name1);
        config.put("name2", name2);
        config.put("source", "Azure App Configuration");
        return config;
    }
    
    @GetMapping("/debug")
    public String debug() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DEBUGGING AZURE APP CONFIGURATION ===\n\n");
        
        // Test different key variations
        sb.append("@Value ${name1}: ").append(name1).append("\n");
        sb.append("@Value ${name2}: ").append(name2).append("\n");
        sb.append("env.getProperty('name1'): ").append(env.getProperty("name1", "NOT_FOUND")).append("\n");
        sb.append("env.getProperty('name2'): ").append(env.getProperty("name2", "NOT_FOUND")).append("\n");
        sb.append("env.getProperty('/application/name1'): ").append(env.getProperty("/application/name1", "NOT_FOUND")).append("\n");
        
        sb.append("\n=== PROPERTY SOURCES ===\n");
        org.springframework.core.env.AbstractEnvironment absEnv = (org.springframework.core.env.AbstractEnvironment) env;
        org.springframework.core.env.MutablePropertySources sources = absEnv.getPropertySources();
        
        for (org.springframework.core.env.PropertySource<?> source : sources) {
            sb.append(source.getName()).append("\n");
        }
        
        return sb.toString();
    }

}