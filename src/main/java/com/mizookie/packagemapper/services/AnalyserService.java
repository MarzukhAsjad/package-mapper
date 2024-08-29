package com.mizookie.packagemapper.services;
import java.util.Map;
import java.util.List;

public interface AnalyserService {
    String crawl(String path); // path to the files which return the code to parse
    List<String> parse(String data); // data from the file to parse
    void visualize(Map<String, List<String>> classesMap); // visualize the parsed data
    void analyse(String repositoryPath); // orchestrate the crawling, parsing and visualization
}
