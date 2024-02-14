package com.safaricom.msuseraccountservice.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.safaricom.msuseraccountservice.model.enums.MyEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tbl_user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private Long userId;

    @Column(name = "username")
    private String userName;

    @CreatedDate
    @Column(name = "createdat", nullable = false, updatable = false)
    @Generated(value = "INSERT")
    private LocalDateTime createdAt;

    @Column(name = "active")
    @Enumerated(EnumType.ORDINAL)
    private MyEnum active;

    @Column(name = "balance")
    private double balance;

    // public UserAccount() {
    // // Initialize any default values here if needed.

    // // this way not null and default values in db and model match
    // this.createdAt = LocalDateTime.now();
    // }

    // create a method to check whether user is active. user is active is active is
    // set to yes
    public boolean isActive() {
        return (this.getActive() == MyEnum.yes);
    }

}

// comments
// dont edit object, edit values
