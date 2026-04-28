package com.barmalat.medicalclinic_proxy.model;

public record PatientDto(Long id, String email, String idCardNo, String firstName, String lastName, String phoneNumber, String birthday) {}