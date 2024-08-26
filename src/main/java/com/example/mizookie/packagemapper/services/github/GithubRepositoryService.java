package com.example.mizookie.packagemapper.services.github;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

/**
 * This interface represents a service for interacting with GitHub repositories.
 */
@Service
public interface GithubRepositoryService {
    String downloadPublicRepository(String repositoryUrlString) throws GitAPIException;
    String deleteRepository() throws Exception;
    void downloadPrivateRepository(String repositoryUrlString, String token);
}
