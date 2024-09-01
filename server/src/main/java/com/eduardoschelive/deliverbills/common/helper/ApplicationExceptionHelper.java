package com.eduardoschelive.deliverbills.common.helper;

import com.eduardoschelive.deliverbills.common.dto.ApplicationExceptionResponseDTO;
import com.eduardoschelive.deliverbills.common.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ApplicationExceptionHelper {

    private final MessageHelper messageHelper;
    private final HttpServletRequest httpServletRequest;

    public ApplicationExceptionResponseDTO buildResponseDTO(ApplicationException exception) {
        var httpStatus = exception.getHttpStatus();
        var placeholders = exception.getPlaceholders();
        var messageIdentifier = exception.getMessageIdentifier();
        var errorCode = exception.getErrorCode();

        return this.buildResponseDTO(httpStatus, messageIdentifier, errorCode, placeholders);
    }

    public ApplicationExceptionResponseDTO buildResponseDTO(HttpStatus httpStatus, String messageIdentifier, String errorCode, Object... placeholders) {
        var message = this.messageHelper.getMessage(messageIdentifier);

        if (placeholders != null) {
            for (int i = 0; i < placeholders.length; i++) {
                message = message.replace("{" + i + "}", String.valueOf(placeholders[i]));
            }
        }

        return new ApplicationExceptionResponseDTO(
                httpStatus.value(),
                errorCode,
                message,
                Instant.now(),
                httpServletRequest.getRequestURI()
        );
    }

    public ResponseEntity<Object> buildResponseEntity(ApplicationException exception) {
        var responseDTO = this.buildResponseDTO(exception);
        return new ResponseEntity<>(responseDTO, exception.getHttpStatus());
    }

}
