package com.evinfo;

import com.evinfo.global.common.RestProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RestProperties.class)
public class CapstoneEvinfoApplication {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/application-rest.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(CapstoneEvinfoApplication.class, args);
    }

}
