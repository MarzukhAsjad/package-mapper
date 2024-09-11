package com.mizookie.packagemapper.services.implementations;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.mizookie.packagemapper.services.GraphService;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import lombok.Data;

@Service
@Data
public class GraphServiceImpl implements GraphService {

    // HashMap to store the dependencyMap between classes
    private Map<String, List<String>> dependencyMap;

    // Add a dependency between two classes
    @Override
    public void addDependency(String source, String target) {
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
    public void displayGraph() {
        // Display the graph
        try (PrintWriter writer = new PrintWriter(new FileWriter("graph.txt"))) {
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
