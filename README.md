# GitHub Repositories Proxy API

A REST API that returns all non-fork GitHub repositories of a given user, including branch names and last commit SHA.
Implemented as part of a recruitment task, strictly following the provided requirements.<br>
<br>

### Stack

**Java 25**

**Spring Boot 4.0.1**

**Gradle (Kotlin DSL)**

**Spring MVC**

**Spring RestClient**

**WireMock (integration tests)**<br>
<br>

### Endpoint

GET /repositories/{username}<br>
<br>

### Success Response (200)
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



### User Not Found Response (404)
{

  "status": 404,
  
  "message": "User not found"
  
}<br>
<br>

### Architecture

- Controller: GithubRepositoryController

- Service: GithubRepositoryService

- Client: GithubClient

*Single package. No DTO/domain split. No security. No pagination. No caching. No resilience. No WebFlux.*<br>
<br>

### Testing

- Only integration tests

- Tools used:

  - @SpringBootTest

  - MockMvc

  - WireMock

Test class: GithubRepositoriesProxyIT



### Running the Application

./gradlew build

./gradlew bootRun



App will be available at:

http://localhost:8080

### Author

Artur Sobczak
sobczak.artur88@gmail.com

### LinkedIn
https://www.linkedin.com/in/artur-sobczak-03724a175/
