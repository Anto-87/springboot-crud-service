package com.srlite.srlitespringservice.repository;

import java.util.List;

import com.srlite.srlitespringservice.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByName(String name);

    
}
