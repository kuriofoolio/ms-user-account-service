package com.safaricom.msuseraccountservice.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safaricom.msuseraccountservice.dto.UserAccountRequest;
import com.safaricom.msuseraccountservice.dto.UserAccountResponseDTO;
import com.safaricom.msuseraccountservice.exceptions.InsufficientFundsException;
import com.safaricom.msuseraccountservice.exceptions.UserNotActiveException;
import com.safaricom.msuseraccountservice.exceptions.UserNotDeactivatedException;
import com.safaricom.msuseraccountservice.exceptions.UserNotFoundException;
import com.safaricom.msuseraccountservice.exceptions.WithdrawalMultipleException;
import com.safaricom.msuseraccountservice.model.UserAccount;
import com.safaricom.msuseraccountservice.service.UserAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// change this
@Tag(name = "User Account Controller", description = "User Account Controller for User Account Service")
@RestController
@RequestMapping("/api/v1")
public class UserAccountController {

        UserAccount userAccount;
        private UserAccountService userAccountService;

        // controller autowires from the service
        @Autowired
        public void setService(UserAccountService userAccountService) {
                this.userAccountService = userAccountService;
        }

        /**
         * 
         * @param userAccountRequest
         * @return
         * @throws URISyntaxException
         */

        @Operation(summary = "Create a user account", description = "Create a user account object by specifying its id, username, createdat, active, and balance. The response is User Account object with  id, username, createdat, active, and balance.", tags = {
                        "Add a user account" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
                        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
        @PostMapping("/users")
        public ResponseEntity<UserAccountResponseDTO> addUserAccount(@RequestBody UserAccountRequest userAccountRequest)
                        throws URISyntaxException {
                return userAccountService.addUserAccount(userAccountRequest);
        }

        /**
         * 
         * @param userid
         * @return
         */

        @Operation(summary = "Get user account details by ID", description = "Get a user account object by specifying its id. The response is User Account object with  id, username, createdat, active, and balance.", tags = {
                        "Get a user account" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UserAccount.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
        })
        @GetMapping("/users/{userid}")
        public ResponseEntity<UserAccount> getUserAccountDetailsById(@PathVariable Long userid) {
                return userAccountService.getUserAccountDetailsById(userid);

        }

        /**
         * @apiNote Get all user accounts
         * @param userAccountRequest
         * @since 1.0
         * @version 1.0
         * @return
         */

        @Operation(summary = "Get all user accounts details", description = "Get all user account objects. The response is a list of User Account objects with  id, username, createdat, active, and balance.", tags = {
                        "Get user accounts" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),

        })
        @GetMapping("/users")
        public List<UserAccount> getAllUserAccountDetails() {
                return userAccountService.getAllUserAccountDetails();
        }

        /**
         * 
         * @param userid
         * @param withdrawalamount
         * @throws UserNotActiveException
         * @throws InsufficientFundsException
         * @throws WithdrawalMultipleException
         * @return UserAccountResponseDTO
         */

        @Operation(summary = "Make withdrawal", description = "Make a withdrawal by specifying a user's id and withdrawal amount. The response is UserAccountResponseDTO", tags = {
                        "Withdraw from user account" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),

                        @ApiResponse(responseCode = "400", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "403", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }) })
        @PutMapping("/users/withdraw/{userid}/{withdrawalamount}")
        public ResponseEntity<UserAccountResponseDTO> makeWithdrawal(@PathVariable Long userid,
                        @PathVariable double withdrawalamount)
                        throws UserNotActiveException, InsufficientFundsException, WithdrawalMultipleException {
                return userAccountService.makeWithdrawal(userid, withdrawalamount);
        }

        /**
         * @param useraccount
         * @version 1.0
         * @since 1.0
         * @apiNote Update User Account Details by specifiying new details for username,
         *          active, and balance fields in the request body. If a field is not
         *          specified, it will not be updated.
         * @see UserAccount
         * @see UserAccountResponseDTO
         * @throws UserNotActiveException
         * @return UserAccountResponseDTO
         */
        @Operation(summary = "Update User Account Details", description = "Update User Account Details by specifiying new details for username, active, and balance", tags = {
                        "Update user account" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),

                        @ApiResponse(responseCode = "400", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "403", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }) })
        @PutMapping("/users/update")
        public ResponseEntity<UserAccountResponseDTO> updateUserAccountDetails(
                        @RequestBody UserAccount useraccount)
                        throws UserNotActiveException {

                return userAccountService.updateUserAccountDetails(useraccount);

        }

        /**
         * @param userid
         * @version 1.0
         * @since 1.0
         * @apiNote Delete a user account by specifying a valid user id and dropping the
         *          item from the database
         * @throws UserNotDeactivatedException
         * @throws UserNotFoundException
         * @return UserAccountResponseDTO
         */
        @Operation(summary = "Delete a user account", description = "Delete a user account by specifying a valid user id and dropping the item from the database", tags = {
                        "Delete a user account" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),

                        @ApiResponse(responseCode = "400", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "403", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(schema = @Schema(implementation = UserAccountResponseDTO.class), mediaType = "application/json") }) })
        @DeleteMapping("/users/delete/{userid}")
        public ResponseEntity<UserAccountResponseDTO> deleteUserAccount(
                        @PathVariable Long userid) throws UserNotFoundException, UserNotDeactivatedException {

                return userAccountService.deleteUserAccount(userid);

        }

}
