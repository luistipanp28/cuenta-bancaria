package com.ec.pichincha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ec.pichincha"})
public class CuentaBancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuentaBancariaApplication.class, args);
	}

}
