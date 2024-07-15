package com.example.spring.enums;

import java.io.Serializable;

public interface BaseEnum<T> extends Serializable {
    Integer getCode();

    String getMessage();
}
