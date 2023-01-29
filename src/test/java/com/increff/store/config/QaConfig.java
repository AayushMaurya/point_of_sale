package com.increff.store.config;

import com.increff.store.spring.SpringConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(//
		basePackages = { "com.increff.store" }, //
		excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SpringConfig.class })//
)
@PropertySources({ //
		@PropertySource(value = "classpath:./test.properties", ignoreResourceNotFound = true) //
})
public class QaConfig {


}
