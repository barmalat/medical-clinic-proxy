package com.barmalat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    private final MedicalclinicProvider provider;

    public PageResponse<Doctor> findDoctors(String specialization, int page, int size) {
        log.info("process of finding doctors by specialization:{} started", specialization);
        PageResponse<Doctor> result = provider.findDoctors(specialization, page, size);
        log.info("process of finding doctors by specialization:{} finished", specialization);
        return result;
    }

    public PageResponse<Visit> findDoctorVisits(Long doctorId, int page, int size) {
        log.info("process of finding doctor visits by doctorId:{} started", doctorId);
        PageResponse<Visit> result = provider.findVisits(null, doctorId, null, null, null, page, size);
        log.info("process of finding doctor visits by doctorId:{} finished", doctorId);
        return result;
    }

    public PageResponse<Visit> findAvailableVisitsByDoctorId(Long doctorId, int page, int size) {
        log.info("process of finding doctor available visits by doctorId:{} finished", doctorId);
        PageResponse<Visit> result = provider.findAvailableVisitsByDoctorId(doctorId, page, size);
        log.info("process of finding doctor available visits by doctorId:{} started", doctorId);
        return result;
    }
}