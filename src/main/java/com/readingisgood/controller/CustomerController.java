package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
import com.readingisgood.dto.CustomerDto;
import com.readingisgood.dto.CustomerSaveDto;
import com.readingisgood.entity.Order;
import com.readingisgood.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCustomer(@RequestBody @Valid CustomerSaveDto customerSaveDto)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        customerService.saveCustomer(customerSaveDto);
        LOG.debug(ApplicationConstants.STEPOUT);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomer(@RequestParam String email)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        CustomerDto result = customerService.getCustomer(email);
        LOG.debug(ApplicationConstants.STEPOUT);
        return result;
    }

    @GetMapping(value = "/{email}/order", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<Order>> getOrdersOfCustomer(@PathVariable String email, final Pageable pageable,
            final PagedResourcesAssembler<Order> pagedAssembler)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        PagedModel<EntityModel<Order>> result = pagedAssembler
                .toModel(customerService.getOrdersOfCustomer(email, pageable));
        LOG.debug(ApplicationConstants.STEPOUT);
        return result;
    }
}
