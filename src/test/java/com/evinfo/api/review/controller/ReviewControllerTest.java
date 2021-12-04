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
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ReviewController.class})
public class ReviewControllerTest extends Documentation {
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

    @DisplayName("'/reviews'로 POST 요청 시, 리뷰를 생성하고 값을 반환한다.")
    @Test
    void createReviewTest() throws Exception {
        // TODO: 2021/12/04 추후 util 클래스 만들어서 리팩토링하기
        Station station = ChargerGenerator.getStations().get(0);
        ReviewResponseDto response = new ReviewResponseDto(new Review(1L, "내용", 5.0, station));
        ReviewCreateRequestDto request = new ReviewCreateRequestDto("내용", 5.0, station.getStationId());
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
                                fieldWithPath("content").type(JsonFieldType.STRING).description("생성할 리뷰의 내용"),
                                fieldWithPath("star").type(JsonFieldType.NUMBER).description("생성할 리뷰의 0.5점 단위의 별점"),
                                fieldWithPath("stationId").type(JsonFieldType.STRING).description("생성할 리뷰의 충전소 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 리뷰의 ID"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("생성된 리뷰의 내용"),
                                fieldWithPath("star").type(JsonFieldType.NUMBER).description("생성된 리뷰의 0.5점 단위의 별점")
                        )
                ));
    }
}
