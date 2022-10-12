package com.zihenx.dsclient.controllers.exceptions;

import com.zihenx.dsclient.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<InfoError> errors = new ArrayList<>();

        for (ObjectError err : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError)err).getField();
            String message = err.getDefaultMessage();

            errors.add(new InfoError(name, message));
        }

        var erroMessage = new StandardError(
                Instant.now(),
                status.value(),
                "Field Empty",
                "One or more fields were filled in incorrectly, please check the information given and try again",
                ((ServletWebRequest)request).getRequest().getRequestURI().toString(),
                errors
        );

        return handleExceptionInternal(ex, erroMessage ,headers, status, request);
    }

    @ExceptionHandler
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError err = new StandardError();
        err.setTimeStamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
