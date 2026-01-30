GitHub Repositories Proxy API

A REST API that returns all non-fork GitHub repositories of a given user, including branch names and last commit SHA.
Implemented as part of a recruitment task, strictly following provided requirements.

Stack

Java 25

Spring Boot 4.0.1

Gradle (Kotlin DSL)

Spring MVC

Spring RestClient

WireMock (integration tests)

Endpoint
GET /repositories/{username}

Success (200)
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

User Not Found (404)
{
  "status": 404,
  "message": "User not found"
}

Architecture

Controller: GithubRepositoryController

Service: GithubRepositoryService

Client: GithubClient

Single package, no DTO/domain split, no security, no pagination, no caching, no resilience, no WebFlux.

Testing

Only integration tests using:

@SpringBootTest

MockMvc

WireMock

Test class: GithubRepositoriesProxyIT

Running
./gradlew build
./gradlew bootRun


App runs at:

http://localhost:8080

Author

Artur Sobczak
📧 sobczak.artur88@gmail.com

🔗 LinkedIn
