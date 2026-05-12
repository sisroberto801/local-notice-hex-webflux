package com.hexagonal.notice;

import com.hexagonal.notice.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.profiles.active=test"
)
class NoticeApplicationTests extends BaseTest {


    @Test
    void contextLoads() {
    }

    @Test
    void testDatabaseConnection() {
        StepVerifier.create(databaseClient.sql("SELECT 1 as result").fetch().one())
                .expectNextMatches(row -> row.containsKey("result") && row.get("result").equals(1))
                .verifyComplete();
    }

    @Test
    void testUsersTableExists() {
        StepVerifier.create(databaseClient.sql("SELECT COUNT(*) as count FROM users").fetch().one())
                .expectNextMatches(row -> row.containsKey("count"))
                .verifyComplete();
    }

    @Test
    void testFindUserById() {
        webTestClient.get()
                .uri("/api/users/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("UserNotFoundException")
                .jsonPath("$.message").isEqualTo("User not found")
                .jsonPath("$.path").isEqualTo("/api/users/1");
    }

    @Test
    void testCreateUser() {
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

                    webTestClient.get()
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