package com.hexagonal.notice;

import com.hexagonal.notice.infrastructure.config.JwtUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

public abstract class BaseTest {

    @LocalServerPort
    protected int port;
    @Autowired
    protected DatabaseClient databaseClient;
    @Autowired
    protected JwtUtil jwtUtil;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected ReactiveUserDetailsService userDetailsService;

    private static final String BASE_URL = "http://localhost";
    protected WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        webTestClient = WebTestClient.bindToServer()
                .baseUrl(BASE_URL + ":" + port)
                .build();

        setupDatabase();
    }

    private void setupDatabase() {

        StepVerifier.create(databaseClient.sql("DELETE FROM task_assignments").then()).verifyComplete();
        StepVerifier.create(databaseClient.sql("DELETE FROM tasks").then()).verifyComplete();
        StepVerifier.create(databaseClient.sql("DELETE FROM profiles").then()).verifyComplete();
        StepVerifier.create(databaseClient.sql("DELETE FROM users").then()).verifyComplete();
    }

    protected String createTestUserAndGetToken(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        StepVerifier.create(
                databaseClient.sql("INSERT INTO users (username, password, status) VALUES ($1, $2, $3)")
                        .bind("$1", username)
                        .bind("$2", encodedPassword)
                        .bind("$3", true)
                        .then()
        ).verifyComplete();

        UserDetails userDetails = userDetailsService.findByUsername(username).block();
        return jwtUtil.generateToken(userDetails);
    }

    protected WebTestClient webTestClientWithAuth(String token) {
        return WebTestClient.bindToServer()
                .baseUrl(BASE_URL + ":" + port)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }
}