package com.evinfo.api.review.controller;

import com.evinfo.api.review.dto.ReviewCreateRequestDto;
import com.evinfo.api.review.dto.ReviewResponseDto;
import com.evinfo.api.review.service.ReviewService;
import com.evinfo.docs.Documentation;
import com.evinfo.domain.charger.Station;
import com.evinfo.domain.review.Review;
import com.evinfo.utils.ChargerGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ReviewController.class})
class ReviewControllerTest extends Documentation {
    private static final String API = "/api";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private ReviewService reviewService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("'/reviews'??? POST ?????? ???, ????????? ???????????? ?????? ????????????.")
    @Test
    void createReviewTest() throws Exception {
        // TODO: 2021/12/04 ?????? util ????????? ???????????? ??????????????????
        Station station = ChargerGenerator.getStations().get(0);
        ReviewResponseDto response = new ReviewResponseDto(new Review(1L, "??????", 5.0, LocalDateTime.now(), station));
        ReviewCreateRequestDto request = new ReviewCreateRequestDto("??????", 5.0, station.getStationId());
        String requestMapped = OBJECT_MAPPER.writeValueAsString(request);
        when(reviewService.createReview(any())).thenReturn(response);

        this.mockMvc.perform(post(API + "/reviews")
                        .content(requestMapped)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.star").value(response.getStar()))
                .andDo(print())
                .andDo(document("reviews/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                fieldWithPath("star").type(JsonFieldType.NUMBER).description("????????? ????????? 0.5??? ????????? ??????"),
                                fieldWithPath("stationId").type(JsonFieldType.STRING).description("????????? ????????? ????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                fieldWithPath("star").type(JsonFieldType.NUMBER).description("????????? ????????? 0.5??? ????????? ??????"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ?????? ??? ??????")
                        )
                ));
    }

    @DisplayName("'/reviews'??? GET ?????? ???, ???????????? stationId??? ???????????? ????????? ??????????????? ????????????.")
    @Test
    void getReviewsTest() throws Exception {
        List<ReviewResponseDto> responses = ChargerGenerator.getReviews()
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
        when(reviewService.getReviewsByStationId(anyString())).thenReturn(responses);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("stationId", Collections.singletonList("STATION_ID"));

        this.mockMvc.perform(get(API + "/reviews")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].content").value(responses.get(0).getContent()))
                .andExpect(jsonPath("$[1].content").value(responses.get(1).getContent()))
                .andExpect(jsonPath("$[0].star").value(responses.get(0).getStar()))
                .andExpect(jsonPath("$[1].star").value(responses.get(1).getStar()))
                .andDo(print())
                .andDo(document("reviews/get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("stationId").description("????????? ??????????????? ?????? ???????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("????????? ????????? ??????"),
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                fieldWithPath("[].star").type(JsonFieldType.NUMBER).description("????????? ????????? 0.5??? ????????? ??????"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("????????? ????????? ?????? ?????? ??? ??????")
                        )
                ));
    }

    @DisplayName("'/reviews'??? DELETE ?????? ???, ????????? ????????? ????????????.")
    @Test
    void deleteReviewTest() throws Exception {
        doNothing().when(reviewService).deleteReview(anyLong());

        this.mockMvc.perform(delete(API + "/reviews/{reviewId}", 1L)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("reviews/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("reviewId").description("????????? ????????? ID")
                        )
                ));
    }
}
