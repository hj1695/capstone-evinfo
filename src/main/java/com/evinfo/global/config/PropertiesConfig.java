package com.evinfo.global.config;

import com.evinfo.global.common.RestProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@EnableConfigurationProperties(RestProperties.class)
@Configuration
public class PropertiesConfig {
}
