package com.evinfo.batch.chargers;

import com.evinfo.api.charger.dto.ChargerModifiedResponseDto;
import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerCompositeId;
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
import java.util.Objects;

@Configuration
@Slf4j(topic = "BATCH_FILE_LOGGER")
@RequiredArgsConstructor
public class ChargerConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ChargerClient chargerClient;
    private final RestProperties restProperties;
    private final ChargerRepository chargerRepository;
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

    @Bean
    public Job chargerUpdateJob() {
        return jobBuilderFactory.get("chargerUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(this.chargerUpdateStep())
                .listener(new ChargerJobExecutionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step chargerUpdateStep() {
        return stepBuilderFactory.get("chargerUpdateStep")
                .<ChargerClientResponseDto, Charger>chunk(1000)
                .reader(chargerUpdateReader())
                .processor(chargerUpdateProcessor())
                .writer(chargerJpaWriter())
                .build();
    }

    private ItemReader<ChargerClientResponseDto> chargerUpdateReader() {
        return new LinkedListItemReader<>(chargerClient.findModifiedChargers());
    }

    private ItemProcessor<ChargerClientResponseDto, Charger> chargerUpdateProcessor() {
        return item -> {
            var response = ChargerModifiedResponseDto.valueOf(item);
            var charger = chargerRepository.findById(new ChargerCompositeId(response.getStationId(), response.getChargerId()))
                    .orElse(null);
            if (Objects.isNull(charger) || charger.getChargerStat().equals(response.getChargerStat())) {
                return null;
            }
            log.info("update: {} stat {} to {}", charger.getStationName(), charger.getChargerStat().getName(), response.getChargerStat().getName());
            charger.updateChargerStat(response.getChargerStat());

            return charger;
        };
    }
}
