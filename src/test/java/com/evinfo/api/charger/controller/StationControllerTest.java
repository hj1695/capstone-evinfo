package com.evinfo.api.charger.controller;

import com.evinfo.api.charger.dto.StationResponseDto;
import com.evinfo.api.charger.service.StationService;
import com.evinfo.docs.Documentation;
import com.evinfo.global.error.ErrorCode;
import com.evinfo.utils.ChargerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {StationController.class})
class StationControllerTest extends Documentation {
    private static final String API = "/api";
    private static final String 사용자_위도 = "11.22";
    private static final String 사용자_경도 = "22.33";
    private static final String 사용자_요구_데이터_크기 = "10";
    private static final List<String> 사용자_필터링_타입 = Arrays.asList("1", "2", "3");

    @MockBean
    private StationService stationService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("'/stations'로 GET 요청 시, 충전기의 목록을 반환한다.")
    @Test
    void getStationsTest() throws Exception {
        List<StationResponseDto> stationResponses = ChargerGenerator.getStations()
                .stream()
                .map(station -> new StationResponseDto(station, 10.0))
                .collect(Collectors.toList());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("latitude", Collections.singletonList(사용자_위도));
        params.put("longitude", Collections.singletonList(사용자_경도));
        params.put("size", Collections.singletonList(사용자_요구_데이터_크기));
        params.put("chargerTypes", 사용자_필터링_타입);
        when(stationService.getStations(any())).thenReturn(stationResponses);

        this.mockMvc.perform(get(API + "/stations")
                .params(params)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].stationId").value(stationResponses.get(0).getStationId()))
                .andExpect(jsonPath("$[1].stationId").value(stationResponses.get(1).getStationId()))
                .andExpect(jsonPath("$[0].stationName").value(stationResponses.get(0).getStationName()))
                .andExpect(jsonPath("$[1].stationName").value(stationResponses.get(1).getStationName()))
                .andDo(print())
                .andDo(document("stations/gets",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("latitude").description("사용자의 위도 값"),
                                parameterWithName("longitude").description("사용자의 경도 값"),
                                parameterWithName("size").description("반환받고자 하는 사용자 주변 충전소 개수. 최대 1000개로 제한"),
                                parameterWithName("chargerTypes").description("반환받고자 하는 정수형의 충전기 타입 ID 목록")
                        ),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("전체 충전소의 목록"),
                                fieldWithPath("[].stationId").type(JsonFieldType.STRING).description("충전소의 ID"),
                                fieldWithPath("[].stationName").type(JsonFieldType.STRING).description("충전소의 이름"),
                                fieldWithPath("[].address").type(JsonFieldType.STRING).description("충전소의 주소"),
                                fieldWithPath("[].location").type(JsonFieldType.STRING).description("충전소의 세부 주소"),
                                fieldWithPath("[].useTime").type(JsonFieldType.STRING).description("충전소의 사용 가능 시간"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("충전소의 위도"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("충전소의 경도"),
                                fieldWithPath("[].callNumber").type(JsonFieldType.STRING).description("충전소의 전화번호"),
                                fieldWithPath("[].businessName").type(JsonFieldType.STRING).description("충전소의 운영기관 이름"),
                                fieldWithPath("[].isLimit").type(JsonFieldType.BOOLEAN).description("충전소의 외부인 이용가능 여부"),
                                fieldWithPath("[].isParkingFree").type(JsonFieldType.BOOLEAN).description("충전소의 주차요금 무료 여부"),
                                fieldWithPath("[].distance").type(JsonFieldType.NUMBER).description("현재 위치 기준 충전소와의 거리(단위: km)"),
                                fieldWithPath("[].chargerTypes").type(JsonFieldType.ARRAY).description("충전소에 사용 가능한 충전기 타입의 ID 목록"),
                                fieldWithPath("[].enableChargers").type(JsonFieldType.NUMBER).description("충전소에 사용 가능한 대기 충전기의 개수"),
                                fieldWithPath("[].chargers").type(JsonFieldType.ARRAY).description("충전소의 충전기 목록"),
                                fieldWithPath("[].chargers[].chargerId").type(JsonFieldType.STRING).description("충전소에 속하는 충전기의 ID"),
                                fieldWithPath("[].chargers[].output").type(JsonFieldType.NUMBER).description("충전소에 속하는 충전기의 출력 kw값"),
                                fieldWithPath("[].chargers[].price").type(JsonFieldType.NUMBER).description("충전소에 속하는 충전기의 가격"),
                                fieldWithPath("[].chargers[].isDCCombo").type(JsonFieldType.BOOLEAN).description("충전소에 속하는 충전기의 타입이 DC 콤보(급속)에 속하는지 여부"),
                                fieldWithPath("[].chargers[].isDCDemo").type(JsonFieldType.BOOLEAN).description("충전소에 속하는 충전기의 타입이 DC 데모(급속)에 속하는지 여부"),
                                fieldWithPath("[].chargers[].isAC3").type(JsonFieldType.BOOLEAN).description("충전소에 속하는 충전기의 타입이 AC3 상(급속)에 속하는지 여부"),
                                fieldWithPath("[].chargers[].isACSlow").type(JsonFieldType.BOOLEAN).description("충전소에 속하는 충전기의 타입이 AC 단상(5핀, 완속)에 속하는지 여부"),
                                fieldWithPath("[].chargers[].chargerStat").type(JsonFieldType.STRING).description("충전소에 속하는 충전기의 현재 상태"),
                                fieldWithPath("[].chargers[].lastChargeDateTime").type(JsonFieldType.STRING).description("충전소에 속하는 충전기의 마지막 충전 종료시간. 오류시 2000-01-01 반환"),
                                fieldWithPath("[].chargers[].startChargeDateTime").type(JsonFieldType.STRING).description("충전소에 속하는 충전기의 최근 충전 시작시간. 오류시 2000-01-01 반환")
                        )
                ));
    }

    @DisplayName("예외 테스트: '/stations'로 GET 요청 시, 위도 정보가 누락되면 예외를 반환한다.")
    @Test
    void getStationsWithoutLatitudeTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("latitude", Collections.emptyList());
        params.put("longitude", Collections.singletonList(사용자_경도));
        params.put("size", Collections.singletonList(사용자_요구_데이터_크기));

        this.mockMvc.perform(get(API + "/stations")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andDo(print());
    }

    @DisplayName("예외 테스트: '/stations'로 GET 요청 시, 경도 정보가 누락되면 예외를 반환한다.")
    @Test
    void getStationsWithoutLongitudeTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("latitude", Collections.singletonList(사용자_위도));
        params.put("longitude", Collections.emptyList());
        params.put("size", Collections.singletonList(사용자_요구_데이터_크기));

        this.mockMvc.perform(get(API + "/stations")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andDo(print());
    }

    @DisplayName("예외 테스트: '/stations'로 GET 요청 시, 경도 정보가 누락되면 예외를 반환한다.")
    @Test
    void getStationsWithoutSizeTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("latitude", Collections.singletonList(사용자_위도));
        params.put("longitude", Collections.singletonList(사용자_경도));
        params.put("size", Collections.emptyList());

        this.mockMvc.perform(get(API + "/stations")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andDo(print());
    }
}
