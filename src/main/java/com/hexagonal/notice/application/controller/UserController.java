package com.hexagonal.notice.application.controller;

import com.hexagonal.notice.application.service.UserService;
import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.infrastructure.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User Controller
 * <p>
 * Handles user-related endpoints with JWT authentication.
 * Provides CRUD operations for user management.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('USER')")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}