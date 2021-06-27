package com.readingisgood.service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingisgood.constant.OrderStatus;
import com.readingisgood.dto.OrderDto;
import com.readingisgood.entity.Order;
import com.readingisgood.exception.BadRequestException;
import com.readingisgood.exception.ExceptionModel;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.OrderRepository;

@Service
public class OrderService
{
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private OrderRepository orderRepository;

    private BookService bookService;

    @Autowired
    public OrderService(OrderRepository orderRepository, BookService bookService)
    {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
    }

    @Transactional
    public Order saveNewOrder(@Valid OrderDto orderDto)
    {
        bookService.setBookForOrder(orderDto.getBookId(), orderDto.getCount());
        double priceOfBook = bookService.getPriceOfBook(orderDto.getBookId());

        Order order = new Order();

        order.setBookId(orderDto.getBookId());
        order.setCount(orderDto.getCount());
        order.setEmail(orderDto.getEmail());

        order.setOrderDate(Instant.now().toEpochMilli());
        order.setStatus(OrderStatus.ORDERED);
        order.setBookPriceAtDate(priceOfBook);
        LOG.info("Order from user will be saved username: {}", order.getEmail());
        return orderRepository.save(order);
    }

    public Order getOrderById(String id)
    {
        Optional<Order> result = orderRepository.findById(id);
        if (result.isPresent())
        {
            return result.get();
        }
        else
        {
            throw new NotFoundException(
                    new ExceptionModel(HttpStatus.NOT_FOUND, "The order with Id: " + id + " is not found"));
        }
    }

    public List<Order> getOrdersByDateRange(String startDate, String endDate)
    {
        Instant start = safeParseToInstant(startDate);
        Instant end = safeParseToInstant(endDate);
        return orderRepository.findByOrderDateBetween(start.toEpochMilli(), end.toEpochMilli());
    }

    public Page<Order> getOrderOfCustomerPageable(String email, Pageable pageable)
    {
        return orderRepository.findAllByEmail(email, pageable);
    }

    private Instant safeParseToInstant(String date)
    {
        try
        {
            return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
        }
        catch (DateTimeParseException e)
        {
            throw new BadRequestException(new ExceptionModel(HttpStatus.BAD_REQUEST,
                    "The date can not be parsed. It must be ISO-8601 Format"));
        }
    }

}
