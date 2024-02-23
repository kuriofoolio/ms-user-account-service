package com.safaricom.msuseraccountservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;

import com.safaricom.msuseraccountservice.dto.UserAccountResponseDTO;
import com.safaricom.msuseraccountservice.exceptions.InsufficientFundsException;
import com.safaricom.msuseraccountservice.exceptions.UserNotActiveException;
import com.safaricom.msuseraccountservice.exceptions.UserNotDeactivatedException;
import com.safaricom.msuseraccountservice.exceptions.UserNotFoundException;
import com.safaricom.msuseraccountservice.exceptions.WithdrawalMultipleException;

@ControllerAdvice
public class UserAccountExceptionsAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserAccountResponseDTO> userNotFoundHandler(UserNotFoundException ex) {
        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.NOT_FOUND.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userAccountResponseDTO);
    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<UserAccountResponseDTO> userNotActiveHandler(UserNotActiveException ex) {
        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.FORBIDDEN.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userAccountResponseDTO);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<UserAccountResponseDTO> insufficientFundsHandler(InsufficientFundsException ex) {
        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.FORBIDDEN.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userAccountResponseDTO);
    }

    @ExceptionHandler(WithdrawalMultipleException.class)
    public ResponseEntity<UserAccountResponseDTO> withdrawalMultipleHandler(WithdrawalMultipleException ex) {

        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userAccountResponseDTO);
    }

    @ExceptionHandler(UserNotDeactivatedException.class)
    public ResponseEntity<UserAccountResponseDTO> userNotDeactivatedHandler(UserNotDeactivatedException ex) {

        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.FORBIDDEN.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userAccountResponseDTO);
    }

    // handle other exceptions that are not caught by specific handlers
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<?> internalServerErrorHandler(InternalServerError err, WebRequest request) {

        // String errorMessage = "Sorry, an unexpected error occurred!";
        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseDescription("error")
                .responseSummary(err.getMessage())
                .build();

        // if (env.getProperty("app.environment").equalsIgnoreCase("dev")) {
        // errorMessage += "\nDetails:\n" + ex.getMessage();
        // }

        // ErrorDetails errorDetails = new ErrorDetails(errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userAccountResponseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleAllExceptions(HttpMessageNotReadableException ex, WebRequest request) {

        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()

                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userAccountResponseDTO);
    }

}
