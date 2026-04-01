package com.barmalat.medicalclinic_proxy.controller;

import com.barmalat.medicalclinic_proxy.exception.ErrorMessageDto;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.service.VisitService;
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
@RequestMapping("/visits")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "/visits", description = "all end points from VisitController")
public class VisitController {
    private final VisitService visitService;

    @Operation(summary = "find visits by specialization and time range", description = """
            mandatory request param with specialization and time range, ex. /visits?specialization=chirurg&from=2026-04-01T00:00&to=2026-04-30T23:59
            
            Optional request param for pagination, ex. /visits?page=0&size=3
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visits found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))})})
    @GetMapping
    public PageResponse<VisitDto> findVisits(
            @RequestParam String specialization,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Received GET /visits specialization:{} from:{} to:{}", specialization, from, to);
        PageResponse<VisitDto> result = visitService.findVisits(specialization, from, to, page, size);
        log.info("Returned GET /visits with {} elements", result.totalElements());
        return result;
    }

    @Operation(summary = "find available visits", description = """
            mandatory one of the three schemes of request param:
            
            1. with date and speciality, ex. /visits/available?date=2026-04-01&specialization=chirurg
            
            2. with time range and specialization, ex. /visits/available?specialization=chirurg&from=2026-04-01T00:00&to=2026-04-30T23:59
            
            3. with time range, ex. /visits/available?from=2026-04-01T00:00&to=2026-04-30T23:59
            
            Optional request param for pagination, ex. /visits?page=0&size=3
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available visits found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))})})
    @GetMapping("/available")
    public PageResponse<VisitDto> findAvailableVisits(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Received GET /visits/available date:{} specialization:{} from:{} to:{}", date, specialization, from, to);
        PageResponse<VisitDto> result = visitService.findAvailableVisits(date, specialization, from, to, page, size);
        log.info("Returned GET /visits/available with {} elements", result.totalElements());
        return result;
    }

    @Operation(summary = "cancel visit by visitId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit cancelled",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDto.class))}),
            @ApiResponse(responseCode = "404", description = "Visit not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "409", description = "Visit is already cancelled",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDto.class))})})
    @PatchMapping("/{visitId}/cancel")
    public VisitDto cancelVisit(@PathVariable Long visitId) {
        log.info("Received PATCH /visits/{}/cancel", visitId);
        VisitDto result = visitService.cancelVisit(visitId);
        log.info("Returned PATCH /visits/{}/cancel with status:{}", visitId, result.status());
        return result;
    }
}