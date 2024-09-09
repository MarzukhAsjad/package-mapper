package com.mizookie.packagemapper.services;

import org.springframework.stereotype.Service;

@Service
public interface GraphService {
    // Add a dependency between two classes
    void addDependency(String source, String target);
    // Display the graph
    void displayGraph();
}
