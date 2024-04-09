package com.zee.ExpenseTracker.exception;

import com.zee.ExpenseTracker.entity.ErrorObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setTimeStamp(new Date());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex, WebRequest webRequest){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setTimeStamp(new Date());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleItemAlreadyExistsException(ItemAlreadyExistException ex, WebRequest webRequest){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setTimeStamp(new Date());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> generalizedException(Exception ex, WebRequest webRequest){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setTimeStamp(new Date());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,Object> body = new HashMap<String,Object>();
        body.put("status",HttpStatus.BAD_REQUEST.value());
        body.put("timeStamp",new Date());
        List<String> message = ex.getBindingResult()
                .getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
        body.put("message",message);
        return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST);
    }
}
