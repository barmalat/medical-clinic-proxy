package com.barmalat;

public record Patient(Long id, String email, String idCardNo, String firstName, String lastName, String phoneNumber, String birthday) {}