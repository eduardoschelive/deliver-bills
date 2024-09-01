package com.eduardoschelive.deliverbills.common.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageHelper {

    private final MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.of("pt-BR"));
    }

}
