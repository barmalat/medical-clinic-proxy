package com.barmalat;

import com.barmalat.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "medicalclinicClient", configuration = ClientConfiguration.class)
public interface MedicalclinicClient {
    @GetMapping("/doctors")
    PageResponse<Doctor> findDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/visits")
    PageResponse<Visit> findVisits(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/visits/available")
    PageResponse<Visit> findAvailableVisits(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/visits/doctor/{doctorId}/available")
    PageResponse<Visit> findAvailableVisitsByDoctorId(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @PatchMapping("/visits/{visitId}/patient/{patientId}")
    Visit addPatientToVisit(
            @PathVariable Long visitId,
            @PathVariable Long patientId
    );

    @PatchMapping("/visits/{visitId}/cancel")
    Visit cancelVisit(@PathVariable Long visitId);
}