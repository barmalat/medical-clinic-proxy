package com.barmalat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class VisitServiceTest {
    VisitService visitService;
    MedicalclinicProvider provider;

    @BeforeEach
    void setup() {
        this.provider = Mockito.mock(MedicalclinicProvider.class);
        this.visitService = new VisitService(provider);
    }

    @Test
    void findVisits_DataCorrect_PageResponseWithVisitsReturned() {
        //given
        String specialization = "chirurg";
        String from = "2028-09-19T00:00";
        String to = "2028-09-20T00:00";
        int page = 0;
        int size = 5;
        Doctor doctor = new Doctor(1L, "test@doctor.pl", "chirurg", "jan", "brzechwa", null);
        Visit visit1 = new Visit(3L, doctor, null, LocalDateTime.of(2028,9,19,10,30),
                LocalDateTime.of(2028,9,19,10,45), VisitStatus.AVAILABLE);
        Visit visit2 = new Visit(3L, doctor, null, LocalDateTime.of(2028,9,19,11,30),
                LocalDateTime.of(2028,9,19,11,45), VisitStatus.AVAILABLE);
        PageResponse<Visit> visits = new PageResponse<>(List.of(visit1,visit2), 0, 2, 2, 2);
        when(provider.findVisits(null,null,specialization,from,to,page,size)).thenReturn(visits);
        //when
        PageResponse<Visit> result = visitService.findVisits(specialization, from, to, page, size);
        //then
        Assertions.assertAll(
                () -> assertEquals(2, result.totalElements()),
                () -> assertEquals(visits.content(), result.content())
        );
        verify(provider, times(1)).findVisits(null, null, specialization, from, to, page, size);
        verifyNoMoreInteractions(provider);
    }

    @Test
    void findAvailableVisits_DataCorrect_PageResponseWithVisitsReturned() {
        //given
        String date = "2028-09-20";
        String specialization = "chirurg";
        String from = "2028-09-19T00:00";
        String to = "2028-09-20T00:00";
        int page = 0;
        int size = 5;
        Doctor doctor = new Doctor(1L, "test@doctor.pl", "chirurg", "jan", "brzechwa", null);
        Visit visit1 = new Visit(3L, doctor, null, LocalDateTime.of(2028,9,19,10,30),
                LocalDateTime.of(2028,9,19,10,45), VisitStatus.AVAILABLE);
        Visit visit2 = new Visit(3L, doctor, null, LocalDateTime.of(2028,9,19,11,30),
                LocalDateTime.of(2028,9,19,11,45), VisitStatus.AVAILABLE);
        PageResponse<Visit> visits = new PageResponse<>(List.of(visit1,visit2), 0, 2, 2, 2);
        when(provider.findAvailableVisits(date,specialization,from,to,page,size)).thenReturn(visits);
        //when
        PageResponse<Visit> result = visitService.findAvailableVisits(date,specialization,from,to,page,size);
        //then
        Assertions.assertAll(
                () -> assertEquals(2, result.totalElements()),
                () -> assertEquals(visits.content(), result.content())
        );
        verify(provider, times(1)).findAvailableVisits(date, specialization, from, to, page, size);
        verifyNoMoreInteractions(provider);
    }

    @Test
    void cancelVisit_DataCorrect_VisitReturned() {
        //given
        Long visitId = 3L;
        Doctor doctor = new Doctor(1L, "test@doctor.pl", "chirurg", "jan", "brzechwa", null);
        Visit visit = new Visit(3L, doctor, null, LocalDateTime.of(2028,9,19,10,30),
                LocalDateTime.of(2028,9,19,10,45), VisitStatus.CANCELLED);
        when(provider.cancelVisit(visitId)).thenReturn(visit);
        //when
        Visit result = visitService.cancelVisit(visitId);
        //then
        Assertions.assertAll(
                () -> assertEquals(visit.id(), result.id()),
                () -> assertEquals(visit.doctor(), result.doctor()),
                () -> assertEquals(visit.startTime(), result.startTime()),
                () -> assertEquals(visit.endTime(), result.endTime()),
                () -> assertEquals(visit.status(), result.status())
        );
        verify(provider, times(1)).cancelVisit(visitId);
        verifyNoMoreInteractions(provider);
    }
}