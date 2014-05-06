package org.app.core.dataaccess

import groovy.util.logging.Slf4j
import org.app.core.domain.Customer
import org.app.core.domain.EmailAddress
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * Created by ngandriau on 5/4/14.
 */
@Slf4j
class CustomerRepositorySpec extends AbsRepositorySpec {


    def "find customer by id "() {
        when:
            def nicolasId = loadNicolasByEmail().id
            def nicolas = repositories.customerRepo.findOne(nicolasId)

        then:
            nicolas
            nicolas.firstname == "Nicolas"
            nicolas.lastname == "GANDRIAU"
    }

    def "save new customer"() {
        when:
            def newCustomer = new Customer(firstname: "firstname", lastname: "lastname")
            def savedCustmer = repositories.customerRepo.save(newCustomer)

        then:
            savedCustmer.id
            savedCustmer.firstname == newCustomer.firstname
            savedCustmer.lastname == newCustomer.lastname
    }

    def "update customer save in db"() {
        when:
            Customer nicolas = loadNicolasByEmail()
            nicolas.emailAddress = new EmailAddress(value: "new@email.com")
            repositories.customerRepo.save(nicolas)

            def savedCustomer = repositories.customerRepo.findByLastname("GANDRIAU")

        then:
            savedCustomer
            savedCustomer.id == nicolas.id
            savedCustomer.emailAddress == new EmailAddress(value: "new@email.com")
    }

    def "find customer by email address"() {
        when:
            Customer nicolas = loadNicolasByEmail()

        then:
            nicolas
            nicolas.firstname == "Nicolas"
            nicolas.lastname == "GANDRIAU"
    }

    def "find all customer"() {
        when:
            List<Customer> customers = new ArrayList(repositories.customerRepo.findAll())

        then:
            customers.size() == 2
    }

    def "delete a customer"() {
        when:
            Customer nicolas = loadNicolasByEmail()

            repositories.customerRepo.delete(nicolas)

        then:
            loadNicolasByEmail() == null
    }

    def "access customer page by page"() {
        given:
            20.times {
                repositories.customerRepo.save(new Customer(firstname: "fname$it", lastname: "lname$it"))
            }

        when:
            Page<Customer> result = repositories.customerRepo.findAll(new PageRequest(1, 5))

        then:
            result
            result.isFirstPage() == false
            result.isLastPage() == false
            result.numberOfElements == 5
    }

    private loadNicolasByEmail() {
        repositories.customerRepo.findByEmailAddress(new EmailAddress("nicolas@email.com"))
    }

}
