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
import com.safaricom.msuseraccountservice.exceptions.WithdrawalMultipleException;
import com.safaricom.msuseraccountservice.model.UserAccount;
import com.safaricom.msuseraccountservice.service.UserAccountService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor
@AllArgsConstructor
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
    @GetMapping("/users/{userid}")
    public ResponseEntity<UserAccount> getUserAccountDetailsById(@PathVariable Long userid) {
        return userAccountService.getUserAccountDetailsById(userid);

    }

    /**
     * 
     * @return
     */
    @GetMapping("/users")
    public List<UserAccount> getAllUserAccountDetails() {
        return userAccountService.getAllUserAccountDetails();
    }

    @PutMapping("/users/withdraw/{userid}/{withdrawalamount}")
    public ResponseEntity<UserAccountResponseDTO> makeWithdrawal(@PathVariable Long userid,
            @PathVariable double withdrawalamount)
            throws UserNotActiveException, InsufficientFundsException, WithdrawalMultipleException {
        return userAccountService.makeWithdrawal(userid, withdrawalamount);
    }

    // @PutMapping
    // public String updateCloudVendorDetails(@RequestBody CloudVendor cloudVendor)
    // {
    // this.cloudVendor = cloudVendor;
    // return "updated";
    // }

    // @DeleteMapping("{vendorID}")
    // public String deletCloudVendorDetails(String vendorID) {
    // this.cloudVendor = null;
    // return "deleted";
    // }
}
