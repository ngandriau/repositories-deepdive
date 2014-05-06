package org.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@PropertySource(name = "defaultPropertySource",
        value = "classpath:${APP_ENV:default}.properties")
public class BaseConfig
{

    @Resource
    private Environment env;
}
