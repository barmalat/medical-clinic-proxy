package com.barmalat;

import java.time.LocalDateTime;

public record Visit(Long id, Doctor doctor, Patient patient, LocalDateTime startTime, LocalDateTime endTime, VisitStatus status) {
}