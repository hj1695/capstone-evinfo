package com.umjjal.domain.post.web.docs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umjjal.global.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CommonDocumentationTest extends Documentation {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("에러 공통 ResponseDto를 문서화한다.")
    @Test
    void commonsErrorResponse() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/docs/error")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andDo(print())
                .andDo(document("common",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("발생한 에러의 응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("발생한 에러의 상태값"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("발생한 에러의 응답 메시지")
                        )
                ));
    }

    @DisplayName("Enum 공통 클래스의 목록을 문서화한다.")
    @Test
    void commonsEnumTest() throws Exception {
        //when
        ResultActions result = this.mockMvc.perform(
                get("/docs")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = result.andReturn();
        DocCommons docs = getData(mvcResult);

        //then
        result.andExpect(status().isOk())
                .andDo(document("common",
                        customResponseFields("custom-response", beneathPath("errorCodes").withSubsectionId("errorCodes"),
                                attributes(key("title").value("에러코드")),
                                enumConvertFieldDescriptor(docs.getErrorCodes())
                        )
                ));
    }

    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {

        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }

    DocCommons getData(MvcResult result) throws IOException {
        System.out.println(result.getResponse().getContentAsString());
        DocCommons responses = OBJECT_MAPPER.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<DocCommons>() {
                });

        return responses;
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }
}
