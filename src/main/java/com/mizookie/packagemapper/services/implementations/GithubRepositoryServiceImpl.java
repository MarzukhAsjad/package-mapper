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

    private Git git;

    /**
     * This method downloads a public GitHub repository to the local file system.
     * 
     * @param repositoryUrlString The URL of the repository to download.
     * @return A message indicating the result of the download operation.
     */
    @Override
    public String downloadPublicRepository(String repositoryUrlString) throws GitAPIException {
        try {
            File localDirectory = new File(localRepositoryDirectory);

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
     * This method downloads a private GitHub repository to the local file system.
     * 
     * @param repositoryUrlString The URL of the repository to download.
     * @param token               The access token for authenticating with GitHub
     *                            for cloning the private repository.
     */
    @Override
    public void downloadPrivateRepository(String repositoryUrlString, String token) {
        // TODO: Implement method to download private repositories
    }

    @Override
    public String deleteRepository() throws IOException, GitAPIException {
        Path directoryPath = Paths.get(localRepositoryDirectory);
    
        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            log.info("Deleting repository directory... for {}", directoryPath.toAbsolutePath());
            
            // Close the Git repository and shutdown the Git instance
            try {
                git.close();
                git = null;
                Git.shutdown();
                // Delete the repository directory and its contents recursively
                removeRecursively(directoryPath.toFile());
            } catch (Exception e) {
                log.error("Failed to delete repository directory: {}", e.getMessage());
                throw e;
            }
            // Recreate the empty directory
            Files.createDirectories(directoryPath);
            return "Repository directory deleted!";
        } else if (git == null) {
            return "Repository directory is already deleted!";
        } else {
            return "Repository directory not found!";
        }
    }

    // Helper method to remove a directory and its contents recursively
    private static void removeRecursively(File f) {
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
