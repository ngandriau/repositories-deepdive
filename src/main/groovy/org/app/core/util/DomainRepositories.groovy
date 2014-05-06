package org.app.core.util

import org.app.core.dataaccess.BookRepository
import org.app.core.dataaccess.CustomerRepository
import org.app.core.dataaccess.OrderRepository
import org.springframework.context.ApplicationContext

/**
 * Created by ngandriau on 5/4/14.
 */
class DomainRepositories {

    CustomerRepository customerRepo
    OrderRepository orderRepo
    BookRepository bookRepo

    static DomainRepositories getRepositories(ApplicationContext ctx){
        CustomerRepository aCustomerRepo = (CustomerRepository) ctx.getBean("customerRepository")
        OrderRepository anOrderRepo = (OrderRepository) ctx.getBean("orderRepository")
        BookRepository aBbookRepo = (BookRepository) ctx.getBean("bookRepository")

        def app = new DomainRepositories(customerRepo: aCustomerRepo, orderRepo: anOrderRepo, bookRepo: aBbookRepo)

        return app
    }

}
