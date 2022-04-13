package wolt.delivery_fee.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wolt.delivery_fee.domain.ErrorModel;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : ex.getAllErrors()) {
            if (sb.length() > 0)
                sb.append("; ");
            sb.append(error.getDefaultMessage());
        }
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Validation Error", sb.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}