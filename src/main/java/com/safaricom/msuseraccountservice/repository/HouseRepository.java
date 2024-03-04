package com.safaricom.msuseraccountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safaricom.msuseraccountservice.model.House;
import com.safaricom.msuseraccountservice.model.UserAccount;

/**
 * 
 */
@Repository
public interface HouseRepository extends JpaRepository<House, String > {

    Optional<UserAccount> findByHouseName(House house);

    
}
