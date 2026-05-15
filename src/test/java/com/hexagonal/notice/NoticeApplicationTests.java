package com.hexagonal.notice;

import com.hexagonal.notice.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.profiles.active=test"
)
class NoticeApplicationTests extends BaseTest {

    @Test
    @DisplayName("Should connect to database successfully when application starts")
    void databaseConnection_shouldConnectSuccessfully_whenApplicationStarts() {
        StepVerifier.create(databaseClient.sql("SELECT 1 as result").fetch().one())
                .expectNextMatches(row -> row.containsKey("result") && row.get("result").equals(1))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should find users table when database schema is loaded")
    void usersTable_shouldExist_whenDatabaseSchemaIsLoaded() {
        StepVerifier.create(databaseClient.sql("SELECT COUNT(*) as count FROM users").fetch().one())
                .expectNextMatches(row -> row.containsKey("count"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return 404 error when user does not exist")
    void findUserById_shouldReturnNotFoundError_whenUserDoesNotExist() {
        String token = createTestUserAndGetToken("testuser", "password123");
        WebTestClient authenticatedClient = webTestClientWithAuth(token);

        authenticatedClient.get()
                .uri("/api/users/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("UserNotFoundException")
                .jsonPath("$.message").isEqualTo("User not found")
                .jsonPath("$.path").isEqualTo("/api/users/999");
    }

    @Test
    @DisplayName("Should create and retrieve user successfully when valid data is provided")
    void createUser_shouldCreateAndRetrieveUser_whenValidDataIsProvided() {
        User user = User.builder()
                .username("testuser")
                .password("password123")
                .status(true)
                .build();

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .consumeWith(created -> {

                    User userCreated = created.getResponseBody();

                    assertNotNull(userCreated);
                    assertNotNull(userCreated.getId());
                    assertEquals(user.getUsername(), userCreated.getUsername());

                    String token = createTestUserAndGetToken("authuser", "password123");
                    WebTestClient authenticatedClient = webTestClientWithAuth(token);

                    authenticatedClient.get()
                            .uri("/api/users/" + userCreated.getId())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(User.class)
                            .consumeWith(retrieved -> {

                                User userRetrieved = retrieved.getResponseBody();

                                assertNotNull(userRetrieved);
                                assertEquals(userCreated.getId(), userRetrieved.getId());
                            });
                });
    }
}