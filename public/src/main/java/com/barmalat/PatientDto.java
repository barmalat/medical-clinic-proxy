package com.barmalat;

public record PatientDto(Long id, String email, String idCardNo, String firstName, String lastName, String phoneNumber, String birthday) {}