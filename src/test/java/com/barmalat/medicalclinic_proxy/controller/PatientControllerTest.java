//package com.barmalat.medicalclinic_proxy.controller;
//
//import com.barmalat.medicalclinic_proxy.model.PageResponse;
//import com.barmalat.medicalclinic_proxy.model.VisitDto;
//import com.barmalat.medicalclinic_proxy.model.VisitStatus;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureWireMock(port = 8112)
//public class PatientControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private WireMockServer wireMockServer;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void resetWireMock() {
//        wireMockServer.resetAll();
//    }
//
//    @Test
//    void findPatientVisits_DataCorrect_PageVisitDtoReturn() throws Exception {
//        //given
//        PageResponse<VisitDto> response = new PageResponse<>(
//                List.of(new VisitDto(1L, null, null,
//                        LocalDateTime.of(2027, 1, 1, 10, 0),
//                        LocalDateTime.of(2027, 1, 1, 10, 30),
//                        VisitStatus.RESERVED)),
//                1, 1L, 20, 0);
//        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
//                .withQueryParam("patientId", equalTo("1"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(200)
//                        .withBody(objectMapper.writeValueAsString(response))));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1/visits"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].status").value("RESERVED"))
//                .andExpect(jsonPath("$.totalElements").value(1L));
//    }
//
//    @Test
//    void findPatientVisits_ServiceUnavailable_Returns503() throws Exception {
//        //given
//        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
//                .withQueryParam("patientId", equalTo("1"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(503)
//                        .withBody("{\"message\":\"Serwis niedostępny\",\"status\":\"SERVICE_UNAVAILABLE\"}")));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1/visits"))
//                .andExpect(status().isServiceUnavailable());
//        wireMockServer.verify(3, getRequestedFor(urlPathEqualTo("/visits")));
//    }
//
//    @Test
//    void addPatientToVisit_DataCorrect_VisitDtoReturn() throws Exception {
//        //given
//        VisitDto response = new VisitDto(1L, null, null,
//                LocalDateTime.of(2027, 1, 1, 10, 0),
//                LocalDateTime.of(2027, 1, 1, 10, 30),
//                VisitStatus.RESERVED);
//        wireMockServer.stubFor(patch(urlPathEqualTo("/visits/1/patient/2"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(200)
//                        .withBody(objectMapper.writeValueAsString(response))));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/2/visits/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.status").value("RESERVED"));
//    }
//
//    @Test
//    void addPatientToVisit_VisitNotAvailable_Returns409() throws Exception {
//        //given
//        wireMockServer.stubFor(patch(urlPathEqualTo("/visits/1/patient/2"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(409)
//                        .withBody("{\"message\":\"Wybrana wizyta nie jest wolna.\",\"status\":\"CONFLICT\"}")));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/2/visits/1"))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value("Wybrana wizyta nie jest wolna."));
//    }
//}