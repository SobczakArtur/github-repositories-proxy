# GitHub Repositories Proxy API

A REST API that returns all non-fork GitHub repositories of a given user, including branch names and last commit SHA.
Designed as a lightweight proxy over the GitHub REST API, following clean architecture and industry best practices.<br>
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

### User Not Found Response (404)
```json
{

  "status": 404,
  
  "message": "User not found"
  
}
```

### Architecture

- Controller: GithubRepositoryController

- Service: GithubRepositoryService

- Client: GithubClient

*Single package. No DTO/domain split. No security. No pagination. No caching. No resilience. No WebFlux.*<br>
<br>

### Testing

- **Only integration tests**

- Tools used:

  - @SpringBootTest

  - MockMvc

  - **WireMock**

**Test class:** GithubRepositoriesProxyIT<br>
<br>

### Running the Application  
Open a terminal in the root folder of the project (where build.gradle.kts is located) or use the built-in terminal in IntelliJ IDEA, then run:<br>
<br>
./gradlew build

./gradlew bootRun<br>
<br>

The application will be available at:

http://localhost:8080<br>
<br>

### Author

Artur Sobczak  
sobczak.artur88@gmail.com

### LinkedIn
https://www.linkedin.com/in/artur-sobczak-03724a175/


<br><br>
##Example of operations:##

![](https://github.com/SobczakArtur/github-repositories-proxy/blob/main/images/github_app%20(1).JPG?raw=true)
<br><br>
![](https://github.com/SobczakArtur/github-repositories-proxy/blob/main/images/github_app%20(2).JPG?raw=true)
<br><br>
![](https://github.com/SobczakArtur/github-repositories-proxy/blob/main/images/github_app%20(3).JPG?raw=true)

