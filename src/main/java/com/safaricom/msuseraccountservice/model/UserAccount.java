package com.safaricom.msuseraccountservice.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @CreatedDate
    @Column(name = "modifiedat", nullable = true, updatable = true)
    @Generated(value = "INSERT")
    private LocalDateTime modifiedAt;

    // @ManyToOne
    // @JoinColumn(name = "houseid", referencedColumnName = "houseid", foreignKey = @jakarta.persistence.ForeignKey(name = "fk_houseid"))
    // private House house; 

    // @ManyToOne(targetEntity = House.class, fetch = FetchType.LAZY)
    // @JoinColumn(name = "house_name", referencedColumnName = "house_name",
    // nullable = false)

    // private House house;

    public boolean isActive() {
        return (this.getActive() == MyEnum.yes);
    }

}

// comments
// dont edit object, edit values
