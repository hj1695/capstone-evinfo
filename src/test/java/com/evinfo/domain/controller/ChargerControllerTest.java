package com.evinfo.domain.controller;

import com.evinfo.domain.charger.domain.Charger;
import com.evinfo.domain.charger.service.ChargerClient;
import com.evinfo.utils.ChargerGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ChargerControllerTest {
    private static final String API = "/api";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private ChargerClient chargerClient;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("'/chargers'로 GET 요청 시, 충전기의 목록을 반환한다.")
    @Test
    void getChargersTest() throws Exception {
        List<Charger> chargerResponses = ChargerGenerator.getChargers();
        when(chargerClient.getChargers()).thenReturn(chargerResponses);

        this.mockMvc.perform(get(API + "/chargers")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(chargerResponses.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(chargerResponses.get(1).getId()))
                .andExpect(jsonPath("$[0].stationName").value(chargerResponses.get(0).getStationName()))
                .andExpect(jsonPath("$[1].stationName").value(chargerResponses.get(1).getStationName()))
                .andDo(print());
    }
}
