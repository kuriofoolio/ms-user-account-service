package com.safaricom.msuseraccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class HouseRequestDTO {
    @Builder.Default
    private String housename = "";

}
