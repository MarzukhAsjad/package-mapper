package com.mizookie.packagemapper.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Printer {
    // private constructor to hide the implicit public one
    private Printer() { }

    // Analysis file instance for logging
    private Path analysisFile;

    @Value("${logs.directory}")
    private String logsDirectory;

    @Value("${analysis.directory}")
    private String analysisDirectory;

    // Print to console
    public void log(String message) {
        log.info(message);
        writeToFile(message);
    }

    // Create analysis log
    public void createAnalysisLog(String repoString) {
        log.info("Creating analysis log for repository: " + FileService.getFileNameOnly(repoString));
        String analysisLog = "analysis-" + FileService.getFileNameOnly(repoString) + ".log";
        analysisFile = Paths.get(analysisDirectory, analysisLog);
        try {
            Files.createDirectories(analysisFile.getParent());
            Files.createFile(analysisFile);
        } catch (IOException e) {
            log.error("An error occurred while creating the analysis log file.");
            e.printStackTrace();
        }
    }

    // Write to file
    private void writeToFile(String message) {
        try {
            FileWriter writer = new FileWriter(analysisFile.toFile(), true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            log.error("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

}
