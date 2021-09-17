package com.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Record duplicate")
public class RecordExistsException extends RuntimeException {

    public RecordExistsException(String message){
        super(message);
    }

}
