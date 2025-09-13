package com.example.restclientpocproductservice.external.pricing;

import com.example.restclientpocproductservice.external.pricing.dto.ProductPriceDto;

import java.awt.print.Pageable;
import java.util.List;

public interface PricingClient {
    List<ProductPriceDto> getPrices(int page, int size);

    ProductPriceDto getPrices(Long id);
}
