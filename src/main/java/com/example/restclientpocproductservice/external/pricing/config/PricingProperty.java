package com.example.restclientpocproductservice.external.pricing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "client.pricing")
@Data
public class PricingProperty {
    private String baseUrl;
    private Duration connectTimeOut = Duration.ofSeconds(2);
    private Duration readTimeOut = Duration.ofSeconds(5);
}
