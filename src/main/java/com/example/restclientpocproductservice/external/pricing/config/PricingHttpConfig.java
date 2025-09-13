package com.example.restclientpocproductservice.external.pricing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

@Configuration
@RequiredArgsConstructor
public class PricingHttpConfig {

    private final PricingProperty pricingProperty;

    @Bean
    RestClient pricingRestClient() {
        final HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(pricingProperty.getConnectTimeOut())
                .build();
        JdkClientHttpRequestFactory jdkClientHttpRequestFactory = new JdkClientHttpRequestFactory(httpClient);
        jdkClientHttpRequestFactory.setReadTimeout(pricingProperty.getReadTimeOut());

        return RestClient.builder()
                .baseUrl(pricingProperty.getBaseUrl())
                .requestFactory(jdkClientHttpRequestFactory)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
