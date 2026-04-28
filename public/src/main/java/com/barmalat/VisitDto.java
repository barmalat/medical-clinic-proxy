package com.barmalat;

import java.time.LocalDateTime;

public record VisitDto(Long id, DoctorDto doctor, PatientDto patient, LocalDateTime startTime, LocalDateTime endTime, VisitStatus status) {
}