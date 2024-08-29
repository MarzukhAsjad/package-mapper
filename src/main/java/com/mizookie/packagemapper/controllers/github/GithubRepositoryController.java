package com.mizookie.packagemapper.controllers.github;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizookie.packagemapper.dto.user.GithubRepositoryInfoRequest;
import com.mizookie.packagemapper.services.GithubRepositoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class is a controller for handling requests related to GitHub repositories.
 */
@Slf4j
@RestController
@RequestMapping("/repository")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryService;

    @Autowired
    public GithubRepositoryController(GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    private static final String MESSAGE_KEY = "message";

    /**
     * This method downloads a public GitHub repository to the local file system.
     * @param requestBody The request body containing the URL of the repository to download.
     * @return A response entity containing a message indicating the result of the download operation.
     */
    @PostMapping("/download")
    ResponseEntity<Map<String, Object>> downloadPublicRepository(@RequestBody GithubRepositoryInfoRequest requestBody) {
        String repositoryUrlString = requestBody.getRepositoryUrl();
        log.info("Downloading repository: {}", repositoryUrlString);
        try {
            String responseMessageString = githubRepositoryService.downloadPublicRepository(repositoryUrlString);
            return ResponseEntity.ok(Map.of(MESSAGE_KEY, responseMessageString));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_KEY, e.getMessage()));
        }
    }

    /**
     * This method deletes a GitHub repository.
     * @param requestBody The request body containing the URL of the repository to delete.
     * @return A response entity containing a message indicating the result of the delete operation.
     */
    @PostMapping("/delete")
    ResponseEntity<Map<String, Object>> deleteRepository() {
        log.info("Deleting repository...");
        try {
            String responseMessageString = githubRepositoryService.deleteRepository();
            return ResponseEntity.ok(Map.of(MESSAGE_KEY, responseMessageString));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(MESSAGE_KEY, e.getMessage()));
        }
    } 
}
