package com.readingisgood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.readingisgood.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String>
{
    Page<Order> findAllByEmail(String email, Pageable pageable);
}
