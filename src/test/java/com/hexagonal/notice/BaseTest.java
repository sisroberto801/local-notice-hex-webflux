package com.hexagonal.notice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

public abstract class BaseTest {

    @LocalServerPort
    protected int port;
    @Autowired
    protected DatabaseClient databaseClient;

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
}