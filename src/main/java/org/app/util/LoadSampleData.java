package org.app.util;

import org.app.config.DBConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * Created by ngandriau on 5/1/14.
 */
public class LoadSampleData
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DBConfig.class);

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
