package com.readingisgood.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingisgood.dto.MonthlyStatisticsDto;
import com.readingisgood.dto.StatisticsDto;
import com.readingisgood.entity.Order;
import com.readingisgood.repository.OrderRepository;

@Service
public class StatisticsService
{

    private OrderRepository orderRepository;

    @Autowired
    public StatisticsService(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    public StatisticsDto getStatistics()
    {
        List<Order> orders = this.orderRepository.findAll();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM").withZone(ZoneId.of("UTC"));

        Map<String, List<Order>> monthlyMappedOrders = orders.stream()
                .collect(Collectors.groupingBy(a -> dateTimeFormatter.format(Instant.ofEpochMilli(a.getOrderDate()))));

        StatisticsDto statisticsDto = new StatisticsDto();
        List<MonthlyStatisticsDto> monthlyStatistics = new ArrayList<>();

        monthlyMappedOrders.entrySet().stream().forEach(entry -> monthlyStatistics.add(getStatisticsOfMonth(entry)));
        statisticsDto.setStatistics(monthlyStatistics);
        return statisticsDto;
    }

    private MonthlyStatisticsDto getStatisticsOfMonth(Entry<String, List<Order>> entry)
    {
        MonthlyStatisticsDto monthlyStatisticsDto = new MonthlyStatisticsDto();
        monthlyStatisticsDto.setMonth(entry.getKey());
        List<Order> ordersInMonth = entry.getValue();

        double totalPrice = 0;
        int totalBookCount = 0;
        int totalOrderCount = ordersInMonth.size();
        for (int i = 0; i < ordersInMonth.size(); i++)
        {
            Order order = ordersInMonth.get(i);
            int count = order.getCount();
            totalBookCount += count;
            totalPrice += count * order.getBookPriceAtDate();
        }

        monthlyStatisticsDto.setTotalOrderCount(totalOrderCount);
        monthlyStatisticsDto.setTotalBookCount(totalBookCount);
        monthlyStatisticsDto.setTotalPurchasedAmount(totalPrice);
        return monthlyStatisticsDto;
    }

}
