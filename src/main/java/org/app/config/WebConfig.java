package org.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Initial setup inspired by: https://spring.io/blog/2013/05/11/content-negotiation-using-spring-mvc
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@Import(ActivitiConfig.class)
@ComponentScan(basePackages = {"org.app.rest.controller"})
public class WebConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
                favorParameter(true).
                parameterName("mediaType").
                ignoreAcceptHeader(true).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("json", MediaType.APPLICATION_JSON);
    }
}
