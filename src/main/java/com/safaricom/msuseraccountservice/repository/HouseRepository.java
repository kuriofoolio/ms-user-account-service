package com.safaricom.msuseraccountservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safaricom.msuseraccountservice.model.House;

/**
 * 
 */
@Repository
public interface HouseRepository extends JpaRepository<House, Long > {

    

    
}
