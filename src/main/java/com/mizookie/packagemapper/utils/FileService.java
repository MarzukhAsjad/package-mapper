package com.mizookie.packagemapper.utils;

import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class FileService {

    private FileService() {
        throw new IllegalStateException("Utility class");
    }

    // Get the file extension
    public static String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf);
    }

    // Get the filename with no extension
    public static String getFileNameWithoutExtension(String fileName) {
        int lastIndexOfSlash = Math.max(fileName.lastIndexOf("/"), fileName.lastIndexOf("\\"));
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return fileName;
        }
        return fileName.substring(lastIndexOfSlash + 1, lastIndexOfDot);
    }

    // Get the filename with extension
    public static String getFileNameWithExtension(String fileName) {
        int lastIndexOfSlash = Math.max(fileName.lastIndexOf("/"), fileName.lastIndexOf("\\"));
        if (lastIndexOfSlash == -1) {
            return fileName;
        }
        return fileName.substring(lastIndexOfSlash + 1);
    }

    // Get all files in a directory and its subdirectories
    public static List<String> getFiles(String directoryPath) {
        List<String> files = null;
        try {
            files = Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .map(Object::toString)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    // Get all directories in a directory (excluding subdirectories)
    public static List<String> getDirectories(String directoryPath) {
        List<String> directories = null;
        try {
            directories = Files.walk(Paths.get(directoryPath), 1)
                    .filter(Files::isDirectory)
                    .map(Object::toString)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directories;
    }

}
