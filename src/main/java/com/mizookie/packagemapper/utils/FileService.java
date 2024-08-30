package com.mizookie.packagemapper.utils;

import java.util.*;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

@Slf4j
@UtilityClass
public class FileService {
    // Get the file extension
    public String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf);
    }

    // Get the filename with no extension
    public String getFileNameWithoutExtension(String fileName) {
        int lastIndexOfSlash = Math.max(fileName.lastIndexOf("/"), fileName.lastIndexOf("\\"));
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return fileName;
        }
        return fileName.substring(lastIndexOfSlash + 1, lastIndexOfDot);
    }

    // Get the filename with extension
    public String getFileNameWithExtension(String fileName) {
        int lastIndexOfSlash = Math.max(fileName.lastIndexOf("/"), fileName.lastIndexOf("\\"));
        if (lastIndexOfSlash == -1) {
            return fileName;
        }
        return fileName.substring(lastIndexOfSlash + 1);
    }

    // Get all files in a directory and its subdirectories
    public List<String> getFiles(String directoryPath) {
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
    public List<String> getDirectories(String directoryPath) {
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

    // Helper method to delete a directory and its contents recursively
    public void removeRecursively(File f) {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                removeRecursively(c);
            }
        }
        if (!f.delete()) {
            log.error("Failed to delete file: {}", f.getAbsolutePath());
        }
    }
}
