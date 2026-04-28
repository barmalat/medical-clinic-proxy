package com.barmalat.medicalclinic_proxy.service;

import com.barmalat.medicalclinic_proxy.client.MedicalclinicClient;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {
    private final MedicalclinicClient client;

    public PageResponse<VisitDto> findPatientVisits(Long patientId, int page, int size) {
        log.info("process of finding patient visits by patientId:{} started", patientId);
        PageResponse<VisitDto> result = client.findVisits(patientId, null, null, null, null, page, size);
        log.info("process of finding patient visits by patientId:{} finished", patientId);
        return result;
    }

    public VisitDto addPatientToVisit(Long visitId, Long patientId) {
        log.info("process of booking visit by patient with visitId:{} and patientId:{} started", visitId, patientId);
        VisitDto result = client.addPatientToVisit(visitId, patientId);
        log.info("process of booking visit by patient with visitId:{} and patientId:{} finished", visitId, patientId);
        return result;
    }
}