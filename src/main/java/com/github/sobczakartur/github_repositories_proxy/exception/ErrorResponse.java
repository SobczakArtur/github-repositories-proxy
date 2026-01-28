package com.github.sobczakartur.github_repositories_proxy.exception;

public record ErrorResponse(int status, String message) {}