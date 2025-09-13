package com.example.restclientpocproductservice.external.pricing.imp;

import com.example.restclientpocproductservice.external.pricing.PricingClient;
import com.example.restclientpocproductservice.external.pricing.dto.ProductPriceDto;
import com.example.restclientpocproductservice.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("restClient")
public class RestClientPricingClient implements PricingClient {

    public static final ParameterizedTypeReference<ApiResponse<List<ProductPriceDto>>> LIST_TYPE = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ApiResponse<ProductPriceDto>> ONE_TYPE = new ParameterizedTypeReference<>() {
    };
    private final @Qualifier("pricingRestClient") RestClient restClient;

    @Override
    public List<ProductPriceDto> getPrices(int page, int size) {
        return restClient
                .get()
                .uri(url ->
                        url.queryParam("page", page)
                                .queryParam("size", size)
                                .build())
                .retrieve()
                .body(LIST_TYPE)
                .getData();
    }

    @Override
    public ProductPriceDto getPrices(Long id) {
        return restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment(String.valueOf(id))
                                .build())
                .retrieve()
                .body(ONE_TYPE)
                .getData();
    }
}
