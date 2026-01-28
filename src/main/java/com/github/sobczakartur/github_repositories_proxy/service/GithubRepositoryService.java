package com.github.sobczakartur.github_repositories_proxy.service;

import com.github.sobczakartur.github_repositories_proxy.client.GithubClient;
import com.github.sobczakartur.github_repositories_proxy.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GithubRepositoryService {

        private final GithubClient githubClient;

        GithubRepositoryService(GithubClient githubClient) {
            this.githubClient = githubClient;
        }

    public List<Map<String, Object>> getRepositories(String username) {
        List<Map<String, Object>> repositories = githubClient.getRepositories(username);

        if (repositories.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return repositories.stream()
                .filter(repo -> {
                    Object forkValue = repo.get("fork");
                    return forkValue instanceof Boolean && !(Boolean) forkValue;
                })
                .map(repo -> {
                    String repoName = (String) repo.get("name");
                    Map<String, Object> owner = (Map<String, Object>) repo.get("owner");
                    String ownerLogin = (String) owner.get("login");

                    List<Map<String, Object>> branches = githubClient.getBranches(ownerLogin, repoName);

                    List<Map<String, Object>> mappedBranches = branches.stream()
                            .map(branch -> {
                                String branchName = (String) branch.get("name");
                                Map<String, Object> commit = (Map<String, Object>) branch.get("commit");
                                String sha = (String) commit.get("sha");

                                return Map.<String, Object>of(
                                        "name", branchName,
                                        "lastCommitSha", sha
                                );
                            })
                            .toList();

                    return Map.of(
                            "repositoryName", repoName,
                            "ownerLogin", ownerLogin,
                            "branches", mappedBranches
                    );
                })
                .toList();
    }
}