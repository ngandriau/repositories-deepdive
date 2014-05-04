package org.app.util

import org.app.config.DbConfig
import org.app.domain.Book
import org.app.domain.Customer
import org.app.domain.EmailAddress
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by ngandriau on 5/4/14. <br/>
 *
 * Simple main which load some data in db for testing purpose
 */
class LoadSampleData {
    DomainRepositories repositories

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class)
        def app = new LoadSampleData(ctx)

        app.loadSampleData()
    }

    LoadSampleData(ApplicationContext ctx) {
        repositories = DomainRepositories.getRepositories(ctx)
    }

    void loadSampleData() {

        Book springInAction = new Book(isbn: "1932394354", title: "Spring in Action", author: "Craig Walls")
        Book activitiInAction = new Book(isbn: "9781617290121", title: "Activiti in Action", author: "Tijs Rademakers")
        Book javaPersistenceWithHibernate = new Book(isbn: "1-932394-88-5 ", title: "Java Persistence with Hibernate", author: "Christian Bauer")
        repositories.bookRepo.save([springInAction, activitiInAction, javaPersistenceWithHibernate])


        Customer nicolas = new Customer(firstname: "Nicolas", lastname: "GANDRIAU", emailAddress: new EmailAddress("nicolas@email.com"))
        Customer john = new Customer(firstname: "John", lastname: "Doe")
        repositories.customerRepo.save([nicolas, john])


    }
}
