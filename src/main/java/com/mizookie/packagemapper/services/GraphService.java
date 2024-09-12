package com.mizookie.packagemapper.services;

import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface GraphService {
    // Set the dependency map
    void setDependencyMap(Map<String, List<String>> classesMap);
    // Add a dependency between two classes
    void addEdge(String source, String target);
    // Display the graph
    void displayGraph(String repositoryName);
}
