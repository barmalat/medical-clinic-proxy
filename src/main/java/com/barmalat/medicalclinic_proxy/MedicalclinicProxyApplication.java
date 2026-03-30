package com.barmalat.medicalclinic_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicalclinicProxyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MedicalclinicProxyApplication.class, args);
	}
}