package com.safaricom.msuseraccountservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.safaricom.msuseraccountservice.dto.UserAccountResponseDTO;
import com.safaricom.msuseraccountservice.exceptions.InsufficientFundsException;
import com.safaricom.msuseraccountservice.exceptions.UserNotActiveException;
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
                // HttpStatus.BAD_REQUEST.value() may need to put this in response code
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseDescription("failed")
                .responseSummary(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userAccountResponseDTO);
    }
}
