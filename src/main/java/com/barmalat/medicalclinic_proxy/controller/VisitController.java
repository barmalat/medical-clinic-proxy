package com.barmalat.medicalclinic_proxy.controller;

import com.barmalat.medicalclinic_proxy.client.MedicalclinicClient;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import com.barmalat.medicalclinic_proxy.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
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
        konieczny request param z przedzialem czasu i specjalizacja, np: /visits?specialization=chirurg&from=2026-04-01T00:00&to=2026-04-30T23:59
        
        Opcjonalny Request Param dot. paginacji, np. /visits?page=0&size=3
        """)
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
            W zależności od oczekiwań, wymagane zachowanie jednego z trzech schematów request param:
            
            1. jeśli chcemy wyswietlić dostępne wizyty dla danej specjalizacji w konkretnym dniu: /visits/available?date=2026-04-01&specialization=chirurg
            
            2. jeśli chcemy wyświetlić dostępne wizyty dla danej specjalizacji w danym przedziale czasu: /visits/available?specialization=chirurg&from=2026-04-01T00:00&to=2026-04-30T23:59
            
            3. jeśli chcemy wyświetlić dostępne wizyty bez danej specjalizacji w danym przedziale czasu: /visits/available?from=2026-04-01T00:00&to=2026-04-30T23:59
            
            Opcjonalny Request Param dot. paginacji, np. /visits/available?page=0&size=3
            """)
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

    @Operation(summary = "Odwołaj wizytę przez doktora (P-002)",
            description = "Ustawia status wizyty na CANCELLED. Pacjent pozostaje przypisany w historii.")
    @PatchMapping("/{visitId}/cancel")
    public VisitDto cancelVisit(@PathVariable Long visitId) {
        log.info("Received PATCH /visits/{}/cancel", visitId);
        VisitDto result = visitService.cancelVisit(visitId);
        log.info("Returned PATCH /visits/{}/cancel with status:{}", visitId, result.status());
        return result;
    }
}