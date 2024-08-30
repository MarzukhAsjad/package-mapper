package com.mizookie.packagemapper.services;
import java.util.Map;
import java.util.List;
import java.io.IOException;

public interface AnalyserService {
    void visualize(Map<String, List<String>> classesMap); // visualize the parsed data
    void analyse(String repositoryPath); // orchestrate the crawling, parsing and visualization
    void analyse() throws IOException; // analyze all repositories
}
