package com.barmalat.config;

import com.barmalat.DoctorService;
import com.barmalat.MedicalclinicProviderImpl;
import com.barmalat.PatientService;
import com.barmalat.VisitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    DoctorService doctorService(MedicalclinicProviderImpl provider) {
        return new DoctorService(provider);
    }
    @Bean
    VisitService visitService(MedicalclinicProviderImpl provider) {
        return new VisitService(provider);
    }
    @Bean
    PatientService patientService(MedicalclinicProviderImpl provider) {
        return new PatientService(provider);
    }
}