package org.app.rest.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.app.api.OrderStatus;
import org.app.core.dataaccess.BookRepository;
import org.app.core.dataaccess.CustomerRepository;
import org.app.core.dataaccess.OrderRepository;
import org.app.core.domain.Book;
import org.app.core.domain.BookOrder;
import org.app.rest.domain.BookR;
import org.app.rest.domain.OrderR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    @ResponseBody()
    public OrderR getOrderById(@PathVariable Long orderId)
    {


        OrderR orderR = new OrderR();
        orderR.setId(orderId);

        return orderR;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody()
    public List<OrderR> getEveryOrders()
    {

        ArrayList<OrderR> result = new ArrayList<>();


        Iterable<BookOrder> iter = orderRepo.findAll();
        for (BookOrder orderD : iter)
        {
            final OrderR orderR = new OrderR(orderD.getCreationDate(), orderD.getId());
            orderR.setStatus(orderD.getStatus().toString());
            if (orderD.getCustomer() != null)
            {
                orderR.setCustomerFirstname(orderD.getCustomer().getFirstname());
                orderR.setCustomerLastname(orderD.getCustomer().getLastname());
            }

            List<BookR> bookRs = new ArrayList<>();
            for (Book bookD : orderD.getOrderedBooks())
            {
                BookR aBookR = new BookR(bookD.getId(), bookD.getIsbn(), bookD.getTitle());
                if (bookD.getPrice() != null)
                    aBookR.setPrice(bookD.getPrice().getValue());
                bookRs.add(aBookR);
            }
            orderR.setBooks(bookRs);

            orderR.setAmount(orderD.getTotalAmount());

            // not perfect, but we assume that if the order is in this status, it is because the workflow is waiting
            // just after having checked if approval is needed
            orderR.setNeedConfirmation(orderD.getStatus() == OrderStatus.needApprovalChecked);

            result.add(orderR);
        }

        return result;
    }
}
