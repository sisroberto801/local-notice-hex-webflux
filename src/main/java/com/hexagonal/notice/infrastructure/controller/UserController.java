package com.hexagonal.notice.infrastructure.controller;

import com.hexagonal.notice.application.service.UserService;
import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.model.in.UpdateUserPayload;
import com.hexagonal.notice.domain.model.in.UserPayload;
import com.hexagonal.notice.infrastructure.exception.UserNotFoundException;
import com.hexagonal.notice.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<User>> createUser(@RequestBody UserPayload request) {
        return userService.createUser(userMapper.createFromPayload(request))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserPayload request
    ) {
        return userService.updateUser(id, userMapper.updateFromPayload(request))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}