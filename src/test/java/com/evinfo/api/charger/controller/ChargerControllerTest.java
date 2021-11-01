package com.evinfo.api.charger.controller;

import com.evinfo.api.charger.dto.ChargerTypeResponseDto;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ChargerController.class})
class ChargerControllerTest extends Documentation {
    private static final String API = "/api";

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

    @DisplayName("'/chargers/types'로 GET 요청 시, 충전기의 목록을 반환한다.")
    @Test
    void getChargerTypesTest() throws Exception {
        List<ChargerTypeResponseDto> responses = ChargerGenerator.getChargerTypeResponses();
        when(chargerService.getChargerTypes()).thenReturn(responses);

        this.mockMvc.perform(get(API + "/chargers/types")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].key").value(responses.get(0).getKey()))
                .andExpect(jsonPath("$[1].key").value(responses.get(1).getKey()))
                .andExpect(jsonPath("$[0].name").value(responses.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(responses.get(1).getName()))
                .andDo(print())
                .andDo(document("chargers/types",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("충전기 타입의 목록"),
                                fieldWithPath("[].key").type(JsonFieldType.NUMBER).description("충전기 타입의 ID 값"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("충전기 타입의 이름")
                        )
                ));
    }
}
