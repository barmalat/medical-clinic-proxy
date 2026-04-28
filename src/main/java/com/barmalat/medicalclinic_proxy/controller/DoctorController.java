package com.barmalat.medicalclinic_proxy.controller;

import com.barmalat.medicalclinic_proxy.exception.ErrorMessageDto;
import com.barmalat.medicalclinic_proxy.model.DoctorDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            description = "Optional request params for filter by specialization and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))})})
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
            description = "Optional request param for pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor visits found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))})})
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
            description = "Optional request param for pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available visits found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))})})
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