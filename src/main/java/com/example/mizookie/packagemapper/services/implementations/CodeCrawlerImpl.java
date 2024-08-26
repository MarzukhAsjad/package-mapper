package com.example.mizookie.packagemapper.services.implementations;
import java.util.Map;

import com.example.mizookie.packagemapper.services.CodeCrawler;

public class CodeCrawlerImpl implements CodeCrawler {
    @Override
    public String crawl(String path) {
        // TODO: Crawl the path, get the text data, and return it as a string
        return null;
    }

    @Override
    public void parse(String data) {
        // TODO: Parse the data and store it in a data structure while 
        // maintaining the relationships between the classes
    }

    @Override
    public void visualize(Map<String, String> classesMap) {
        // TODO: Visualize the parsed data in a graphical format
    }
    
}
