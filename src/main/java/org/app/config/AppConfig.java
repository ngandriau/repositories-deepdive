package org.app.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.annotations.AbstractActivitiConfigurer;
import org.activiti.spring.annotations.EnableActiviti;
import org.app.beans.PrinterBean;
import org.app.util.MyEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ngandriau on 5/1/14.
 */
@Configuration
@PropertySource(name = "defaultPropertySource",
        value = "classpath:${APP_ENV:default}.properties")
@EnableTransactionManagement
@EnableJpaRepositories("org.app.dataaccess")
@EnableActiviti
public class AppConfig
{

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_GENERATE_DDL = "hibernate.generate_ddl";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.valueOf(env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT)));
        vendorAdapter.setGenerateDdl(Boolean.valueOf(env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_GENERATE_DDL)));
        vendorAdapter.setGenerateDdl(Boolean.valueOf(env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL)));

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        factory.setDataSource(dataSource());

        return factory;
    }

    @Bean
    public JpaTransactionManager transactionManager()
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

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
                engine.setEventListeners(Arrays.<ActivitiEventListener>asList( new MyEventListener()));
            }
        };
    }

    @Bean
    public PrinterBean printer(){
        return new PrinterBean();
    }
}
