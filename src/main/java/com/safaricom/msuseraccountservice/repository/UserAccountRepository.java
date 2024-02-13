package com.safaricom.msuseraccountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safaricom.msuseraccountservice.model.UserAccount;

/**
 * 
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * @param userid
     * @return Optional<UserAccount> if found the account for given user id otherwise throw UserNotFoundException
     */
    Optional<UserAccount> findByUserId(Long userid);
   
}