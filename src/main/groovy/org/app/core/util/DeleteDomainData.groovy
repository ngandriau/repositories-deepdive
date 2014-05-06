package org.app.core.util

import org.app.core.config.DbConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by ngandriau on 5/4/14.
 */
class DeleteDomainData {

    DomainRepositories repositories

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class);

        DeleteDomainData app = new DeleteDomainData(ctx)

        app.deleteAll()
    }

    DeleteDomainData(ApplicationContext ctx) {
        repositories =  DomainRepositories.getRepositories(ctx)
    }

    def deleteAll() {
        repositories.with {
            orderRepo.deleteAll()
            customerRepo.deleteAll()
            bookRepo.deleteAll()
        }
    }
}
