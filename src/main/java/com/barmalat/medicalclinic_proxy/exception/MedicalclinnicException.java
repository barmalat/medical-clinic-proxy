package com.barmalat.medicalclinic_proxy.exception;

import lombok.Getter;

@Getter
public class MedicalclinnicException extends RuntimeException {
    private final int status;

    public MedicalclinnicException(String message, int status) {
        super(message);
        this.status = status;
    }
}