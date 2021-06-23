package com.readingisgood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.readingisgood.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>
{
    public Customer findByEmail(String email);

}
