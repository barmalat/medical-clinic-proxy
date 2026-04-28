package com.barmalat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PatientService {
    private final MedicalclinicProvider clientProvider;

    public PageResponse<Visit> findPatientVisits(Long patientId, int page, int size) {
        log.info("process of finding patient visits by patientId:{} started", patientId);
        PageResponse<Visit> result = clientProvider.findVisits(patientId, null, null, null, null, page, size);
        log.info("process of finding patient visits by patientId:{} finished", patientId);
        return result;
    }

    public Visit addPatientToVisit(Long visitId, Long patientId) {
        log.info("process of booking visit by patient with visitId:{} and patientId:{} started", visitId, patientId);
        Visit result = clientProvider.addPatientToVisit(visitId, patientId);
        log.info("process of booking visit by patient with visitId:{} and patientId:{} finished", visitId, patientId);
        return result;
    }
}