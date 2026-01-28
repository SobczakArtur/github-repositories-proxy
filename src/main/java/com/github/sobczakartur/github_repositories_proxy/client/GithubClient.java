package com.github.sobczakartur.github_repositories_proxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class GithubClient {

    private final RestClient restClient;
    private final String githubApiUrl;

    GithubClient(RestClient.Builder restClientBuilder, @Value("${github.api.url}") String githubApiUrl) {
        this.restClient = restClientBuilder.build();
        this.githubApiUrl = githubApiUrl;
    }

    public List<Map<String, Object>> getRepositories(String username) {
        return restClient.get()
                .uri(githubApiUrl + "/users/{username}/repos", username)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        (request, response) -> {
                            throw new org.springframework.web.client.HttpClientErrorException(
                                    response.getStatusCode(), "User not found");
                        })
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<Map<String, Object>> getBranches(String owner, String repo) {
        return restClient.get()
                .uri(githubApiUrl + "/repos/{owner}/{repo}/branches", owner, repo)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        (request, response) -> {
                            throw new HttpClientErrorException(
                                    response.getStatusCode(), "Failed to fetch branches");
                        })
                .body(new ParameterizedTypeReference<>() {});
    }
}