package org.app

import org.app.config.ActivitiConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.env.Environment
import spock.lang.Specification

/**
 * Sample spec case bootstrapping the application.
 */
class ApplicationBootstrappingSpec extends Specification {


    def "bootstrap application"(){
        when:
            AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext(ActivitiConfig.class)
            Environment env = appCtx.getEnvironment();

        then:
            env.getProperty("project.name") == "nico-repositories-deeplive"
    }
}