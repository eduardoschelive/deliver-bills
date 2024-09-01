package com.eduardoschelive.deliverbills.common.exception;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    private final Object[] placeholders;

    protected ApplicationException(String message, Object... placeholders) {
        super(message);
        this.placeholders = placeholders;
    }

    public abstract String getErrorCode();

    public abstract String getMessageIdentifier();

    public abstract HttpStatus getHttpStatus();

    @Nullable
    public Object[] getPlaceholders() {
        return placeholders;
    }

}
