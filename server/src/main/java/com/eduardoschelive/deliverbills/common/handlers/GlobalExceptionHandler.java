package com.eduardoschelive.deliverbills.common.handlers;

import com.eduardoschelive.deliverbills.common.exception.ApplicationException;
import com.eduardoschelive.deliverbills.common.helper.ApplicationExceptionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApplicationExceptionHelper applicationExceptionHelper;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationError(ApplicationException applicationError) {
        return this.applicationExceptionHelper.buildResponseEntity(applicationError);
    }

}
