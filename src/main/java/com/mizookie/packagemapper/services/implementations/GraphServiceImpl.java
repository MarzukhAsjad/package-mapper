package com.mizookie.packagemapper.services.implementations;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mizookie.packagemapper.services.GraphService;
import com.mxgraph.layout.mxCircleLayout;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import com.mxgraph.util.mxCellRenderer;
import java.awt.Color;
import java.awt.image.BufferedImage;

@Service
public class GraphServiceImpl implements GraphService {

    // JGraphT graph to store dependencies between classes
    private Graph<String, DefaultEdge> dependencyGraph;

    @Value("${analysis.directory}")
    private String analysisDirectory;

    // Constructor to initialize the graph
    public GraphServiceImpl() {
        // Initialize a directed graph
        this.dependencyGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    // Set the dependency map (not needed with JGraphT)
    @Override
    public void setDependencyMap(Map<String, List<String>> classesMap) {
        // Clear the existing graph
        dependencyGraph.removeAllVertices(dependencyGraph.vertexSet());
        // Create a copy of the classesMap
        Map<String, List<String>> classesCopy = new HashMap<>(classesMap);
        
        // Add vertices and edges from the copied map
        for (Map.Entry<String, List<String>> entry : classesCopy.entrySet()) {
            String source = entry.getKey();
            dependencyGraph.addVertex(source);
            for (String target : entry.getValue()) {
                dependencyGraph.addVertex(target);
                dependencyGraph.addEdge(source, target);
            }
        }
    }

    @Override // Set the dependency map
    public void setDependencyMap(Graph<String, DefaultEdge> graph) {
        this.dependencyGraph = graph;
    }

    // Add a dependency between two classes
    @Override
    public void addEdge(String source, String target) {
        // Add vertices if they don't exist
        dependencyGraph.addVertex(source);
        dependencyGraph.addVertex(target);
        // Add the edge (dependency)
        dependencyGraph.addEdge(source, target);
    }

    // Display the graph
    @Override
    public void displayGraph(String repositoryName) {
        // Create a file to save the image
        String fileName = analysisDirectory + "/" + repositoryName + ".png";
        File imgFile = new File(fileName);

        // Create a JGraphXAdapter for the JGraphT graph
        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(dependencyGraph);

        // Apply a layout to the graph
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        // Create a BufferedImage from the graph
        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);

        // Write the BufferedImage to a file
        try {
            ImageIO.write(image, "PNG", imgFile);
            System.out.println("Graph image saved to: " + imgFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Graph<String, DefaultEdge> createLargeGraph() {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Add vertices
        for (int i = 1; i <= 1000; i++) {
            graph.addVertex("v" + i);
        }

        // Add edges
        for (int i = 1; i < 1000; i++) {
            graph.addEdge("v" + i, "v" + (i + 1));
        }
        graph.addEdge("v1000", "v1");

        return graph;
    }

    public static Graph<String, DefaultEdge> createSmallGraph() {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Add vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        // Add edges
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");

        return graph;
    }

    public static Graph<String, DefaultEdge> createMediumGraph() {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Add vertices
        for (int i = 1; i <= 100; i++) {
            graph.addVertex("v" + i);
        }

        // Add edges
        for (int i = 1; i < 100; i++) {
            graph.addEdge("v" + i, "v" + (i + 1));
        }
        graph.addEdge("v100", "v1");

        return graph;
    }
}