package com.mizookie.packagemapper.services.implementations;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.mizookie.packagemapper.services.GraphService;

import lombok.Data;

@Service
@Data
public class GraphServiceImpl implements GraphService {

    // HashMap to store the dependencies between classes
    private HashMap<String, List<String>> dependencies;

    // Add a dependency between two classes
    @Override
    public void addDependency(String source, String target) {
        // Add a dependency between two classes
        if (dependencies.containsKey(source)) {
            dependencies.get(source).add(target);
        } else {
            List<String> targets = new ArrayList<>();
            targets.add(target);
            dependencies.put(source, targets);
        }
    }
    // Display the graph
    @Override
    public void displayGraph() {
        // Display the graph
        for (String source : dependencies.keySet()) {
            System.out.println(source + " --> ");
            for (String target : dependencies.get(source)) {
                System.out.println("  " + target);
            }
        }
    }

}
