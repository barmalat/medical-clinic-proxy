package com.barmalat;

public interface MedicalclinicProvider {

    PageResponse<Doctor> findDoctors(String specialization, int page, int size);

    PageResponse<Visit> findVisits(Long patientId, Long doctorId, String specialization, String from, String to, int page, int size);

    PageResponse<Visit> findAvailableVisitsByDoctorId(Long doctorId, int page, int size);

    Visit addPatientToVisit(Long visitId, Long patientId);

    PageResponse<Visit> findAvailableVisits(String date, String specialization, String from, String to, int page, int size);

    Visit cancelVisit(Long visitId);
}