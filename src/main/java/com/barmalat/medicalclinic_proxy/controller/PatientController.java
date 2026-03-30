package com.barmalat.medicalclinic_proxy.controller;

import com.barmalat.medicalclinic_proxy.client.MedicalclinicClient;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "/patients", description = "all end points from PatientController")
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "find all patient visits",
            description = "opcjonalny request param paginacyjny")
    @GetMapping("/{patientId}/visits")
    public PageResponse<VisitDto> findPatientVisits(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Received GET /patients/{}/visits", patientId);
        PageResponse<VisitDto> result = patientService.findPatientVisits(patientId, page, size);
        log.info("Returned GET /patients/{}/visits with {} elements", patientId, result.totalElements());
        return result;
    }

    @Operation(summary = "add patient by patientId to visit by visitId")
    @PatchMapping("/{patientId}/visits/{visitId}")
    public VisitDto addPatientToVisit(
            @PathVariable Long patientId,
            @PathVariable Long visitId) {
        log.info("Received PATCH /patients/{}/visits/{}", patientId, visitId);
        VisitDto result = patientService.addPatientToVisit(visitId, patientId);
        log.info("Returned PATCH /patients/{}/visits/{} with status:{}", patientId, visitId, result.status());
        return result;
    }
}