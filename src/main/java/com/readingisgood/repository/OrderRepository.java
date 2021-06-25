package com.readingisgood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.readingisgood.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String>
{
    Page<Order> findAllByEmail(String email, Pageable pageable);
    
    List<Order> findByOrderDateBetween(long startDate, long endDate);
}
