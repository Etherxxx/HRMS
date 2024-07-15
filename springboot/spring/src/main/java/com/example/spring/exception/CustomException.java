package com.example.spring.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String msg;

    public CustomException(String msg) {
        this.msg = msg;
    }

}
