package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
import com.readingisgood.entity.Order;
import com.readingisgood.service.OrderService;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController
{
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
    
    private OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }
    
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewOrder(@Valid Order order)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        orderService.saveNewOrder();
        LOG.debug(ApplicationConstants.STEPOUT);
    }
    
    @GetMapping(value = "/{orderId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getOrderById()
    {
        LOG.debug(ApplicationConstants.STEPIN);
        orderService.getOrderById();
        LOG.debug(ApplicationConstants.STEPOUT);
    }
    
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getOrdersByDateRange(@RequestParam long startDate, @RequestParam long endDate)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        orderService.getOrdersByDateRange();
        LOG.debug(ApplicationConstants.STEPOUT);
    }
    
}
