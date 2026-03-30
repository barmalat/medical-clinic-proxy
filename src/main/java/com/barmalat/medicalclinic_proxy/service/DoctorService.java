package com.barmalat.medicalclinic_proxy.service;

import com.barmalat.medicalclinic_proxy.client.MedicalclinicClient;
import com.barmalat.medicalclinic_proxy.model.DoctorDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    private final MedicalclinicClient client;

    public PageResponse<DoctorDto> findDoctors(String specialization, int page, int size) {
        log.info("process of finding doctors by specialization:{} started", specialization);
        PageResponse<DoctorDto> result = client.findDoctors(specialization, page, size);
        log.info("process of finding doctors by specialization:{} finished", specialization);
        return result;
    }

    public PageResponse<VisitDto> findDoctorVisits(Long doctorId, int page, int size) {
        log.info("process of finding doctor visits by doctorId:{} started", doctorId);
        PageResponse<VisitDto> result = client.findVisits(null, doctorId, null, null, null, page, size);
        log.info("process of finding doctor visits by doctorId:{} finished", doctorId);
        return result;
    }

    public PageResponse<VisitDto> findAvailableVisitsByDoctorId(Long doctorId, int page, int size) {
        log.info("process of finding doctor available visits by doctorId:{} finished", doctorId);
        PageResponse<VisitDto> result = client.findAvailableVisitsByDoctorId(doctorId, page, size);
        log.info("process of finding doctor available visits by doctorId:{} started", doctorId);
        return result;
    }
}