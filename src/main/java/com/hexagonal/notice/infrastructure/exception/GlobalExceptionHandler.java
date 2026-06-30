package com.hexagonal.notice.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<?>> fatalErrorUnexpectedException(Exception e, ServerWebExchange exchange) {
        log.error("internal server {}", exchange.getRequest().getURI().getPath(), e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage data = new ErrorMessage(e, exchange.getRequest().getURI().getPath(), status.value());
        return Mono.just(new ResponseEntity<>(data, status));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<?>> badRequest(Exception e, ServerWebExchange exchange) {
        log.info("bad request {}", exchange.getRequest().getURI().getPath());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage data = new ErrorMessage(e, exchange.getRequest().getURI().getPath(), status.value());
        return Mono.just(new ResponseEntity<>(data, status));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<?>> notFound(UserNotFoundException e, ServerWebExchange exchange) {
        log.info("user not found {}", exchange.getRequest().getURI().getPath());
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage data = new ErrorMessage(e, exchange.getRequest().getURI().getPath(), status.value());
        return Mono.just(new ResponseEntity<>(data, status));
    }
}
