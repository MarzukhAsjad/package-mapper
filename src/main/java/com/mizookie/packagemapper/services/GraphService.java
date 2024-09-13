package com.mizookie.packagemapper.services;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

@Service
public interface GraphService {
    // Set the dependency map
    void setDependencyMap(Graph<String, DefaultEdge> graph);
    // Testing Overloading
    void setDependencyMap(Map<String, List<String>> classesMap);

    // Add a dependency between two classes
    void addEdge(String source, String target);
    // Display the graph
    void displayGraph(String repositoryName);
}
