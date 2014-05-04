package org.app.beans;

import org.app.dataaccess.CustomerRepository;
import org.app.dataaccess.OrderRepository;
import org.app.domain.BookOrder;
import org.app.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ngandriau on 5/4/14.
 */

@Service
@Transactional
public class OrderService
{

    final static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    public BookOrder newOrder(String customerLastname)
    {
        log.info("newOrder(customerLastname:{})", customerLastname);

        Customer customer = customerRepository.findByLastname(customerLastname);

        if (customer != null)
        {
            log.debug("  found a customer with id:{}. Use it", customer.getId());
        } else
        {
            log.debug("  no customer found with the name, create new one");
            customer = customerRepository.save(new Customer("???", customerLastname));
        }


        BookOrder newOrder =  orderRepository.save(new BookOrder(customer));

        return newOrder;

    }
}
