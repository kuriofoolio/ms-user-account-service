package com.safaricom.msuseraccountservice.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.safaricom.msuseraccountservice.model.enums.MyEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class UserAccountRequest {

    @Builder.Default
    private String username = "";

    @Builder.Default
    private MyEnum active = MyEnum.values()[0];

    @Builder.Default
    private double balance = 0.0;

    // private House house;
    // private Long houseid;

    // specify that no other field names other than the above are allowed in the
    // request
    @JsonAnySetter(enabled = true)
    public void setOtherField(String key, Object value) {
        throw new IllegalArgumentException("Field " + key + " is not allowed in the request body");
    }

}