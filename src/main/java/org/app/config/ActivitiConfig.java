package org.app.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.annotations.AbstractActivitiConfigurer;
import org.activiti.spring.annotations.EnableActiviti;
import org.app.beans.BookOrderProcessService;
import org.app.beans.PrinterBean;
import org.app.util.GenericProcessEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

/**
 * Created by ngandriau on 5/1/14.
 */
@Configuration
@Import(DbConfig.class)
@EnableActiviti
public class ActivitiConfig
{

    @Bean
    public AbstractActivitiConfigurer abstractActivitiConfigurer(
            final EntityManagerFactory emf,
            final PlatformTransactionManager transactionManager) {

        return new AbstractActivitiConfigurer() {

            @Override
            public void postProcessSpringProcessEngineConfiguration(SpringProcessEngineConfiguration engine) {
                engine.setTransactionManager(transactionManager);
                engine.setJpaEntityManagerFactory(emf);
                engine.setJpaHandleTransaction(false);
                engine.setJobExecutorActivate(true);
                engine.setJpaCloseEntityManager(false);
                engine.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
                engine.setEventListeners(Arrays.<ActivitiEventListener>asList( new GenericProcessEventListener()));
            }
        };
    }

    @Bean
    public PrinterBean printer(){
        return new PrinterBean();
    }

    @Bean
    public BookOrderProcessService bookOrderProcessService(){return new BookOrderProcessService();}
}
