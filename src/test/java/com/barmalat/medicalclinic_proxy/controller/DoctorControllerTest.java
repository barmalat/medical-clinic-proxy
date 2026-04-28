//package com.barmalat.medicalclinic_proxy.controller;
//
//import com.barmalat.medicalclinic_proxy.model.DoctorDto;
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
//@AutoConfigureWireMock(port = 8112)
//@AutoConfigureMockMvc
//public class DoctorControllerTest {
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
//    void findDoctors_WithoutSpecialization_PageDoctorDtoReturn() throws Exception {
//        //given
//        PageResponse<DoctorDto> response = new PageResponse<>(
//                List.of(new DoctorDto(1L, "doc@pl", "chirurg", "Jan", "Kowalski", List.of())),
//                1, 1L, 20, 0);
//        wireMockServer.stubFor(get(urlPathEqualTo("/doctors"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(200)
//                        .withBody(objectMapper.writeValueAsString(response))));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].specialization").value("chirurg"))
//                .andExpect(jsonPath("$.totalElements").value(1L));
//    }
//
//    @Test
//    void findDoctors_WithSpecialization_PageDoctorDtoReturn() throws Exception {
//        //given
//        PageResponse<DoctorDto> response = new PageResponse<>(
//                List.of(new DoctorDto(1L, "doc@pl", "chirurg", "Jan", "Kowalski", List.of())),
//                1, 1L, 20, 0);
//        wireMockServer.stubFor(get(urlPathEqualTo("/doctors"))
//                .withQueryParam("specialization", equalTo("chirurg"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(200)
//                        .withBody(objectMapper.writeValueAsString(response))));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors?specialization=chirurg"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].specialization").value("chirurg"));
//    }
//
//    @Test
//    void findDoctors_ServiceUnavailable_Returns503() throws Exception {
//        //given
//        wireMockServer.stubFor(get(urlPathEqualTo("/doctors"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(503)
//                        .withBody("{\"message\":\"Serwis niedostępny\",\"status\":\"SERVICE_UNAVAILABLE\"}")));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
//                .andExpect(status().isServiceUnavailable());
//        wireMockServer.verify(3, getRequestedFor(urlPathEqualTo("/doctors")));
//    }
//
//    @Test
//    void findDoctorVisits_DataCorrect_PageVisitDtoReturn() throws Exception {
//        //given
//        PageResponse<VisitDto> response = new PageResponse<>(
//                List.of(new VisitDto(1L, null, null,
//                        LocalDateTime.of(2027, 1, 1, 10, 0),
//                        LocalDateTime.of(2027, 1, 1, 10, 30),
//                        VisitStatus.AVAILABLE)),
//                1, 1L, 20, 0);
//        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
//                .withQueryParam("doctorId", equalTo("1"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(200)
//                        .withBody(objectMapper.writeValueAsString(response))));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/1/visits"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].status").value("AVAILABLE"));
//    }
//
//    @Test
//    void findDoctorVisits_DoctorNotFound_Returns404() throws Exception {
//        //given
//        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
//                .withQueryParam("doctorId", equalTo("99"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(404)
//                        .withBody("{\"message\":\"Nie znaleziono doktora o wskazanym ID.\",\"status\":\"NOT_FOUND\"}")));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/99/visits"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Nie znaleziono doktora o wskazanym ID."));
//    }
//
//    @Test
//    void findAvailableVisitsByDoctorId_DataCorrect_PageVisitDtoReturn() throws Exception {
//        //given
//        PageResponse<VisitDto> response = new PageResponse<>(
//                List.of(new VisitDto(1L, null, null,
//                        LocalDateTime.of(2027, 1, 1, 10, 0),
//                        LocalDateTime.of(2027, 1, 1, 10, 30),
//                        VisitStatus.AVAILABLE)),
//                1, 1L, 20, 0);
//        wireMockServer.stubFor(get(urlPathEqualTo("/visits/doctor/1/available"))
//                .willReturn(aResponse()
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withStatus(200)
//                        .withBody(objectMapper.writeValueAsString(response))));
//        //when+then
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/1/visit/available"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].status").value("AVAILABLE"));
//    }
//}