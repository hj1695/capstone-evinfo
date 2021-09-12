package com.evinfo.global.config;

import com.evinfo.global.common.RestProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@EnableConfigurationProperties(RestProperties.class)
@Configuration
public class PropertiesConfig {
    // TODO: 2021/09/13 yml 파일 가져오는거 여기서 말고 다른데서 처리하기, 그리고 enum docs test에 ChargerType이랑 그 Stat넣어주기
}
