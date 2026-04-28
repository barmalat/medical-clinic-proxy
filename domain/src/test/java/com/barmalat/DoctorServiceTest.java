package com.barmalat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    DoctorService doctorService;
    MedicalclinicProvider provider;

    @BeforeEach
    void setup() {
        this.provider = Mockito.mock(MedicalclinicProvider.class);
        this.doctorService = new DoctorService(provider);
    }

    @Test
    void findDoctors_DataCorrect_PageResponseWithDoctorsReturned() {
        //given
        String specialization = "chirurg";
        int page = 0;
        int size = 5;
        Doctor doctor1 = new Doctor(1L, "test@doctor.pl", "chirurg", "jan", "brzechwa", null);
        Doctor doctor2 = new Doctor(2L, "test2@doctor.pl", "chirurg", "janusz", "brzechwalski", null);
        PageResponse<Doctor> doctors = new PageResponse<>(List.of(doctor1,doctor2), 0, 2,2,2);
        when(provider.findDoctors(specialization,page,size)).thenReturn(doctors);
        //when
        PageResponse<Doctor> result = doctorService.findDoctors(specialization, page, size);
        //then
        Assertions.assertAll(
                () -> assertEquals(2, result.totalElements()),
                () -> assertEquals(doctors.content(), result.content())
        );
        verify(provider, times(1)).findDoctors(specialization,page,size);
        verifyNoMoreInteractions(provider);
    }

    @Test
    void findDoctorVisits_DataCorrect_PageResponseWithVisitsReturned() {
        //given
        Long doctorId = 1L;
        Doctor doctor1 = new Doctor(1L, "test@doctor.pl", "chirurg", "jan", "brzechwa", null);
        int page = 0;
        int size = 5;
        Visit visit1 = new Visit(3L, doctor1, null, null, null, VisitStatus.AVAILABLE);
        Visit visit2 = new Visit(5L, doctor1, null, null, null, VisitStatus.AVAILABLE);
        PageResponse<Visit> visits = new PageResponse<>(List.of(visit1,visit2), 0, 2, 2, 2);
        when(provider.findVisits(null, doctorId, null, null, null, page, size)).thenReturn(visits);
        //when
        PageResponse<Visit> result = doctorService.findDoctorVisits(doctorId, page, size);
        //then
        Assertions.assertAll(
                () -> assertEquals(2, result.totalElements()),
                () -> assertEquals(visits.content(), result.content())
        );
        verify(provider, times(1)).findVisits(null, doctorId, null, null, null, page, size);
        verifyNoMoreInteractions(provider);
    }

    @Test
    void findAvailableVisitsByDoctorId_DataCorrect_PageResponseWithVisitsReturned() {
        //given
        Long doctorId = 1L;
        Doctor doctor1 = new Doctor(1L, "test@doctor.pl", "chirurg", "jan", "brzechwa", null);
        int page = 0;
        int size = 5;
        Visit visit1 = new Visit(3L, doctor1, null, null, null, VisitStatus.AVAILABLE);
        Visit visit2 = new Visit(5L, doctor1, null, null, null, VisitStatus.AVAILABLE);
        PageResponse<Visit> visits = new PageResponse<>(List.of(visit1,visit2), 0, 2, 2, 2);
        when(provider.findAvailableVisitsByDoctorId(doctorId,page,size)).thenReturn(visits);
        //when
        PageResponse<Visit> result = doctorService.findAvailableVisitsByDoctorId(doctorId, page, size);
        //then
        Assertions.assertAll(
                () -> assertEquals(2, result.totalElements()),
                () -> assertEquals(visits.content(), result.content())
        );
        verify(provider, times(1)).findAvailableVisitsByDoctorId(doctorId,page, size);
        verifyNoMoreInteractions(provider);
    }
}