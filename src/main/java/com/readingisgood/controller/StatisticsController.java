package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
import com.readingisgood.service.StatisticsService;

@RestController
@RequestMapping(value = "/api/statistics")
public class StatisticsController
{
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);
    
    private StatisticsService statisticsService;
    
    @Autowired
    public StatisticsController(StatisticsService statisticsService)
    {
        this.statisticsService = statisticsService;
    }
    
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getStatistics()
    {
        LOG.debug(ApplicationConstants.STEPIN);
        statisticsService.getStatistics();
        LOG.debug(ApplicationConstants.STEPOUT);
    }
}
