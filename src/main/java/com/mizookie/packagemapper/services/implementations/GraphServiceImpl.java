package com.mizookie.packagemapper.services.implementations;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.mizookie.packagemapper.services.GraphService;

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
        for (String source : dependencyMap.keySet()) {
            System.out.println(source + " --> ");
            for (String target : dependencyMap.get(source)) {
                System.out.println("  " + target);
            }
        }
    }

}
