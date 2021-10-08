package com.evinfo.batch.charger;

import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.batch.charger.dto.ChargerUpdateRequestDto;
import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerCompositeId;
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
import java.util.stream.Collectors;

@Configuration
@Slf4j(topic = "BATCH_FILE_LOGGER")
@RequiredArgsConstructor
public class ChargerUpdateConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ChargerClient chargerClient;
    private final ChargerRepository chargerRepository;
    private final EntityManagerFactory entityManagerFactory;

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
                .<ChargerUpdateRequestDto, Charger>chunk(1000)
                .reader(chargerUpdateReader())
                .processor(chargerUpdateProcessor())
                .writer(chargerJpaWriter())
                .build();
    }

    private ItemReader<ChargerUpdateRequestDto> chargerUpdateReader() {
        var updateChargerRequests = chargerClient.findModifiedChargers()
                .stream()
                .map(ChargerUpdateRequestDto::valueOf)
                .collect(Collectors.toList());
        return new LinkedListItemReader<>(updateChargerRequests);
    }

    private ItemProcessor<ChargerUpdateRequestDto, Charger> chargerUpdateProcessor() {
        return item -> {
            var charger = chargerRepository.findById(new ChargerCompositeId(item.getStationId(), item.getChargerId()))
                    .orElse(null);
            if (Objects.isNull(charger) || charger.getChargerStat().equals(item.getChargerStat())) {
                return null;
            }
            charger.updateChargerStat(item.getChargerStat());

            return charger;
        };
    }

    private JpaItemWriter<Charger> chargerJpaWriter() {
        JpaItemWriter<Charger> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;
    }
}
