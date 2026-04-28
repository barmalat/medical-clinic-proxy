package com.barmalat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class PatientServiceTest {
    PatientService patientService;
    MedicalclinicProvider provider;

    @BeforeEach
    void setup() {
        this.provider = Mockito.mock(MedicalclinicProvider.class);
        this.patientService = new PatientService(provider);
    }

    @Test
    void findPatientVisits_DataCorrect_PageResponseWithVisitsReturned() {
        //given
        Long patientId = 1L;
        int page = 0;
        int size = 5;
        Patient patient = new Patient(1L, "test@patient.pl", "666", "jan", "nowak", "1233", "1.1.2001");
        Visit visit = new Visit(3L, null, patient, null, null, VisitStatus.RESERVED);
        PageResponse<Visit> visits = new PageResponse<>(List.of(visit), 0, 1, 1, 1);
        when(provider.findVisits(patientId, null, null, null, null, page, size)).thenReturn(visits);
        //when
        PageResponse<Visit> result = patientService.findPatientVisits(patientId, page, size);
        //then
        Assertions.assertAll(
                () -> assertEquals(1, result.totalElements()),
                () -> assertEquals(visits.content(), result.content())
        );
        verify(provider, times(1)).findVisits(patientId, null, null, null, null, page, size);
        verifyNoMoreInteractions(provider);
    }

    @Test
    void addPatientToVisit_DataCorrect_VisitReturned() {
        //given
        Long visitId = 1L;
        Long patientId = 2L;
        Patient patient = new Patient(2L, "test@patient.pl", "666", "jan", "nowak", "1233", "1.1.2001");
        Visit visit = new Visit(1L, null, patient, null, null, VisitStatus.RESERVED);
        when(provider.addPatientToVisit(visitId, patientId)).thenReturn(visit);
        //when
        Visit result = patientService.addPatientToVisit(visitId, patientId);
        //then
        Assertions.assertAll(
                () -> assertEquals(visit.id(), result.id()),
                () -> assertEquals(visit.patient(), result.patient()),
                () -> assertEquals(visit.status(), result.status())
        );
        verify(provider, times(1)).addPatientToVisit(visitId, patientId);
        verifyNoMoreInteractions(provider);
    }
}