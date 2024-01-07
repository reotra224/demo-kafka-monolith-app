package com.reotra.demomonolith.common.dto;

import lombok.Builder;

@Builder
public record GenericResponse<T>(boolean success, String message, T data) {

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message("SUCCESS")
                .data(data)
                .success(true)
                .build();
    }

    public static <T> GenericResponse<T> error(String message) {
        return GenericResponse.<T>builder()
                .message(message)
                .success(false)
                .build();
    }
}
