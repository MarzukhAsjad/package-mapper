package com.mizookie.mizookie.packagemapper.services;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * This interface represents a service for interacting with GitHub repositories.
 */
@Service
public interface GithubRepositoryService {
    String downloadPublicRepository(String repositoryUrlString) throws GitAPIException;
    String deleteRepository() throws IOException, GitAPIException;
    void downloadPrivateRepository(String repositoryUrlString, String token);
}
