package com.diegomuu.karting.tarifasFestivos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TarifasFestivosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarifasFestivosApplication.class, args);
	}

}
