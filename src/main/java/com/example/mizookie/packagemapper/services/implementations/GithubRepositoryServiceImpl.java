package com.example.mizookie.packagemapper.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import java.util.stream.Stream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.mizookie.packagemapper.services.GithubRepositoryService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

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

    /**
     * This method downloads a public GitHub repository to the local file system.
     * 
     * @param repositoryUrlString The URL of the repository to download.
     * @return A message indicating the result of the download operation.
     */
    @Override
    public String downloadPublicRepository(String repositoryUrlString) throws GitAPIException {
        try {
            // Create a new File object to represent the local directory where the
            // repository will be cloned
            File localDirectory = new File(localRepositoryDirectory);

            // Clone the repository using JGit library
            Git.cloneRepository()
                    .setURI(repositoryUrlString) // Set the repository URL
                    .setDirectory(localDirectory) // Set the local directory
                    .call();

            // Shutdown the Git object
            Git.shutdown();

            return "Repository downloaded successfully!";
        } catch (GitAPIException e) {
            return "Failed to clone repository: " + e.getMessage();
        }
    }

    /**
     * This method downloads a private GitHub repository to the local file system.
     * 
     * @param repositoryUrlString The URL of the repository to download.
     * @param token               The access token for authenticating with the
     *                            GitHub API.
     */
    @Override
    public void downloadPrivateRepository(String repositoryUrlString, String token) {
        // TODO: Implement method to download private repositories
    }

    @Override
    public String deleteRepository() throws IOException {
        Path directoryPath = Paths.get(localRepositoryDirectory);

        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            log.info("Deleting repository directory... for {}", directoryPath.toAbsolutePath());

            try (Stream<Path> paths = Files.walk(directoryPath)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            File file = path.toFile();
                            log.info(path.toString());
                            if (!file.canWrite() && !file.setWritable(true)) {
                                log.error("Failed to set write permission: " + path);
                            }

                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                log.error("Failed to delete: " + path, e);
                            }
                        });
            } catch (IOException e) {
                log.error("Failed to delete directory", e);
                throw e;
            }

            Files.createDirectories(directoryPath); // Recreate the empty directory
            return "Repository directory deleted!";
        } else {
            return "Repository directory not found!";
        }
    }
}
