package com.evinfo.api.map.controller;


import com.evinfo.api.map.dto.LocationCategoryResponseDto;
import com.evinfo.api.map.dto.LocationResponseDto;
import com.evinfo.api.map.service.MapClient;
import com.evinfo.api.map.service.MapService;
import com.evinfo.docs.Documentation;
import com.evinfo.utils.MapGenerator;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
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

@WebMvcTest(controllers = {MapController.class})
class MapControllerTest extends Documentation {
    private static final String API = "/api";

    @MockBean
    private MapClient mapClient;
    @MockBean
    private MapService mapService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("'/locations'로 GET 요청 시, 카테고리에 맞는 가게의 목록을 반환한다.")
    @Test
    void getLocationsTest() throws Exception {
        List<LocationResponseDto> responses = MapGenerator.getLocations();
        when(mapClient.getMapDocuments(anyDouble(), anyDouble(), anyString())).thenReturn(responses);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("latitude", Collections.singletonList("37.0"));
        params.put("longitude", Collections.singletonList("127.0"));
        params.put("category", Collections.singletonList("CE7,MT1"));

        this.mockMvc.perform(get(API + "/locations")
                .params(params)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(responses.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(responses.get(1).getName()))
                .andExpect(jsonPath("$[0].address").value(responses.get(0).getAddress()))
                .andExpect(jsonPath("$[1].address").value(responses.get(1).getAddress()))
                .andDo(print())
                .andDo(document("locations/gets",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("latitude").description("사용자의 위도 값"),
                                parameterWithName("longitude").description("사용자의 경도 값"),
                                parameterWithName("category").description("반환받고자 하는 가게의 카테고리 문자열 목록")
                        ),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("전체 가게의 목록"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("가게의 상호명"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("가게의 위도 값"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("가게의 경도 값"),
                                fieldWithPath("[].distance").type(JsonFieldType.NUMBER).description("지정한 위치에서 가게까지의 거리"),
                                fieldWithPath("[].callNumber").type(JsonFieldType.STRING).description("가게의 전화번호"),
                                fieldWithPath("[].address").type(JsonFieldType.STRING).description("가게의 주소"),
                                fieldWithPath("[].placeUrl").type(JsonFieldType.STRING).description("가게의 daum map 기준 소개 url"),
                                fieldWithPath("[].category").type(JsonFieldType.STRING).description("가게가 속하는 카테고리 코드")
                        )
                ));
    }

    @DisplayName("'/locations/categories'로 GET 요청 시, 가게 카테고리의 목록을 반환한다.")
    @Test
    void getLocationCategoriesTest() throws Exception {
        List<LocationCategoryResponseDto> responses = MapGenerator.getLocationCategoryResponses();
        when(mapService.getLocationCategories()).thenReturn(responses);

        this.mockMvc.perform(get(API + "/locations/categories")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].key").value(responses.get(0).getKey()))
                .andExpect(jsonPath("$[1].key").value(responses.get(1).getKey()))
                .andExpect(jsonPath("$[0].name").value(responses.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(responses.get(1).getName()))
                .andDo(print())
                .andDo(document("locations/categories",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("가게 카테고리의 목록"),
                                fieldWithPath("[].key").type(JsonFieldType.STRING).description("가게 카테고리의 ID 값"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("가게 카테고리의 이름")
                        )
                ));
    }
}
