package com.example.mizookie.packagemapper.services;

public interface CodeCrawler {
    String crawl(String path); // path to the files which return the code to parse
    void parse(String data); // data from the file to parse
    void visualize(); // visualize the parsed data
}
