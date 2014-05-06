package org.app.core.util

import groovy.util.logging.Slf4j
import org.app.config.DbConfig
import org.app.core.domain.AmountVT
import org.app.core.domain.Book
import org.app.core.domain.BookOrder
import org.app.core.domain.Customer
import org.app.core.domain.EmailAddress
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by ngandriau on 5/4/14. <br/>
 *
 * Simple main which load some data in db for testing purpose
 */

@Slf4j
class LoadSampleData {
    static final String CURRENCY = '$CAD';
    public static final String SPRING_IN_ACTION_ISBN = "1932394354"
    public static final String ACTIVITI_IN_ACTION_ISBN = "9781617290121"
    public static final String JAVA_PERSISTENCE_ISBN = "1-932394-88-5 "

    DomainRepositories repositories

    public static void main(String[] args) {
        log.info "When launch via \"main\" also delete existing data!"

        ApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class)

        log.info "== delete existing data"
        def deleteApp = new DeleteDomainData(ctx)
        deleteApp.deleteAll()

        log.info "== load sample data"
        def loadApp = new LoadSampleData(ctx)
        loadApp.loadSampleData()
    }

    LoadSampleData(ApplicationContext ctx) {
        repositories = DomainRepositories.getRepositories(ctx)
    }

    void loadSampleData() {

        Book springInAction = new Book(isbn: SPRING_IN_ACTION_ISBN, title: "Spring in Action",
                author: "Craig Walls", price: new AmountVT(CURRENCY, 25.50))
        Book activitiInAction = new Book(isbn: ACTIVITI_IN_ACTION_ISBN, title: "Activiti in Action",
                author: "Tijs Rademakers", price: new AmountVT(CURRENCY, 19.25))
        Book javaPersistenceWithHibernate = new Book(isbn: JAVA_PERSISTENCE_ISBN, title: "Java Persistence with Hibernate",
                author: "Christian Bauer" , price: new AmountVT(CURRENCY, 57.95))
        repositories.bookRepo.save([springInAction, activitiInAction, javaPersistenceWithHibernate])


        Customer nicolas = new Customer(firstname: "Nicolas", lastname: "GANDRIAU",
                emailAddress: new EmailAddress("nicolas@email.com"))
        Customer john = new Customer(firstname: "John", lastname: "Doe")
        repositories.customerRepo.save([nicolas, john])

        BookOrder order1 = new BookOrder(customer: john, orderedBooks: [springInAction, javaPersistenceWithHibernate])
        repositories.orderRepo.save(order1)
    }
}
