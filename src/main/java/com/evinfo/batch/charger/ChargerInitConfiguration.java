package com.evinfo.batch.charger;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.batch.charger.dto.ChargerCreateRequestDto;
import com.evinfo.domain.charger.Station;
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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j(topic = "BATCH_FILE_LOGGER")
@RequiredArgsConstructor
public class ChargerInitConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ChargerClient chargerClient;
    private final RestProperties restProperties;
    private final DataSource dataSource;

    private Map<String, Station> stationExist;
    private List<ChargerClientResponseDto> chargerResponses;

    @Bean
    public Job chargerInitJob() {
        stationExist = new HashMap<>();
        chargerResponses = new ArrayList<>();

        return jobBuilderFactory.get("chargerInitJob")
                .incrementer(new RunIdIncrementer())
                .start(this.stationInitStep())
                .next(this.chargerInitStep())
                .listener(new ChargerJobExecutionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step stationInitStep() {
        return stepBuilderFactory.get("stationInitStep")
                .<ChargerClientResponseDto, Station>chunk(restProperties.getEvinfoChunk().intValue())
                .reader(EVInfoReader())
                .processor(stationInitProcessor())
                .writer(stationWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step chargerInitStep() {
        return stepBuilderFactory.get("chargerInitStep")
                .<ChargerClientResponseDto, ChargerCreateRequestDto>chunk(restProperties.getEvinfoChunk().intValue())
                .reader(EVInfoReader())
                .processor(chargerInitProcessor())
                .writer(chargerWriter())
                .build();
    }

    private ItemReader<ChargerClientResponseDto> EVInfoReader() {
        if (chargerResponses.isEmpty()) {
            chargerResponses = chargerClient.fetchChargers();
        }
        return new LinkedListItemReader<>(chargerResponses);
    }

    private ItemProcessor<ChargerClientResponseDto, Station> stationInitProcessor() {
        return response -> {
            var station = response.getStation();
            if (stationExist.containsKey(station.getStationId())) {
                return null;
            }
            stationExist.put(station.getStationId(), station);
            var charger = response.getCharger();
            station.addCharger(charger);

            return station;
        };
    }

    private ItemWriter<Station> stationWriter() {
        JdbcBatchItemWriter<Station> itemWriter = new JdbcBatchItemWriterBuilder<Station>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("replace into station(station_id, station_name, address, location, latitude, longitude, call_number, use_time) " +
                        "values(:stationId, :stationName, :address, :location, :latitude, :longitude, :callNumber, :useTime) ")
                .build();

        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    private ItemProcessor<ChargerClientResponseDto, ChargerCreateRequestDto> chargerInitProcessor() {
        return response -> {
            var charger = response.getCharger();
            var station = response.getStation();
            charger.updateStation(station);

            return new ChargerCreateRequestDto(charger);
        };
    }

    private ItemWriter<ChargerCreateRequestDto> chargerWriter() {
        JdbcBatchItemWriter<ChargerCreateRequestDto> itemWriter = new JdbcBatchItemWriterBuilder<ChargerCreateRequestDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("replace into charger(charger_id, station_id, charger_stat, charger_type) " +
                        "values(:chargerId, :stationId, :chargerStat, :chargerType) ")
                .build();

        itemWriter.afterPropertiesSet();

        return itemWriter;
    }
}
