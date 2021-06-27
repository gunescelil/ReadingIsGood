package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
import com.readingisgood.dto.OrderDto;
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
    public Order saveNewOrder(@RequestBody @Valid OrderDto orderDto)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        Order newOrder = orderService.saveNewOrder(orderDto);
        LOG.debug(ApplicationConstants.STEPOUT);
        return newOrder;
    }

    @GetMapping(value = "/{orderId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable String orderId)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        Order order = orderService.getOrderById(orderId);
        LOG.debug(ApplicationConstants.STEPOUT);
        return order;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        List<Order> order = orderService.getOrdersByDateRange(startDate, endDate);
        LOG.debug(ApplicationConstants.STEPOUT);
        return order;
    }

}
