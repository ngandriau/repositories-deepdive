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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mycomp/orders")
public class OrderQueriesController
{
    private static Logger LOG = LoggerFactory.getLogger(OrderQueriesController.class);


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

    @RequestMapping(value="/{orderId}", method = RequestMethod.GET)
    @ResponseBody()
    public OrderR getOrderById(@PathVariable Long orderId) {



        OrderR orderR = new OrderR();
        orderR.setId(orderId);

        return orderR;
    }
}
