# GitHub Repositories Proxy API

REST API that returns all **non-fork GitHub repositories** of a given user, together with their **branches and the
SHA of the last commit on each branch**. It works as a thin proxy over the GitHub REST API, built with Java 21,
Spring Boot and Spring `RestClient`.

> **In short:** one endpoint, a small layered Spring Boot application that integrates with an external REST API,
> filters and reshapes its data, returns a JSON error for unknown users, and is covered by integration tests that
> mock GitHub with WireMock (no real network calls in tests).

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)
![Gradle](https://img.shields.io/badge/Gradle-Kotlin%20DSL-blue)

---

## How it works

1. Fetches the user's repositories from GitHub (`GET /users/{username}/repos`).
2. Removes forks.
3. For each remaining repository, fetches its branches (`GET /repos/{owner}/{repo}/branches`).
4. Returns repository name, owner login and a list of branches with the last commit SHA.

---

## Endpoint

`GET /repositories/{username}`

### Success response (200)

```json
[
  {
    "repositoryName": "backend-repo",
    "ownerLogin": "test-user",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123"
      }
    ]
  }
]
```

### User not found (404)

```json
{
  "status": 404,
  "message": "User not found"
}
```

### Try it

```bash
curl http://localhost:8080/repositories/<github-username>
```

---

## What to look at (for a quick code review)

| Topic | Where / what |
|---|---|
| **External API integration** | `GithubClient` uses Spring `RestClient`; the GitHub base URL is configurable through the `github.api.url` property |
| **Filtering and mapping** | `GithubRepositoryService`: drops forks and builds the response with repository name, owner and branches |
| **Layered structure** | `controller` → `service` → `client`, with a separate `exception` package |
| **Error handling** | Custom `UserNotFoundException`; unknown users get a JSON error body (covered by a test) |
| **Tests without network access** | Integration tests stub both GitHub endpoints with WireMock, so tests are fast and deterministic |

---

## Tech stack

| Area | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2.3, Spring MVC, Spring `RestClient` |
| Build tool | Gradle (Kotlin DSL, wrapper included) |
| Testing | JUnit 5, `@SpringBootTest`, MockMvc, WireMock |

---

## Configuration

`src/main/resources/application.properties`:

```properties
github.api.url=https://api.github.com
```

---

## Getting started

Requires **JDK 21** (the Gradle toolchain is set to Java 21).

```bash
git clone https://github.com/SobczakArtur/github-repositories-proxy.git
cd github-repositories-proxy
./gradlew bootRun
```

The application is available at `http://localhost:8080`.

---

## Testing

```bash
./gradlew test
```

Test class: `GithubRepositoriesProxyIT` (`@SpringBootTest`, MockMvc, WireMock). Scenarios:

- an existing user: forks are filtered out and the branches with the last commit SHA are returned,
- an unknown user: the API responds with `404` and the JSON error body shown above.

---

## Known limitations

This is a deliberately small project. What it does **not** do (yet):

- **No authentication towards GitHub.** Requests without a token have a low rate limit, and the API makes one
  request for the repository list plus one per repository for its branches.
- **No pagination.** GitHub returns 30 items per page by default, so users with more repositories or branches
  get a partial result.
- **No caching and no resilience mechanisms** (retries, timeouts).
- **Responses are built from maps** instead of dedicated DTO or record types.
- **Only integration tests** (no unit tests of the service layer).

---

## Screenshots

| | |
|---|---|
| ![Example 1](images/github_app%20%281%29.JPG) | ![Example 2](images/github_app%20%282%29.JPG) |
| ![Example 3](images/github_app%20%283%29.JPG) | |

---

## Author

**Artur Sobczak**: [GitHub](https://github.com/SobczakArtur) · [LinkedIn](https://www.linkedin.com/in/artur-sobczak-03724a175/) · sobczak.artur88@gmail.com
