package com.safaricom.msuseraccountservice.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safaricom.msuseraccountservice.dto.UserAccountRequest;
import com.safaricom.msuseraccountservice.dto.UserAccountResponseDTO;
import com.safaricom.msuseraccountservice.exceptions.InsufficientFundsException;
import com.safaricom.msuseraccountservice.exceptions.UserNotActiveException;
import com.safaricom.msuseraccountservice.exceptions.UserNotFoundException;
import com.safaricom.msuseraccountservice.exceptions.WithdrawalMultipleException;
import com.safaricom.msuseraccountservice.model.UserAccount;
import com.safaricom.msuseraccountservice.repository.UserAccountRepository;

@Service
public class UserAccountService {
        // private final UserAccountRepository userAccountRepository;

        private UserAccountRepository userAccountRepository;

        // service autowires from the repository
        @Autowired
        public void setRepository(UserAccountRepository userAccountRepository) {
                this.userAccountRepository = userAccountRepository;
        }

        /**
         * 
         * @param userAccountRequest
         * @version 1.0.0
         * @since 1.0.0
         * @return ResponseEntity<UserAccountResponseDTO>
         * @apiNote This method is used to add a new user account to the database
         * @exception URISyntaxException
         * @throws URISyntaxException
         */
        public ResponseEntity<UserAccountResponseDTO> addUserAccount(UserAccountRequest userAccountRequest)
                        throws URISyntaxException {

                UserAccount userAccount = UserAccount.builder()

                                .userName(userAccountRequest.getUsername())
                                .active(userAccountRequest.getActive())
                                .balance(userAccountRequest.getBalance())
                                .createdAt(LocalDateTime.now())
                                .build();

                UserAccount newUserAccount = userAccountRepository.save(userAccount);

                UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()
                                .responseCode("1000")
                                .responseDescription("success")
                                .responseSummary("User account added successfully")
                                .build();
                                // just incase uri isnt created well
                return ResponseEntity.created(new URI("/api/v1/users/" + newUserAccount.getUserId()))
                                .body(userAccountResponseDTO);

        }

        /**
         * 
         * @param userid
         * @version 1.0.0
         * @since 1.0.0
         * @return ResponseEntity<UserAccount>
         * @apiNote This method is used to retrieve a user account from the database
         * @exception UserNotFoundException
         * @throws UserNotFoundException
         */
        public ResponseEntity<UserAccount> getUserAccountDetailsById(Long userid) throws UserNotFoundException {
                // using this since not sure account exists
                UserAccount foundUserAccount = userAccountRepository.findByUserId(userid)
                                .orElseThrow(() -> new UserNotFoundException(userid));

                return ResponseEntity.ok().body(foundUserAccount);
        }

        /**
         * 
         * @return List<UserAccount>
         * @version 1.0.0
         * @since 1.0.0
         * @apiNote This method is used to retrieve all user accounts from the database
         */
        public List<UserAccount> getAllUserAccountDetails() {
                return userAccountRepository.findAll();
        }

        /**
         * 
         * @params userid, withdrawalamount
         * @version 1.0.0
         * @since 1.0.0
         * @return ResponseEntity<Double>
         * @apiNote This method is used to make a withdrawal
         * @exception Exception
         * @throws Exception
         */
        public ResponseEntity<UserAccountResponseDTO> makeWithdrawal(Long userid, double withdrawalamount)
                        throws UserNotActiveException, InsufficientFundsException, WithdrawalMultipleException {

                UserAccount foundUserAccount = userAccountRepository.findByUserId(userid)
                                .orElseThrow(() -> new UserNotFoundException(userid));

                double userAccountBalance = foundUserAccount.getBalance();

                if (foundUserAccount.isActive()) {

                        if (withdrawalamount <= userAccountBalance) {

                                if (withdrawalamount % 100 == 0) {
                                        double newBalance = userAccountBalance - withdrawalamount;

                                        // create functionality to overwrite user balance in database with newBalance
                                        foundUserAccount.setBalance(newBalance);
                                        foundUserAccount = userAccountRepository.save(foundUserAccount);

                                        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()
                                                        .responseCode("1000")
                                                        .responseDescription("success")
                                                        .responseSummary("Withdrawal successful")
                                                        .build();

                                        return ResponseEntity.ok().body(userAccountResponseDTO);
                                } else {

                                        throw new WithdrawalMultipleException();
                                }

                        } else {

                                throw new InsufficientFundsException();

                        }

                } else {

                        throw new UserNotActiveException(userid);

                }

        }

}

// comments
// the work of a service is to build the logic
// controller just calls the methods

// use this when youre very sure account exists
// UserAccount foundUserAccount =
// userAccountRepository.findByUserId(userid).get()

// create method for withdrawal functionality
// constraints: user should not withdraw more than the balance in their account,
// and they cannot withdraw negative amounts of money, withdrawal amount must be
// a multiple of 100 ,user must be active to withdraw
// public ResponseEntity<UserAccountResponseDTO>
// makeWithdrawal(UserAccountRequest userAccountRequest) {
