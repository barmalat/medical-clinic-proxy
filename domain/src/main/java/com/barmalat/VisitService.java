package com.barmalat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class VisitService {
    private final MedicalclinicProvider clientProvider;

    public PageResponse<Visit> findVisits(String specialization, String from, String to, int page, int size) {
        log.info("process of finding visits with specialization:{} from:{} to:{} started", specialization, from, to);
        PageResponse<Visit> result = clientProvider.findVisits(null, null, specialization, from, to, page, size);
        log.info("process of finding visits with specialization:{} from:{} to:{} finished", specialization, from, to);
        return result;
    }

    public PageResponse<Visit> findAvailableVisits(String date, String specialization, String from, String to, int page, int size) {
        log.info("process of finding available visits with date:{} specialization:{} from:{}, to:{} started", date, specialization, from, to);
        PageResponse<Visit> result = clientProvider.findAvailableVisits(date, specialization, from, to, page, size);
        log.info("process of finding available visits with date:{} specialization:{} from:{}, to:{} finished", date, specialization, from, to);
        return result;
    }

    public Visit cancelVisit(Long visitId) {
        log.info("process of canceling visit by visitId:{} started", visitId);
        Visit result = clientProvider.cancelVisit(visitId);
        log.info("process of canceling visit by visitId:{} finished", visitId);
        return result;
    }
}