package com.barmalat.medicalclinic_proxy.client;

import com.barmalat.medicalclinic_proxy.model.DoctorDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.model.VisitStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWireMock(port = 8112)
@SpringBootTest
public class MedicalclinicClientTest {
    @Autowired
    private WireMockServer wireMockServer;
    @Autowired
    private MedicalclinicClient client;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findDoctors_DataCorrect_PageDoctorDtoReturn() throws JsonProcessingException {
        //given
        PageResponse<DoctorDto> response = new PageResponse<>(
                List.of(new DoctorDto(1L, "doc@pl", "chirurg", "Jan", "Kowalski", List.of())),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/doctors"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        PageResponse<DoctorDto> result = client.findDoctors(null, 0, 20);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.totalElements()),
                () -> assertEquals(1L, result.content().getFirst().id()),
                () -> assertEquals("chirurg", result.content().getFirst().specialization())
        );
    }

    @Test
    void findDoctors_BySpecialization_PageDoctorDtoReturn() throws JsonProcessingException {
        //given
        PageResponse<DoctorDto> response = new PageResponse<>(
                List.of(new DoctorDto(1L, "doc@pl", "chirurg", "Jan", "Kowalski", List.of())),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/doctors"))
                .withQueryParam("specialization", equalTo("chirurg"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        PageResponse<DoctorDto> result = client.findDoctors("chirurg", 0, 20);
        //then
        assertEquals("chirurg", result.content().getFirst().specialization());
    }

    @Test
    void findVisits_ByPatientId_PageVisitDtoReturn() throws JsonProcessingException {
        //given
        PageResponse<VisitDto> response = new PageResponse<>(
                List.of(new VisitDto(1L, null, null,
                        LocalDateTime.of(2027, 1, 1, 10, 0),
                        LocalDateTime.of(2027, 1, 1, 10, 30),
                        VisitStatus.RESERVED)),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("patientId", equalTo("1"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        PageResponse<VisitDto> result = client.findVisits(1L, null, null, null, null, 0, 20);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.totalElements()),
                () -> assertEquals(VisitStatus.RESERVED, result.content().getFirst().status())
        );
    }

    @Test
    void findVisits_ByDoctorId_PageVisitDtoReturn() throws JsonProcessingException {
        //given
        PageResponse<VisitDto> response = new PageResponse<>(
                List.of(new VisitDto(1L, null, null,
                        LocalDateTime.of(2027, 1, 1, 10, 0),
                        LocalDateTime.of(2027, 1, 1, 10, 30),
                        VisitStatus.AVAILABLE)),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("doctorId", equalTo("1"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        PageResponse<VisitDto> result = client.findVisits(null, 1L, null, null, null, 0, 20);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.totalElements()),
                () -> assertEquals(VisitStatus.AVAILABLE, result.content().getFirst().status())
        );
    }

    @Test
    void findAvailableVisits_ByDateAndSpecialization_PageVisitDtoReturn() throws JsonProcessingException {
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
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        PageResponse<VisitDto> result = client.findAvailableVisits("2027-01-01", "chirurg", null, null, 0, 20);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.totalElements()),
                () -> assertEquals(VisitStatus.AVAILABLE, result.content().getFirst().status())
        );
    }

    @Test
    void findAvailableVisitsByDoctorId_DataCorrect_PageVisitDtoReturn() throws JsonProcessingException {
        //given
        PageResponse<VisitDto> response = new PageResponse<>(
                List.of(new VisitDto(1L, null, null,
                        LocalDateTime.of(2027, 1, 1, 10, 0),
                        LocalDateTime.of(2027, 1, 1, 10, 30),
                        VisitStatus.AVAILABLE)),
                1, 1L, 20, 0);
        wireMockServer.stubFor(get(urlPathEqualTo("/visits/doctor/1/available"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        PageResponse<VisitDto> result = client.findAvailableVisitsByDoctorId(1L, 0, 20);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.totalElements()),
                () -> assertEquals(VisitStatus.AVAILABLE, result.content().getFirst().status())
        );
    }

    @Test
    void addPatientToVisit_DataCorrect_VisitDtoReturn() throws JsonProcessingException {
        //given
        VisitDto response = new VisitDto(1L, null, null,
                LocalDateTime.of(2027, 1, 1, 10, 0),
                LocalDateTime.of(2027, 1, 1, 10, 30),
                VisitStatus.RESERVED);
        wireMockServer.stubFor(patch(urlPathEqualTo("/visits/1/patient/2"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        VisitDto result = client.addPatientToVisit(1L, 2L);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.id()),
                () -> assertEquals(VisitStatus.RESERVED, result.status())
        );
    }

    @Test
    void cancelVisit_DataCorrect_VisitDtoReturn() throws JsonProcessingException {
        //given
        VisitDto response = new VisitDto(1L, null, null,
                LocalDateTime.of(2027, 1, 1, 10, 0),
                LocalDateTime.of(2027, 1, 1, 10, 30),
                VisitStatus.CANCELLED);
        wireMockServer.stubFor(patch(urlPathEqualTo("/visits/1/cancel"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)));
        //when
        VisitDto result = client.cancelVisit(1L);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.id()),
                () -> assertEquals(VisitStatus.CANCELLED, result.status())
        );
    }
}