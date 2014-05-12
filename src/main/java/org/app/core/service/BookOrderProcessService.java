package org.app.core.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.app.core.dataaccess.BookRepository;
import org.app.core.dataaccess.CustomerRepository;
import org.app.core.dataaccess.OrderRepository;
import org.app.core.domain.Book;
import org.app.core.domain.BookOrder;
import org.app.core.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import static org.app.api.OrderStatus.*;


@Service
@Transactional
public class BookOrderProcessService
{

    public static final String CUSTOMER_LASTNAME_PARAM_KEY = "customerLastname";
    public static final String ISBNS_PARAM_KEY = "isbns";
    public static final String ORDERID_PARAM_KEY = "orderId";
    public static final String ACTION_PARAM_KEY = "action";

    final static Logger log = LoggerFactory.getLogger(BookOrderProcessService.class);

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

    public void newOrder(DelegateExecution execution)
    {
        log.info("newOrder()");

        final Map<String, Object> processParams = execution.getVariables();
        for (Map.Entry<String, Object> param : processParams.entrySet())
        {
            log.debug("  process param[{}]={}", param.getKey(), param.getValue());
        }

        final String customerLastname = (String) processParams.get(CUSTOMER_LASTNAME_PARAM_KEY);
        Customer customer = customerRepo.findByLastname(customerLastname);

        if (customer != null)
        {
            log.debug("  found a customer with id:{} and matching lastname:{}. Use it", customer.getId(), customer.getLastname());
        } else
        {
            log.debug("  no customer found with matching lastname:{}. create new one.", customerLastname);
            customer = customerRepo.save(new Customer("???", customerLastname));
        }

        final BookOrder order = new BookOrder(customer);

        Collection<String> isbns = getIsbns(execution);
        for (String isbn : isbns)
        {
            log.debug("  add book({}) in order", isbn);
            Book aBook = bookRepo.findByIsbn(isbn);
            if (aBook == null)
            {
                log.warn("  requested book with isbn({}) is not is stock, donnot add it to the order");
            } else
            {
                order.getOrderedBooks().add(aBook);
            }
        }

        order.setStatus(created);
        BookOrder savedOrder = orderRepo.save(order);

        execution.setVariable(ORDERID_PARAM_KEY, savedOrder.getId());
    }

    public void checkForApproval(DelegateExecution execution)
    {
        final int maxAmountFoAutoApproval = 1; //TMP for testing purpose

        log.info("checkForApproval() - max amount for auto approval = {} ", maxAmountFoAutoApproval);

        BookOrder order = orderRepo.findOne(getOrderId(execution));

        log.debug("  found the order with id({})", getOrderId(execution));

        BigDecimal orderAmout = BigDecimal.ZERO;
        for (Book book : order.getOrderedBooks())
        {
            orderAmout = orderAmout.add(book.getPrice().getValue());
        }

        log.debug("  order amount = {}", orderAmout);

        if (orderAmout.doubleValue() >= maxAmountFoAutoApproval)
        {
            log.debug("    order need approval!");
            execution.setVariable(ACTION_PARAM_KEY, "NeedApproval");
        } else
        {
            log.debug("    order is auto approved.");
            execution.setVariable(ACTION_PARAM_KEY, "NoApprovalNeeded");
        }

        order.setStatus(needApprovalChecked);
        orderRepo.save(order);
    }

    Long getOrderId(DelegateExecution execution)
    {
        return (Long) execution.getVariable(ORDERID_PARAM_KEY);
    }

    String getCustomerLastname(DelegateExecution execution)
    {
        return (String) execution.getVariable(CUSTOMER_LASTNAME_PARAM_KEY);
    }

    Collection<String> getIsbns(DelegateExecution execution)
    {
        return (Collection<String>) execution.getVariable(ISBNS_PARAM_KEY);
    }

    public void recordManagerValidationDecision(Long orderId, boolean approved, String reason)
    {

        log.info("recordManagerValidationDecision() - approved:{} ", approved);

        BookOrder order = orderRepo.findOne(orderId);

        //TODO: record the approval decision and reason

        order.setStatus(validated);
        orderRepo.save(order);
    }

    public void rejectOrder(DelegateExecution execution)
    {

        log.info("rejectOrder()");

        BookOrder order = orderRepo.findOne(getOrderId(execution));


        order.setStatus(rejected);
        orderRepo.save(order);
    }

    public void submitOrder(DelegateExecution execution)
    {

        log.info("submitOrder()");

        BookOrder order = orderRepo.findOne(getOrderId(execution));


        order.setStatus(submited);
        orderRepo.save(order);
    }
}
