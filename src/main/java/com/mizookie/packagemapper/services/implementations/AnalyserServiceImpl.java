package com.mizookie.packagemapper.services.implementations;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mizookie.packagemapper.services.AnalyserService;
import com.mizookie.packagemapper.utils.Printer;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnalyserServiceImpl implements AnalyserService {

    @Value("${repository.directory}")
    private String localRepositoryDirectory;

    private Map<String, List<String>> classesMap = new HashMap<>();
    
    /**
     * This method visualizes the parsed data in a graphical format.
     * @param classesMap The map containing the parsed data.
     */
    @Override
    public void visualize(Map<String, List<String>> classesMap) {
        // Improve visualization of the parsed data in a graphical format
        log.info("Visualizing the parsed data...");
        for (Map.Entry<String, List<String>> entry : classesMap.entrySet()) {
            Printer.log("Current File: " + entry.getKey());
            for (String importStatement : entry.getValue()) {
                Printer.log("----- Import: " + importStatement);
            }
        }
    }

    /**
     * This method orchestrates the crawling, parsing and visualization of the code
     * in the repository.
     * @param repositoryPath The path to the repository to analyze.
     */
    @Override
    public void analyse(String repositoryPath) {
        List<String> repoFiles = getFiles(repositoryPath);
        Printer.log("Analyzing repository: " + repositoryPath);
        for (String file : repoFiles) {
            // Skip files that do not have the following extensions or contain ".git"
            String[] doNotSkipExtensions = {".java", ".js", ".ts", ".jsx", ".tsx", ".c", ".cpp", ".csharp", ".py", ".rb", ".kt"};
            boolean skipFile = true;
            for (String extension : doNotSkipExtensions) {
                if (file.endsWith(extension)) {
                    skipFile = false;
                    break;
                }
            }
            if (skipFile || file.contains(".git")) {
                log.info("Skipping file: {}", file);
                continue;
            }
            
            List<String> imports = parse(crawl(file));
            classesMap.put(file, imports);
        }
        
        // Add the parsed data as value for the key "path" in the classesMap
        visualize(classesMap);
    }

    /**
     * This method analyzes all repositories.
     */
    @Override
    public void analyse() throws IOException {
        // Analyze all local repositories
        log.info("Analyzing all local repositories...");
        
        // Crawl through all repositories within the local repository directory
        List<String> repositories = Files.walk(Paths.get(localRepositoryDirectory))
                .filter(Files::isDirectory)
                .map(Object::toString)
                .toList();

        for (String repository : repositories) {
            analyse(repository);
        }
    }

    // This method crawls the code in the repository and extracts the imports
    private String crawl(String path) {
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

    // This method parses the imports from the code 
    private List<String> parse(String data) {
        // Parsing can be improved by using a more sophisticated parser in the future
        return getImports(data);
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
