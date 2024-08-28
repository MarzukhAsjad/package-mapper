package com.mizookie.mizookie.packagemapper.services.implementations;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mizookie.mizookie.packagemapper.services.CodeCrawler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CodeCrawlerImpl implements CodeCrawler {
    @Override
    public String crawl(String path) {
        StringBuilder code = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (Boolean.TRUE.equals(isImportStatement(line))) {
                    code.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toString();
    }

    @Override
    public void parse(String data) {
        // TODO: Parse the data and store it in a data structure while 
        log.info("Data from the file: {}", data);
        // maintaining the relationships between the classes
        
    }

    @Override
    public void visualize(Map<String, String> classesMap) {
        // TODO: Visualize the parsed data in a graphical format
    }
    
    // Helper methods for parsing the code
    private Boolean isImportStatement(String line) {
        return line.startsWith("import");
    }
}
