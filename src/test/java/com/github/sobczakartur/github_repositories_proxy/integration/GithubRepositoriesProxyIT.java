package com.github.sobczakartur.github_repositories_proxy.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
class GithubRepositoriesProxyIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNonForkRepositoriesForExistingUser() throws Exception {

        // GIVEN
        stubFor(get(urlEqualTo("/users/test-user/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        [
                          {
                            "name": "forked-repo",
                            "fork": true,
                            "owner": { "login": "test-user" }
                          },
                          {
                            "name": "backend-repo",
                            "fork": false,
                            "owner": { "login": "test-user" }
                          }
                        ]
                        """)));

        stubFor(get(urlEqualTo("/repos/test-user/backend-repo/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        [
                          {
                            "name": "main",
                            "commit": {
                              "sha": "abc123"
                            }
                          }
                        ]
                        """)));

        // WHEN + THEN
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .get("/repositories/test-user")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))

                .andExpect(jsonPath("$.length()").value(1))

                .andExpect(jsonPath("$[0].repositoryName").value("backend-repo"))
                .andExpect(jsonPath("$[0].ownerLogin").value("test-user"))

                .andExpect(jsonPath("$[0].branches[0].name").value("main"))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value("abc123"));
    }


    @Test
    void shouldReturn404WhenGithubUserDoesNotExist() throws Exception {
        // GIVEN
        stubFor(get(urlEqualTo("/users/unknown-user/repos"))
                .willReturn(aResponse()
                        .withStatus(404)));

        // WHEN + THEN
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/repositories/unknown-user"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));;
    }
}
