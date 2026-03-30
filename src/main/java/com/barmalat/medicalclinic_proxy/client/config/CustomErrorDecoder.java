package com.barmalat.medicalclinic_proxy.client.config;

import com.barmalat.medicalclinic_proxy.exception.MedicalclinnicException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        if (exception.status() == 503) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    50L,
                    response.request());
        }
        if (exception.status() == 500) {
            return new MedicalclinnicException(exception.getMessage(), exception.status());
        }
        return exception;
    }
}