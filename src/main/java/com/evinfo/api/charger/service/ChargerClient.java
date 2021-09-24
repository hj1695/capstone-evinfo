package com.evinfo.api.charger.service;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.api.charger.dto.client.EvinfoBodyResponseDto;
import com.evinfo.api.charger.dto.client.EvinfoResponseDto;
import com.evinfo.global.common.RestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO: 2021/09/07 필터 부분은 진짜 springmvc filter로 분리할 수 있을듯..? 일단은 냅두기
@Slf4j(topic = "HTTP_FILE_LOGGER")
@Service
public class ChargerClient {
    private final WebClient webClient;
    private final RestProperties restProperties;

    public ChargerClient(WebClient.Builder webClientBuilder, final RestProperties restProperties) {
        var factory = new DefaultUriBuilderFactory(restProperties.getEvinfoUrl());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        var exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024)) // TODO: 2021/09/20 이거 yml에 직접 넣쟈?
                .build();

        this.restProperties = restProperties;
        this.webClient = webClientBuilder
                .uriBuilderFactory(factory)
                .baseUrl(restProperties.getEvinfoUrl())
                .exchangeStrategies(exchangeStrategies)
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    // TODO: 2021/09/11 추후 로그인터셉터 추가한 뒤, 해당 로그 필요한지 검토할것.
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isInfoEnabled()) {
                log.info("url : {}", clientRequest.url());
                log.info("request : {}", clientRequest.method());
                clientRequest.headers()
                        .forEach((name, values) ->
                                values.forEach(value ->
                                        log.info("{} : {}", name, value)));
            }
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (log.isInfoEnabled()) {
                log.info("response : {}", clientResponse.statusCode());
                clientResponse.headers()
                        .asHttpHeaders()
                        .forEach((name, values) ->
                                values.forEach(value ->
                                        log.info("{} : {}", name, value)));
            }
            return Mono.just(clientResponse);
        });
    }

    public List<ChargerClientResponseDto> fetchChargers() {
        return Flux.interval(Duration.ofMillis(100))
                .take(restProperties.getEvinfoIterator())
                .flatMap(i -> getEvinfoMono(i + 1))
                .toStream()
                .map(EvinfoResponseDto::getBody)
                .map(EvinfoBodyResponseDto::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Mono<EvinfoResponseDto> getEvinfoMono(long page) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", restProperties.getEvinfoKey())
                        .queryParam("numOfRows", restProperties.getEvinfoChunk())
                        .queryParam("pageNo", page)
                        .build())
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .bodyToMono(EvinfoResponseDto.class);
    }
}
