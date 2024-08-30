package com.mizookie.packagemapper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizookie.packagemapper.services.AnalyserService;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/analyse")
public class AnalyserController {
    private final AnalyserService analyserService;

    @Autowired
    public AnalyserController(AnalyserService analyserService) {
        this.analyserService = analyserService;
    }

    @PostMapping("/custom")
    public void analyse(@RequestBody String repositoryPath) {
        log.info("Repository path received: {}", repositoryPath);
        analyserService.analyse(repositoryPath);
    }

    @PostMapping("/all")
    public void analyseAll() {
        log.info("Analyzing all repositories...");
        try {
            analyserService.analyse();
        } catch (IOException e) {
            log.error("Failed to analyze all repositories: {}", e.getMessage());
        }
    }
}
