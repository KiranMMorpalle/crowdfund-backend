package com.crowdfund.backend.common.exception;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 2
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(BusinessValidationException ex, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    // 3
    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ErrorResponse>  handleUnauthorized(UnauthorizedOperationException ex, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // ⭐ NEW — validation error handler (this fixes your test)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                "Validation failed",
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 4
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Something went wrong",
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 5
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockingFailure(
            ObjectOptimisticLockingFailureException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                409,
                "CONFLICT",
                "Concurrent update detected. Please retry.",
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(error);
    }
}



//package com.crowdfund.backend.common.exception;
//
//
////import com.crowdfund.backend.common.exception.ErrorResponse;
//import jakarta.persistence.OptimisticLockException;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.orm.ObjectOptimisticLockingFailureException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // 1
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request){
//
//        ErrorResponse response = new ErrorResponse(
//                HttpStatus.NOT_FOUND.value(),
//                "NOT_FOUND",
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    // 2
//    @ExceptionHandler(BusinessValidationException.class)
//    public ResponseEntity<ErrorResponse> handleValidation(BusinessValidationException ex, HttpServletRequest request){
//
//        ErrorResponse response = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "BAD_REQUEST",
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//    }
//
//
//    // 3
//    @ExceptionHandler(UnauthorizedOperationException.class)
//    public ResponseEntity<ErrorResponse>  handleUnauthorized(UnauthorizedOperationException ex, HttpServletRequest request){
//
//        ErrorResponse response = new ErrorResponse(
//            HttpStatus.FORBIDDEN.value(),
//            "FORBIDDEN",
//            ex.getMessage(),
//            request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//    }
//
//
//    // 4
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex, HttpServletRequest request){
//
//        ErrorResponse response = new ErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "INTERNAL_SERVER_ERROR",
//                "Something went wrong",
//                request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//    // 5
//    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
//    public ResponseEntity<ErrorResponse> handleOptimisticLockingFailure(
//            ObjectOptimisticLockingFailureException ex,
//            HttpServletRequest request) {
//
//        ErrorResponse error = new ErrorResponse(
//                409,
//                "CONFLICT",
//                "Concurrent update detected. Please retry.",
//                request.getRequestURI()
//        );
//
//        return ResponseEntity.status(409).body(error);
//    }
//
//
//
//
//
//}
