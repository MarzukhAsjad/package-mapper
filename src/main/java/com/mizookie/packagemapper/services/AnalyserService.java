package com.mizookie.packagemapper.services;
import java.util.Map;
import java.util.List;

public interface AnalyserService {
    void visualize(Map<String, List<String>> classesMap); // visualize the parsed data
    void analyse(String repositoryPath); // orchestrate the crawling, parsing and visualization
}
