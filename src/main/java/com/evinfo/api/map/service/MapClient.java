package com.evinfo.api.map.service;

import com.evinfo.api.map.dto.LocationResponseDto;
import com.evinfo.api.map.dto.kakao.KakaoMapDto;
import com.evinfo.global.common.RestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "HTTP_FILE_LOGGER")
@Service
public class MapClient {
    private final WebClient webClient;

    public MapClient(WebClient.Builder webClientBuilder, final RestProperties restProperties) {
        this.webClient = webClientBuilder
                .baseUrl(restProperties.getKakaoUrl())
                .defaultHeader("Authorization", restProperties.getKakaoAuthorization())
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

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

    public List<LocationResponseDto> getMapDocuments(final Double longitude, final Double latitude, final String category) {
        KakaoMapDto kakaoMapDto = getKakaoMapMonos(longitude, latitude, category).blockOptional()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 KAKAO API 요청입니다."));

        return kakaoMapDto.getDocuments()
                .stream()
                .map(kakao -> {
                    return LocationResponseDto.builder()
                            .name(kakao.getPlaceName())
                            .address(kakao.getAddressName())
                            .distance(kakao.getDistance())
                            .latitude(kakao.getY())
                            .longitude(kakao.getX())
                            .callNumber(kakao.getPhone())
                            .placeUrl(kakao.getPlaceUrl())
                            .category(kakao.getCategoryGroupCode())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Mono<KakaoMapDto> getKakaoMapMonos(final Double longitude, final Double latitude, final String category) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/category.json")
                        .queryParam("category_group_code", category)
                        .queryParam("x", longitude)
                        .queryParam("y", latitude)
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("sort", "distance")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .bodyToMono(KakaoMapDto.class);
    }
}
