package com.javaass.jpa.repository;

import com.javaass.jpa.dto.OrderResponse;
import com.javaass.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query("SELECT new com.javaass.jpa.dto.OrderResponse( c.name, p.productName)FROM Customer c JOIN c.products p")
    public List<OrderResponse> getJoinInformation();
}
