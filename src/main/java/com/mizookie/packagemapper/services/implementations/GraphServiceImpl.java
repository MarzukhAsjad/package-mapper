package com.mizookie.packagemapper.services.implementations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.mizookie.packagemapper.services.GraphService;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

@Service
public class GraphServiceImpl implements GraphService {

    // HashMap to store the dependencyMap between classes
    private Map<String, List<String>> dependencyMap;

    @Value("${analysis.directory}")
    private String analysisDirectory;
    // Set the dependency map
    @Override
    public void setDependencyMap(Map<String, List<String>> classesMap) {
        // Set the dependency map
        this.dependencyMap = classesMap;
    }

    // Add a dependency between two classes
    @Override
    public void addEdge(String source, String target) {
        // Add a dependency between two classes
        if (dependencyMap.containsKey(source)) {
            dependencyMap.get(source).add(target);
        } else {
            List<String> targets = new ArrayList<>();
            targets.add(target);
            dependencyMap.put(source, targets);
        }
    }
    // Display the graph
    @Override
    public void displayGraph(String repositoryName) {
        // Display the graph
        String fileName = analysisDirectory + "/" + repositoryName + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String source : dependencyMap.keySet()) {
                writer.println(source + " --> ");
                for (String target : dependencyMap.get(source)) {
                    writer.println("          " + target);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
