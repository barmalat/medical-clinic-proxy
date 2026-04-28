package com.barmalat.config;

import com.barmalat.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class MedicalclinicClientFallbackFactory implements FallbackFactory<MedicalclinicClient> {
    @Override
    public MedicalclinicClient create(Throwable cause) {
        log.error("Fallback worked, cause: {}", cause.getMessage());
        return new MedicalclinicClient() {
            @Override
            public PageResponse<Doctor> findDoctors(String specialization, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public PageResponse<Visit> findVisits(Long patientId, Long doctorId, String specialization,
                                                     String from, String to, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public PageResponse<Visit> findAvailableVisits(String date, String specialization,
                                                              String from, String to, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public PageResponse<Visit> findAvailableVisitsByDoctorId(Long doctorId, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public Visit addPatientToVisit(Long visitId, Long patientId) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public Visit cancelVisit(Long visitId) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }
        };
    }
}