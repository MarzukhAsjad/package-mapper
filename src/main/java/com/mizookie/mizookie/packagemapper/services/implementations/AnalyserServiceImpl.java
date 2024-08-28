package com.mizookie.mizookie.packagemapper.services.implementations;
import java.util.Map;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.mizookie.mizookie.packagemapper.services.AnalyserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnalyserServiceImpl implements AnalyserService {
    private Map<String, List<String>> classesMap = new HashMap<>();

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
    public List<String> parse(String data) {
        // TODO: Parse the data and store it in a data structure while 
        // maintaining the relationships between the classes
        return getImports(data);
    }

    @Override
    public void visualize(Map<String, List<String>> classesMap) {
        // TODO: Visualize the parsed data in a graphical format
        log.info("Visualizing the parsed data...");
        for (Map.Entry<String, List<String>> entry : classesMap.entrySet()) {
            log.info("File: {}", entry.getKey());
            for (String importStatement : entry.getValue()) {
                log.info("----- Import: {}", importStatement);
            }
        }
    }

    @Override
    public void analyse(String repositoryPath) {
        List<String> repoFiles = getFiles(repositoryPath);
        for (String file : repoFiles) {
            List<String> imports = parse(crawl(file));
            classesMap.put(file, imports);
        }
        // Add the parsed data as value for the key "path" in the classesMap
        visualize(classesMap);
    }
    
    // Helper methods for parsing the code
    private Boolean isImportStatement(String line) {
        return line.startsWith("import");
    }

    // Helper method for extracting the imports from a line of code
    private List<String> getImports(String line) {
        List<String> imports = new ArrayList<>();
        if (Boolean.TRUE.equals(isImportStatement(line))) {
            String importStatement = line.replace("import", "")
                    .replace(";", "")
                    .replace("\n", "")
                    .trim();
            
            log.info("Import: |{}|", importStatement);
            imports.add(importStatement);
        }
        return imports;
    }
    // Helper method for extracting filenames under a directory
    private List<String> getFiles(String directoryPath) {
        List<String> files = null;
        try {
            files = Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .map(Object::toString)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
