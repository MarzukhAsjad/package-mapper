package com.mizookie.packagemapper.utils;

import java.io.FileWriter;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Printer {
    // private constructor to hide the implicit public one
    private Printer() { }

    // Print to console
    public static void log(String message) {
        log.info(message);
        writeToFile(message);
    }

    // Write to file
    private static void writeToFile(String message) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            String fileName = "LOGS-" + currentDate + ".log";
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(message + "\n");
            writer.close();
            log.info("Successfully logged to the file.");
        } catch (IOException e) {
            log.error("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

}
