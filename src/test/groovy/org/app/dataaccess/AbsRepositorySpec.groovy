package org.app.dataaccess

import groovy.util.logging.Slf4j
import org.app.config.DbConfig
import org.app.util.LoadSampleData
import org.app.util.DeleteDomainData
import org.app.util.DomainRepositories
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by ngandriau on 5/4/14.
 */
@Slf4j
abstract class AbsRepositorySpec extends Specification {

    @Shared
    ApplicationContext ctx

    @Shared
    DomainRepositories repositories

    @Shared
    DeleteDomainData delete

    @Shared
    LoadSampleData loadSample


    def setupSpec() {
        ctx = new AnnotationConfigApplicationContext(DbConfig.class);
        repositories = DomainRepositories.getRepositories(ctx)

        delete = new DeleteDomainData(ctx)
        delete.deleteAll()

        loadSample = new LoadSampleData(ctx)
    }

    def setup() {
        loadSample.loadSampleData()
    }

    def cleanup() {
        delete.deleteAll()

    }

    def cleanupSpec() {
        log.info "cleanupSpec()"
    }

}
