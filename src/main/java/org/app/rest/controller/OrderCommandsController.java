package org.app.rest.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.app.core.dataaccess.BookRepository;
import org.app.core.dataaccess.CustomerRepository;
import org.app.core.dataaccess.OrderRepository;
import org.app.core.domain.BookOrder;
import org.app.core.domain.Customer;
import org.app.rest.domain.OrderR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/mycomp/orders")
public class OrderCommandsController
{
    private static Logger LOG = LoggerFactory.getLogger(OrderCommandsController.class);

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    BookRepository bookRepo;


    @RequestMapping(method = RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            produces =MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<OrderR> createOrder(@RequestBody OrderR orderR, UriComponentsBuilder builder)
    {

        Customer newCustomerD = new Customer("John", "Doe");
        BookOrder newBookOrderD = new BookOrder(newCustomerD);
        newCustomerD = customerRepo.save(newCustomerD);
        newBookOrderD = orderRepo.save(newBookOrderD);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/mycomp/orders/{id}")
                        .buildAndExpand(newBookOrderD.getId().toString()).toUri()
        );

        orderR.setId(newBookOrderD.getId());

        return new ResponseEntity<OrderR>(orderR, headers, HttpStatus.CREATED);
    }
}
