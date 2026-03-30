package com.barmalat.medicalclinic_proxy.model;

import java.util.List;

public record DoctorDto(Long id, String email, String specialization, String firstName, String lastName, List<FacilityDto> facilities) {
}