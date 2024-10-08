package com.eduardoschelive.deliverbills.filter.exception;

import com.eduardoschelive.deliverbills.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.eduardoschelive.deliverbills.filter.utils.FilterConstants.MAX_SIZE;

public class PageSizeFormatException extends ApplicationException {

    public PageSizeFormatException(String parameterName) {
        super("The specified page size is invalid or higher than the maximum size defined by the server", MAX_SIZE, parameterName);
    }

    @Override
    public String getMessageIdentifier() {
        return "filter.error.invalid_page_size_number_format";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "INVALID_PAGE_SIZE_NUMBER_FORMAT";
    }

}
