package com.github.sobczakartur.github_repositories_proxy.controller;

import com.github.sobczakartur.github_repositories_proxy.service.GithubRepositoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class GithubRepositoryController {

    private final GithubRepositoryService service;

    GithubRepositoryController(GithubRepositoryService service) {
        this.service = service;
    }

    @GetMapping("/repositories/{username}")
    public List<Map<String, Object>> getRepositories(@PathVariable String username) {
        return service.getRepositories(username);
    }
}