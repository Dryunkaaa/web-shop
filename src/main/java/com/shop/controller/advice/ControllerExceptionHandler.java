package com.shop.controller.advice;

import com.shop.controller.admin.CategoryController;
import com.shop.controller.admin.GoodController;
import com.shop.controller.admin.GoodTypeController;
import com.shop.controller.admin.ManufacturerController;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {CategoryController.class, GoodTypeController.class, GoodController.class, ManufacturerController.class})
public class ControllerExceptionHandler {

    @ExceptionHandler({InvalidDataException.class, RecordExistsException.class})
    public ResponseEntity<String> handleFillingError(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
