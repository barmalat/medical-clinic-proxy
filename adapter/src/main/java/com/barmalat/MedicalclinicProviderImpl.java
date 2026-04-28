package com.barmalat;

import org.springframework.stereotype.Component;

@Component
public class MedicalclinicProviderImpl implements MedicalclinicProvider {
    private final MedicalclinicClient client;

    public MedicalclinicProviderImpl(MedicalclinicClient client) {
        this.client = client;
    }

    @Override
    public PageResponse<Doctor> findDoctors(String specialization, int page, int size) {
        return client.findDoctors(specialization, page, size);
    }

    @Override
    public PageResponse<Visit> findVisits(Long patientId, Long doctorId, String specialization, String from, String to, int page, int size) {
        return client.findVisits(patientId, doctorId, specialization, from, to, page, size);
    }

    @Override
    public PageResponse<Visit> findAvailableVisitsByDoctorId(Long doctorId, int page, int size) {
        return client.findAvailableVisitsByDoctorId(doctorId, page, size);
    }

    @Override
    public Visit addPatientToVisit(Long visitId, Long patientId) {
        return client.addPatientToVisit(visitId, patientId);
    }

    @Override
    public PageResponse<Visit> findAvailableVisits(String date, String specialization, String from, String to, int page, int size) {
        return client.findAvailableVisits(date, specialization, from, to, page, size);
    }

    @Override
    public Visit cancelVisit(Long visitId) {
        return client.cancelVisit(visitId);
    }
}