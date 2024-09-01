package com.eduardoschelive.deliverbills.filter.exception;

import com.eduardoschelive.deliverbills.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NoSuchSortableFieldException extends ApplicationException {

    public NoSuchSortableFieldException(String fieldName) {
        super("The specified field is not sortable", fieldName);
    }

    @Override
    public String getMessageIdentifier() {
        return "filter.error.no_such_sortable_field";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "NO_SUCH_SORTABLE_FIELD";
    }

}
