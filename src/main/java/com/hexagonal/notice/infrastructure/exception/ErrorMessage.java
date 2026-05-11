package com.hexagonal.notice.infrastructure.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorMessage {

    private Date timestamp;
    private String error;
    private String message;
    private String path;
    private Integer status;

    public ErrorMessage(Exception exception, String path, Integer status) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
        this.status = status;
        this.timestamp = new Date();
    }
}
