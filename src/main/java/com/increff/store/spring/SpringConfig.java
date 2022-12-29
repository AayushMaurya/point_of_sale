package com.increff.store.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.increff.store")
@PropertySources({@PropertySource(value = "file:./store.properties", ignoreResourceNotFound = true)})
public class SpringConfig {
}
