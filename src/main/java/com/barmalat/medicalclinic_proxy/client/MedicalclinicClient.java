package com.barmalat.medicalclinic_proxy.client;

import com.barmalat.medicalclinic_proxy.client.config.ClientConfiguration;
import com.barmalat.medicalclinic_proxy.model.DoctorDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "medicalclinicClient", configuration = ClientConfiguration.class)
public interface MedicalclinicClient {
    @GetMapping("/doctors")
    PageResponse<DoctorDto> findDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/visits")
    PageResponse<VisitDto> findVisits(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/visits/available")
    PageResponse<VisitDto> findAvailableVisits(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/visits/doctor/{doctorId}/available")
    PageResponse<VisitDto> findAvailableVisitsByDoctorId(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @PatchMapping("/visits/{visitId}/patient/{patientId}")
    VisitDto addPatientToVisit(
            @PathVariable Long visitId,
            @PathVariable Long patientId
    );

    @PatchMapping("/visits/{visitId}/cancel")
    VisitDto cancelVisit(@PathVariable Long visitId);
}