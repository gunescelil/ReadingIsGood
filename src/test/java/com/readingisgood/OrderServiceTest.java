package com.readingisgood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.readingisgood.dto.OrderDto;
import com.readingisgood.entity.Order;
import com.readingisgood.exception.BadRequestException;
import com.readingisgood.exception.ExceptionModel;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.OrderRepository;
import com.readingisgood.service.BookService;
import com.readingisgood.service.OrderService;

class OrderServiceTest
{
    @Mock
    private BookService bookService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavingNewOrderWorks()
    {
        String bookId = "123";
        Integer count = 5;
        OrderDto orderDto = new OrderDto();
        orderDto.setBookId(bookId);
        orderDto.setEmail("gunescelil@gmail.com");
        orderDto.setCount(count);
        double priceOfBook = 10;
        Mockito.when(bookService.getPriceOfBook(bookId)).thenReturn(priceOfBook);

        orderService.saveNewOrder(orderDto);

        ArgumentCaptor<Order> captorOrder = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, Mockito.times(1)).save(captorOrder.capture());

        Order savedOrderInRepo = captorOrder.getValue();

        assertEquals(savedOrderInRepo.getBookPriceAtDate(), priceOfBook);
        assertEquals(savedOrderInRepo.getBookId(), orderDto.getBookId());
        assertEquals(savedOrderInRepo.getEmail(), orderDto.getEmail());
        assertEquals(savedOrderInRepo.getCount(), orderDto.getCount());

        ArgumentCaptor<String> captorBookId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> captorCount = ArgumentCaptor.forClass(Integer.class);
        verify(bookService, Mockito.times(1)).setBookForOrder(captorBookId.capture(), captorCount.capture());

        assertEquals(captorBookId.getValue(), bookId);
        assertEquals(captorCount.getValue(), count);

        verify(bookService, Mockito.times(1)).getPriceOfBook(captorBookId.capture());
        assertEquals(captorBookId.getValue(), bookId);
    }

    @Test()
    void testOrderNotSavedIfBookIsNotFound()
    {
        String bookId = "123";
        Integer count = 5;
        OrderDto orderDto = new OrderDto();
        orderDto.setBookId(bookId);
        orderDto.setEmail("gunescelil@gmail.com");
        orderDto.setCount(count);
        String expectedMessage = "For input string";
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;
        Mockito.doThrow(new NotFoundException(new ExceptionModel(expectedHttpStatus, expectedMessage)))
                .when(bookService).setBookForOrder(bookId, count);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
        {
            orderService.saveNewOrder(orderDto);
        });

        String actualMessage = exception.getMessage();
        assertEquals(exception.getExceptionModel().getErrorCode(), expectedHttpStatus);
        assertTrue(actualMessage.contains(expectedMessage));

        ArgumentCaptor<Order> captorOrder = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, Mockito.never()).save(captorOrder.capture());

        ArgumentCaptor<String> captorBookId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> captorCount = ArgumentCaptor.forClass(Integer.class);
        verify(bookService, Mockito.times(1)).setBookForOrder(captorBookId.capture(), captorCount.capture());

        assertEquals(captorBookId.getValue(), bookId);
        assertEquals(captorCount.getValue(), count);

        verify(bookService, Mockito.never()).getPriceOfBook(captorBookId.capture());
    }

    @Test
    void testFindOrderWorks()
    {
        String orderId = "orderId";
        Order order = new Order();
        order.setId(orderId);
        order.setBookId("bookId");
        order.setCount(5);
        Optional<Order> orderOptional = Optional.of(order);
        when(orderRepository.findById(orderId)).thenReturn(orderOptional);

        Order actual = orderService.getOrderById(orderId);

        verify(orderRepository, Mockito.times(1)).findById(orderId);

        assertEquals(order.getBookId(), actual.getBookId());
        assertEquals(order.getId(), actual.getId());
        assertEquals(order.getCount(), actual.getCount());
    }

    @Test
    void testThrowsNotFoundIfOrderIsNotPresent()
    {
        String orderId = "orderId";
        String expectedMessage = "The order with Id: " + orderId + " is not found";
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;

        when(orderRepository.findById(orderId)).thenReturn(Optional.ofNullable(null));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
        {
            orderService.getOrderById(orderId);
        });

        String actualMessage = exception.getMessage();
        assertEquals(exception.getExceptionModel().getErrorCode(), expectedHttpStatus);
        assertTrue(actualMessage.contains(expectedMessage));

        verify(orderRepository, Mockito.times(1)).findById(orderId);
    }
    
    @Test
    void testGetOrdersBetweenDatesWorks()
    {
        String startDate = "2021-06-20T21:20:04.000Z";
        String endDate = "2021-06-28T09:22:00.000Z"; 
        long startDateLong = 1624224004000l;
        long endDateLong = 1624872120000l;
        
        orderService.getOrdersByDateRange(startDate, endDate);

        verify(orderRepository, Mockito.times(1)).findByOrderDateBetween(startDateLong, endDateLong);
    }
    
    @Test
    void testGetOrdersBetweenDatesThrowsBadRequestExceptionIfDatesAreNotParsable()
    {
        String startDate = "2021-06-20T";
        String endDate = "2021-06-28T09:22:00.000Z"; 
        
        String expectedMessage = "The date can not be parsed. It must be ISO-8601 Format";
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
        {
            orderService.getOrdersByDateRange(startDate, endDate);
        });
        
        String actualMessage = exception.getMessage();
        assertEquals(exception.getExceptionModel().getErrorCode(), expectedHttpStatus);
        assertTrue(actualMessage.contains(expectedMessage));
                

        verify(orderRepository, Mockito.never()).findByOrderDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }
    
    @Test
    void testGetOrderOfCustomerPageableWorks()
    {
        String email = "gunescelil@gmail.com";
        Pageable pageable = PageRequest.of(0, 5);
        
        orderService.getOrderOfCustomerPageable(email, pageable);
        
        ArgumentCaptor<String> captorEmail = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> captorPageable = ArgumentCaptor.forClass(Pageable.class);
        
        verify(orderRepository, Mockito.times(1)).findAllByEmail(captorEmail.capture(), captorPageable.capture());
        
        assertEquals(captorEmail.getValue(), email);
        assertEquals(captorPageable.getValue(), pageable);
        
    }
}
