package com.hexagonal.notice.infrastructure.controller;

import com.hexagonal.notice.application.service.UserService;
import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.infrastructure.dto.UserRequest;
import com.hexagonal.notice.infrastructure.dto.UserResponse;
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

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUser(@RequestBody UserRequest request) {
        User user = userMapper.toUserFromRequest(request);
        return userService.createUser(user)
                .map(userMapper::toUserResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userMapper::toUserResponse)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new com.hexagonal.notice.infrastructure.exception.UserNotFoundException("User not found")));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('USER')")
    public Flux<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .map(userMapper::toUserResponse);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Token")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        User user = userMapper.toUserFromRequest(request);
        return userService.updateUser(id, user)
                .map(userMapper::toUserResponse)
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