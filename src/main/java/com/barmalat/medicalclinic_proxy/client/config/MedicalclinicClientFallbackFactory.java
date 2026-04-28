package com.barmalat.medicalclinic_proxy.client.config;

import com.barmalat.medicalclinic_proxy.client.MedicalclinicClient;
import com.barmalat.medicalclinic_proxy.exception.MedicalclinnicException;
import com.barmalat.medicalclinic_proxy.model.DoctorDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class MedicalclinicClientFallbackFactory implements FallbackFactory<MedicalclinicClient> {
    @Override
    public MedicalclinicClient create(Throwable cause) {
        log.error("Fallback worked, cause: {}", cause.getMessage());
        return new MedicalclinicClient() {
            @Override
            public PageResponse<DoctorDto> findDoctors(String specialization, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public PageResponse<VisitDto> findVisits(Long patientId, Long doctorId, String specialization,
                                                     String from, String to, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public PageResponse<VisitDto> findAvailableVisits(String date, String specialization,
                                                              String from, String to, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public PageResponse<VisitDto> findAvailableVisitsByDoctorId(Long doctorId, int page, int size) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public VisitDto addPatientToVisit(Long visitId, Long patientId) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }

            @Override
            public VisitDto cancelVisit(Long visitId) {
                throw new MedicalclinnicException("Serwis tymczasowo niedostępny. Spróbuj ponownie później.", 503);
            }
        };
    }
}