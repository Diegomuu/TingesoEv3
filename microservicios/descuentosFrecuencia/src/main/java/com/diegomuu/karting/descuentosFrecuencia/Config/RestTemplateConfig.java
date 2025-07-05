package com.diegomuu.karting.descuentosFrecuencia.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced  // Necesario para que Eureka gestione la comunicación
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

