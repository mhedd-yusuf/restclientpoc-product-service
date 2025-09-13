package com.example.restclientpocproductservice.external.pricing;

import com.example.restclientpocproductservice.external.pricing.dto.ProductPriceDto;

import java.util.List;

public interface PricingClient {
    List<ProductPriceDto> getPrices();
}
