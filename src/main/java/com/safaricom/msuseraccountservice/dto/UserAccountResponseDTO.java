package com.safaricom.msuseraccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is a DTO that we use to respond to client after successful addition of a new user account
 * @version 1.0.0
 * @author kuria
 * @since 1.0.0
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class UserAccountResponseDTO {
    private int responseCode;
    private String responseDescription;
    private String responseSummary;
}
