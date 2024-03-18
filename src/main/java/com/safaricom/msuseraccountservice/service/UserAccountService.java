package com.safaricom.msuseraccountservice.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safaricom.msuseraccountservice.dto.HouseRequestDTO;
import com.safaricom.msuseraccountservice.dto.UserAccountRequest;
import com.safaricom.msuseraccountservice.dto.UserAccountResponseDTO;
import com.safaricom.msuseraccountservice.exceptions.InsufficientFundsException;
import com.safaricom.msuseraccountservice.exceptions.UserNotActiveException;
import com.safaricom.msuseraccountservice.exceptions.UserNotDeactivatedException;
import com.safaricom.msuseraccountservice.exceptions.UserNotFoundException;
import com.safaricom.msuseraccountservice.exceptions.HouseNotFoundException;
import com.safaricom.msuseraccountservice.exceptions.WithdrawalMultipleException;
import com.safaricom.msuseraccountservice.model.House;
import com.safaricom.msuseraccountservice.model.UserAccount;
import com.safaricom.msuseraccountservice.repository.HouseRepository;
import com.safaricom.msuseraccountservice.repository.UserAccountRepository;

@Service
public class UserAccountService {
        // private final UserAccountRepository userAccountRepository;

        private UserAccountRepository userAccountRepository;
        private HouseRepository houseRepository;

        // service autowires from the repository
        @Autowired
        public void setRepository(UserAccountRepository userAccountRepository) {
                this.userAccountRepository = userAccountRepository;

        }

        @Autowired
        public void setRepository(HouseRepository houseRepository) {
                this.houseRepository = houseRepository;

        }

        public ResponseEntity<UserAccountResponseDTO> createHouseDetails(HouseRequestDTO houseRequestDTO)
                        throws URISyntaxException {
                House house = House.builder()
                                .houseName(houseRequestDTO.getHousename())
                                .build();

                House newHouse = houseRepository.save(house);

                UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()
                                .responseCode(1000)
                                .responseDescription("success")
                                .responseSummary("House added successfully")
                                .build();

                return ResponseEntity.created(new URI("/api/v1/houses/" + newHouse.getHouseId()))
                                .body(userAccountResponseDTO);

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

                // Resolve House object from houseId
                House house = houseRepository.findById(userAccountRequest.getHouseid())
                                .orElseThrow(() -> new HouseNotFoundException(userAccountRequest.getHouseid()));

                UserAccount userAccount = UserAccount.builder()

                                .userName(userAccountRequest.getUsername())
                                .active(userAccountRequest.getActive())
                                .balance(userAccountRequest.getBalance())
                                .house(house) // Set resolved House object
                                .createdAt(LocalDateTime.now())

                                .build();

                UserAccount newUserAccount = userAccountRepository.save(userAccount);

                UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()
                                .responseCode(1000)
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

                // which is better
                // return ResponseEntity.ok().body(foundUserAccount);
                return ResponseEntity.status(HttpStatus.FOUND.value()).body(foundUserAccount);
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
                                                        .responseCode(1000)
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

        /**
         * 
         * @param userAccount
         * @return ResponseEntity<UserAccountResponseDTO>
         * @version 1.0.0
         * @since 1.0.0
         * @apiNote This method is used to update a user account in the database. It
         *          takes an instance of UserAccount as input and returns a response
         *          entity containing a UserAccountResponseDTO
         * @exception UserNotActiveException
         * @throws UserNotActiveException
         */
        public ResponseEntity<UserAccountResponseDTO> updateUserAccountDetails(UserAccount userAccount)
                        throws UserNotActiveException {
                UserAccount foundUserAccount = userAccountRepository.findByUserId(userAccount.getUserId())
                                .orElseThrow(() -> new UserNotFoundException(userAccount.getUserId()));

                if (foundUserAccount.isActive()) {
                        foundUserAccount.setUserName(userAccount.getUserName());

                        foundUserAccount.setBalance(userAccount.getBalance());

                        // create a new field for modified at and set it to current time
                        foundUserAccount.setModifiedAt(LocalDateTime.now());

                        foundUserAccount = userAccountRepository.save(foundUserAccount);

                        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()
                                        .responseCode(1000)
                                        .responseDescription("success")
                                        .responseSummary("User account updated successfully")
                                        .build();

                        return ResponseEntity.ok().body(userAccountResponseDTO);
                }

                else {
                        throw new UserNotActiveException(userAccount.getUserId());
                }

        }

        /**
         * 
         * @param userid
         * @return ResponseEntity<UserAccountResponseDTO>
         * @version 1.0.0
         * @since 1.0.0
         * @apiNote This method is used to delete a user account from the database. It
         *          takes a userid as input and returns a response entity containing a
         *          UserAccountResponseDTO
         * @exception UserNotDeactivatedException
         * @throws UserNotDeactivatedException
         */
        public ResponseEntity<UserAccountResponseDTO> deleteUserAccount(Long userid)

                        throws UserNotFoundException, UserNotDeactivatedException {
                UserAccount foundUserAccount = userAccountRepository.findByUserId(userid)
                                .orElseThrow(() -> new UserNotFoundException(userid));

                if (!foundUserAccount.isActive()) {
                        userAccountRepository.delete(foundUserAccount);

                        UserAccountResponseDTO userAccountResponseDTO = UserAccountResponseDTO.builder()
                                        .responseCode(1000)
                                        .responseDescription("success")
                                        .responseSummary("User account deleted successfully")
                                        .build();

                        return ResponseEntity.ok().body(userAccountResponseDTO);
                }

                else {
                        throw new UserNotDeactivatedException(userid);

                }

        }

}

// comments
// the work of a service is to build the logic
// controller just calls the methods
// open
// use this when youre very sure account exists
// UserAccount foundUserAccount =
// userAccountRepository.findByUserId(userid).get()
