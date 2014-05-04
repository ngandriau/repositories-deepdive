package org.app.beans;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.app.dataaccess.CustomerRepository;
import org.app.dataaccess.OrderRepository;
import org.app.domain.BookOrder;
import org.app.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by ngandriau on 5/4/14.
 */

@Service
@Transactional
public class BookOrderProcessService
{

    public static final String CUSTOMER_LASTNAME_PARAM_KEY  = "customerLastname";
    public static final String ISBN_PARAM_KEY = "isbn";

    final static Logger log = LoggerFactory.getLogger(BookOrderProcessService.class);

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    public BookOrder newOrder(DelegateExecution execution, String customerLastname)
    {
        log.info("newOrder(customerLastname:{})", customerLastname);

        final Map<String, Object> processParams = execution.getVariables();
        for (Map.Entry<String, Object> param : processParams.entrySet())
        {
            log.debug("  process param[{}]={}", param.getKey(), param.getValue());
        }

        Customer customer = customerRepository.findByLastname((String) processParams.get(CUSTOMER_LASTNAME_PARAM_KEY));

        if (customer != null)
        {
            log.debug("  found a customer with id:{} and matching lastname:{}. Use it", customer.getId(), customer.getLastname());
        } else
        {
            log.debug("  no customer found with matvhing lastname:{}. create new one.", customerLastname);
            customer = customerRepository.save(new Customer("???", customerLastname));
        }


        BookOrder newOrder =  orderRepository.save(new BookOrder(customer));

        return newOrder;

    }
}
