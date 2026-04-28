package com.barmalat.medicalclinic_proxy.model;

import java.time.LocalDateTime;

public record VisitDto(Long id, DoctorDto doctor, PatientDto patient, LocalDateTime startTime, LocalDateTime endTime, VisitStatus status) {
}