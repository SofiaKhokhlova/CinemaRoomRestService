package cinema.controller.advice;

import cinema.exception.AlreadyPurchasedException;
import cinema.exception.OutOfBoundsCoordinatesException;
import cinema.exception.WrongPasswordException;
import cinema.exception.WrongTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler({
            AlreadyPurchasedException.class,
            OutOfBoundsCoordinatesException.class,
            WrongTokenException.class,
    })
    ResponseEntity<ErrorDTO> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler({
            WrongPasswordException.class
    })

    ResponseEntity<ErrorDTO> handleException(WrongPasswordException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new ErrorDTO(ex.getMessage()));
    }



    record ErrorDTO(String error){}

}