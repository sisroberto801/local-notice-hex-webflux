package com.hexagonal.notice.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@RequiredArgsConstructor
public class JwtWebExceptionHandler implements WebExceptionHandler {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (!(ex instanceof JwtAuthenticationException)) {
            return Mono.error(ex);
        }

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorMessage data = new ErrorMessage((JwtAuthenticationException) ex, exchange.getRequest().getURI().getPath(), status.value());
        return errorResponseWriter.write(exchange, data, status);
    }
}
