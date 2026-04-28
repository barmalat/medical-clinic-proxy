package com.barmalat.medicalclinic_proxy.service;

import com.barmalat.medicalclinic_proxy.client.MedicalclinicClient;
import com.barmalat.medicalclinic_proxy.model.PageResponse;
import com.barmalat.medicalclinic_proxy.model.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitService {
    private final MedicalclinicClient client;

    public PageResponse<VisitDto> findVisits(String specialization, String from, String to, int page, int size) {
        log.info("process of finding visits with specialization:{} from:{} to:{} started", specialization, from, to);
        PageResponse<VisitDto> result = client.findVisits(null, null, specialization, from, to, page, size);
        log.info("process of finding visits with specialization:{} from:{} to:{} finished", specialization, from, to);
        return result;
    }

    public PageResponse<VisitDto> findAvailableVisits(String date, String specialization, String from, String to, int page, int size) {
        log.info("process of finding available visits with date:{} specialization:{} from:{}, to:{} started", date, specialization, from, to);
        PageResponse<VisitDto> result = client.findAvailableVisits(date, specialization, from, to, page, size);
        log.info("process of finding available visits with date:{} specialization:{} from:{}, to:{} finished", date, specialization, from, to);
        return result;
    }

    public VisitDto cancelVisit(Long visitId) {
        log.info("process of canceling visit by visitId:{} started", visitId);
        VisitDto result = client.cancelVisit(visitId);
        log.info("process of canceling visit by visitId:{} finished", visitId);
        return result;
    }
}