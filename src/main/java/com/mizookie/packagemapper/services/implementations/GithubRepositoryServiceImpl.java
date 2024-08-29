package com.mizookie.packagemapper.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mizookie.packagemapper.services.GithubRepositoryService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of the GithubRepositoryService interface that provides methods
 * for downloading public and private repositories from GitHub.
 */
@Slf4j
@Service
@Component
public class GithubRepositoryServiceImpl implements GithubRepositoryService {

    @Value("${repository.directory}")
    private String localRepositoryDirectory;

    private String userRepositoryDirectory;

    private Git git;

    /**
     * Downloads a public GitHub repository to the local file system.
     * @param repositoryUrlString The URL of the repository to download.
     * @return A message indicating the result of the download operation.
     */
    @Override
    public String downloadPublicRepository(String repositoryUrlString) throws GitAPIException {
        try {
            // Extract the repository name from the URL
            String repositoryName = getRepositoryName(repositoryUrlString);
            userRepositoryDirectory = localRepositoryDirectory + "/" + repositoryName;
            File localDirectory = new File(userRepositoryDirectory);

            // Close the previous Git instance if it exists
            if (git != null) {
                git.close();
            }

            // Clone the repository to the local directory
            git = Git.cloneRepository()
                    .setURI(repositoryUrlString) // Set the repository URL
                    .setDirectory(localDirectory) // Set the local directory
                    .call();

            return "Repository downloaded successfully!";
        } catch (GitAPIException e) {
            return "Failed to clone repository: " + e.getMessage();
        }
    }

    /**
     * Downloads a private GitHub repository to the local file system.
     * @param repositoryUrlString The URL of the repository to download.
     * @param token The access token for the private repository.
     */
    @Override
    public void downloadPrivateRepository(String repositoryUrlString, String token) {
        // TODO: Implement method to download private repositories
    }

    /**
     * Deletes a GitHub repository from the local file system.
     * @return A message indicating the result of the delete operation.
     */
    @Override
    public String deleteRepository() throws IOException, GitAPIException {
        Path directoryPath = null;
        // Force delete all repositories if invoked right after the application starts
        if (userRepositoryDirectory == null) {
            directoryPath = Paths.get(localRepositoryDirectory);
            removeRecursively(directoryPath.toFile());
            Files.createDirectories(directoryPath);
            return "All repositories have been deleted.";
        }
        // Delete specific repository directory if it exists
        directoryPath = Paths.get(userRepositoryDirectory);
        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            // Close the Git repository and shutdown the Git instance
            try {
                if (git != null) {
                    git.close(); // Close the Git instance
                    git = null; // Set git to null after closing
                }
                // Delete the repository directory and its contents recursively
                log.info("Deleting repository directory: {}", directoryPath);
                removeRecursively(directoryPath.toFile());
            } catch (Exception e) {
                log.error("Failed to delete repository directory: {}", e.getMessage());
                throw e;
            }
            userRepositoryDirectory = null;
            return "Repository directory deleted!";
        } else {
            return "Repository directory not found!";
        }
    }

    // Helper method to extract the repository name from the URL
    private String getRepositoryName(String repositoryUrlString) {
        String[] urlParts = repositoryUrlString.split("/");
        String repositoryName = urlParts[urlParts.length - 1];
        if (repositoryName.endsWith(".git")) {
            repositoryName = repositoryName.substring(0, repositoryName.length() - 4);
        }
        return repositoryName;
    }

    // Helper method to delete a directory and its contents recursively
    private void removeRecursively(File f) {
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