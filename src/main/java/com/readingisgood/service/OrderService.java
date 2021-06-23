package com.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.readingisgood.entity.Order;
import com.readingisgood.repository.OrderRepository;

@Service
public class OrderService
{
    
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    public void saveNewOrder()
    {
        // TODO saveNewOrder       
    }

    public void getOrderById()
    {
        // TODO getOrderById
        
    }

    public void getOrdersByDateRange()
    {
        // TODO getOrdersByDateRange
        
    }

    public Page<Order> getOrderOfCustomerPageable(String email, Pageable pageable)
    {
        return this.orderRepository.findAllByEmail(email, pageable); 
    }

}
