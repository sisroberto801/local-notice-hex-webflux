package com.hexagonal.notice.infrastructure.security;

import com.hexagonal.notice.infrastructure.config.JwtUtil;
import com.hexagonal.notice.infrastructure.exception.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username;

            try {
                username = jwtUtil.extractUsername(token);
            } catch (ExpiredJwtException e) {
                return Mono.error(new JwtAuthenticationException("Token has expired"));
            } catch (Exception e) {
                return Mono.error(new JwtAuthenticationException("Invalid token"));
            }

            if (username != null) {
                return userDetailsService.findByUsername(username)
                        .filter(userDetails -> jwtUtil.validateToken(token, userDetails))
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        ))
                        .flatMap(auth -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
                        .onErrorResume(e -> chain.filter(exchange));
            }
        }

        return chain.filter(exchange);
    }
}
