package com.evinfo.batch.chargers;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.domain.charger.Charger;
import com.evinfo.global.common.RestProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@Slf4j(topic = "BATCH_FILE_LOGGER")
@RequiredArgsConstructor
public class ChargerInitConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ChargerClient chargerClient;
    private final RestProperties restProperties;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job chargerInitJob() {
        return jobBuilderFactory.get("chargerInitJob")
                .incrementer(new RunIdIncrementer())
                .start(this.chargerInitStep())
                .listener(new ChargerJobExecutionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step chargerInitStep() {
        return stepBuilderFactory.get("chargerInitStep")
                .<ChargerClientResponseDto, Charger>chunk(restProperties.getEvinfoChunk().intValue())
                .reader(chargerInitReader())
                .processor(chargerInitProcessor())
                .writer(chargerJpaWriter())
                .build();
    }

    private ItemReader<ChargerClientResponseDto> chargerInitReader() {
        return new LinkedListItemReader<>(chargerClient.fetchChargers());
    }

    private ItemProcessor<ChargerClientResponseDto, Charger> chargerInitProcessor() {
        return ChargerClientResponseDto::getCharger;
    }

    private JpaItemWriter<Charger> chargerJpaWriter() {
        JpaItemWriter<Charger> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;
    }
}
