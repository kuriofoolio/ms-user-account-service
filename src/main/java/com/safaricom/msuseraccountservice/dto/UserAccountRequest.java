package com.safaricom.msuseraccountservice.dto;

import com.safaricom.msuseraccountservice.model.enums.MyEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data

public class UserAccountRequest {

    // create code for the data transfer object above based on the attributes
    // specified in the user model

    // this is what is going to be passed in the request body to get user details
    // and retrieve info from db
    // private Long userid;
    private String username;
    private MyEnum active;
    private double balance;

}
