package org.app.util

import org.app.config.DbConfig
import org.app.dataaccess.BookRepository
import org.app.dataaccess.CustomerRepository
import org.app.dataaccess.OrderRepository
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by ngandriau on 5/4/14.
 */
class DeleteDomainData {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class);

        CustomerRepository customerRepo = (CustomerRepository) ctx.getBean("customerRepository");
        OrderRepository orderRepo = (OrderRepository) ctx.getBean("orderRepository");
        BookRepository bookRepo = (BookRepository) ctx.getBean("bookRepository");

        orderRepo.deleteAll()
        customerRepo.deleteAll()
        bookRepo.deleteAll()
    }
}
