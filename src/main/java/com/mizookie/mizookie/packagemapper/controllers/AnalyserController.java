package com.mizookie.mizookie.packagemapper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizookie.mizookie.packagemapper.services.CodeCrawler;

@RestController
@RequestMapping("/analyse")
public class AnalyserController {
    private final CodeCrawler codeCrawler;

    @Autowired
    public AnalyserController(CodeCrawler codeCrawler) {
        this.codeCrawler = codeCrawler;
    }

    @PostMapping("/custom")
    public void analyse(@RequestBody String repositoryPath) {
        codeCrawler.orchestrate(repositoryPath);
    }
}
