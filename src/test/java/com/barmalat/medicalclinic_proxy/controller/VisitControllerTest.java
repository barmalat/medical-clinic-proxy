package com.barmalat.medicalclinic_proxy.controller;

import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.model.VisitStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8112)
public class VisitControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WireMockServer wireMockServer;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findVisits_BySpecializationAndTimeRange_PageVisitDtoReturn() throws Exception {
        //given
        PageResponse<VisitDto> response = new PageResponse<>(
                List.of(new VisitDto(1L, null, null,
                        LocalDateTime.of(2027, 1, 1, 10, 0),
                        LocalDateTime.of(2027, 1, 1, 10, 30),
                        VisitStatus.RESERVED)),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("specialization", equalTo("chirurg"))
                .withQueryParam("from", equalTo("2027-01-01T00:00"))
                .withQueryParam("to", equalTo("2027-01-31T23:59"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(response))));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits")
                        .param("specialization", "chirurg")
                        .param("from", "2027-01-01T00:00")
                        .param("to", "2027-01-31T23:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].status").value("RESERVED"))
                .andExpect(jsonPath("$.totalElements").value(1L));
    }

    @Test
    void findVisits_ServiceUnavailable_Returns503() throws Exception {
        //given
        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(503)
                        .withBody("{\"message\":\"Serwis niedostępny\",\"status\":\"SERVICE_UNAVAILABLE\"}")));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits")
                        .param("specialization", "chirurg")
                        .param("from", "2027-01-01T00:00")
                        .param("to", "2027-01-31T23:59"))
                .andExpect(status().isServiceUnavailable());
        wireMockServer.verify(3, getRequestedFor(urlPathEqualTo("/visits")));
    }

    @Test
    void getAvailableVisits_ByDateAndSpecialization_PageVisitDtoReturn() throws Exception {
        //given
        PageResponse<VisitDto> response = new PageResponse<>(
                List.of(new VisitDto(1L, null, null,
                        LocalDateTime.of(2027, 1, 1, 10, 0),
                        LocalDateTime.of(2027, 1, 1, 10, 30),
                        VisitStatus.AVAILABLE)),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits/available"))
                .withQueryParam("date", equalTo("2027-01-01"))
                .withQueryParam("specialization", equalTo("chirurg"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(response))));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/available")
                        .param("date", "2027-01-01")
                        .param("specialization", "chirurg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("AVAILABLE"));
    }

    @Test
    void getAvailableVisits_ByTimeRange_PageVisitDtoReturn() throws Exception {
        //given
        PageResponse<VisitDto> response = new PageResponse<>(
                List.of(new VisitDto(1L, null, null,
                        LocalDateTime.of(2027, 1, 1, 10, 0),
                        LocalDateTime.of(2027, 1, 1, 10, 30),
                        VisitStatus.AVAILABLE)),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits/available"))
                .withQueryParam("from", equalTo("2027-01-01T00:00"))
                .withQueryParam("to", equalTo("2027-01-31T23:59"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(response))));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/available")
                        .param("from", "2027-01-01T00:00")
                        .param("to", "2027-01-31T23:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("AVAILABLE"));
    }

    @Test
    void cancelVisit_DataCorrect_VisitDtoReturn() throws Exception {
        //given
        VisitDto response = new VisitDto(1L, null, null,
                LocalDateTime.of(2027, 1, 1, 10, 0),
                LocalDateTime.of(2027, 1, 1, 10, 30),
                VisitStatus.CANCELLED);
        wireMockServer.stubFor(patch(urlPathEqualTo("/visits/1/cancel"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(response))));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void cancelVisit_VisitAlreadyCancelled_Returns409() throws Exception {
        //given
        wireMockServer.stubFor(patch(urlPathEqualTo("/visits/1/cancel"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(409)
                        .withBody("{\"message\":\"Wizyta jest już odwołana.\",\"status\":\"CONFLICT\"}")));
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/1/cancel"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Wizyta jest już odwołana."));
    }
}