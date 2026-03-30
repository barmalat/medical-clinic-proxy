package com.barmalat.medicalclinic_proxy.controller;

import com.barmalat.medicalclinic_proxy.model.DoctorDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "/doctors", description = "all end points from DoctorController")
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "find all doctors",
            description = "opcjonalny request param filtrujacy po specjalizacji oraz paginacyjny")
    @GetMapping
    public PageResponse<DoctorDto> findDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Received GET /doctors specialization:{}", specialization);
        PageResponse<DoctorDto> result = doctorService.findDoctors(specialization, page, size);
        log.info("Returned GET /doctors with {} elements", result.totalElements());
        return result;
    }

    @Operation(summary = "find all doctor visits",
            description = "opcjonalny request param paginacyjny")
    @GetMapping("/{doctorId}/visits")
    public PageResponse<VisitDto> findDoctorVisits(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Received GET /doctors/{}/visits", doctorId);
        PageResponse<VisitDto> result = doctorService.findDoctorVisits(doctorId, page, size);
        log.info("Returned GET /doctors/{}/visits with {} elements", doctorId, result.totalElements());
        return result;
    }

    @Operation(summary = "find all doctor available visits",
            description = "opcjonalny request param paginacyjny")
    @GetMapping("/{doctorId}/visit/available")
    public PageResponse<VisitDto> findAvailableVisitsByDoctorId(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Received GET /doctors/{}/visit/available", doctorId);
        PageResponse<VisitDto> result = doctorService.findAvailableVisitsByDoctorId(doctorId, page, size);
        log.info("Returned GET /doctors/{}/visit/available with {} elements", doctorId, result.totalElements());
        return result;
    }
}