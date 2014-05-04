package org.app.test.util

import org.app.config.DbConfig
import org.app.dataaccess.BookRepository
import org.app.dataaccess.CustomerRepository
import org.app.dataaccess.OrderRepository
import org.app.domain.Book
import org.app.domain.Customer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by ngandriau on 5/4/14. <br/>
 *
 * Simple main which load some data in db for testing purpose
 */
class LoadSampleData {
    CustomerRepository customerRepo
    OrderRepository orderRepo
    BookRepository bookRepo

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class)

        CustomerRepository aCustomerRepo = (CustomerRepository) ctx.getBean("customerRepository")
        OrderRepository anOrderRepo = (OrderRepository) ctx.getBean("orderRepository")
        BookRepository aBbookRepo = (BookRepository) ctx.getBean("bookRepository")

        def app = new LoadSampleData(customerRepo: aCustomerRepo, orderRepo: anOrderRepo, bookRepo: aBbookRepo)

        app.loadSampleData()
    }

    void loadSampleData() {

        Book springInAction = new Book(isbn: "1932394354", title: "Spring in Action", author: "Craig Walls")
        Book activitiInAction = new Book(isbn: "9781617290121", title: "Activiti in Action", author: "Tijs Rademakers")
        Book javaPersistenceWithHibernate = new Book(isbn: "1-932394-88-5 ", title: "Java Persistence with Hibernate", author: "Christian Bauer")
        bookRepo.save([springInAction, activitiInAction, javaPersistenceWithHibernate])


        Customer nicolas = new Customer(firstname: "Nicolas", lastname: "GANDRIAU")
        Customer john = new Customer(firstname: "John", lastname: "Doe")
        customerRepo.save([nicolas, john])


    }
}
