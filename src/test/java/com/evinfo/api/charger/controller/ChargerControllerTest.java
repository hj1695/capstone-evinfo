package com.evinfo.api.charger.controller;

import com.evinfo.api.charger.dto.ChargerResponseDto;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.api.charger.service.ChargerService;
import com.evinfo.docs.Documentation;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
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

@WebMvcTest(controllers = {ChargerController.class})
class ChargerControllerTest extends Documentation {
    private static final String API = "/api";

    @MockBean
    private ChargerClient chargerClient;

    @MockBean
    private ChargerService chargerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("'/chargers'로 GET 요청 시, 충전기의 목록을 반환한다.")
    @Test
    void getChargersTest() throws Exception {
        List<ChargerResponseDto> chargerResponses = ChargerGenerator.getChargers()
                .stream()
                .map(charger -> new ChargerResponseDto(charger, 10.0))
                .collect(Collectors.toList());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("latitude", Collections.singletonList("11.11"));
        params.put("longitude", Collections.singletonList("22.22"));
        params.put("size", Collections.singletonList("10"));
        when(chargerService.getChargers(any())).thenReturn(chargerResponses);

        this.mockMvc.perform(get(API + "/chargers")
                .params(params)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].stationId").value(chargerResponses.get(0).getStationId()))
                .andExpect(jsonPath("$[1].stationId").value(chargerResponses.get(1).getStationId()))
                .andExpect(jsonPath("$[0].stationName").value(chargerResponses.get(0).getStationName()))
                .andExpect(jsonPath("$[1].stationName").value(chargerResponses.get(1).getStationName()))
                .andDo(print())
                .andDo(document("chargers/gets",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("latitude").description("사용자의 위도 값"),
                                parameterWithName("longitude").description("사용자의 경도 값"),
                                parameterWithName("size").description("반환받고자 하는 사용자 주변 충전소 개수. 최대 1000개로 제한")
                        ),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("전체 충전기의 목록"),
                                fieldWithPath("[].stationName").type(JsonFieldType.STRING).description("충전기 충전소의 이름"),
                                fieldWithPath("[].stationId").type(JsonFieldType.STRING).description("충전기 충전소의 ID"),
                                fieldWithPath("[].chargerId").type(JsonFieldType.STRING).description("개별 충전기의 ID"),
                                fieldWithPath("[].isDCCombo").type(JsonFieldType.BOOLEAN).description("충전기의 타입이 DC 콤보(급속)에 속하는지 여부"),
                                fieldWithPath("[].isDCDemo").type(JsonFieldType.BOOLEAN).description("충전기의 타입이 DC 데모(급속)에 속하는지 여부"),
                                fieldWithPath("[].isAC3").type(JsonFieldType.BOOLEAN).description("충전기의 타입이 AC3 상(급속)에 속하는지 여부"),
                                fieldWithPath("[].isACSlow").type(JsonFieldType.BOOLEAN).description("충전기의 타입이 AC 단상(5핀, 완속)에 속하는지 여부"),
                                fieldWithPath("[].address").type(JsonFieldType.STRING).description("충전기의 주소"),
                                fieldWithPath("[].location").type(JsonFieldType.STRING).description("충전기의 세부 주소"),
                                fieldWithPath("[].useTime").type(JsonFieldType.STRING).description("충전기의 사용 가능 시간"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("충전기의 위도"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("충전기의 경도"),
                                fieldWithPath("[].callNumber").type(JsonFieldType.STRING).description("충전기 충전소의 전화번호"),
                                fieldWithPath("[].chargerStat").type(JsonFieldType.STRING).description("충전기의 상태"),
                                fieldWithPath("[].distance").type(JsonFieldType.NUMBER).description("현재 위치 기준 충전기의 위치")
                        )
                ));
    }
}
