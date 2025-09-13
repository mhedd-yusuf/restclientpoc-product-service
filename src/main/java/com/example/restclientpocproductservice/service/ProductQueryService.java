package com.example.restclientpocproductservice.service;

import com.example.restclientpocproductservice.web.dto.ProductResponse;
import com.example.restclientpocproductservice.external.pricing.PricingClient;
import com.example.restclientpocproductservice.external.pricing.dto.ProductPriceDto;
import com.example.restclientpocproductservice.mapper.ToProductResponseMapper;
import com.example.restclientpocproductservice.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final ToProductResponseMapper toProductResponseMapper;
    private final PricingClient pricingClient;

    public List<ProductResponse> getProducts() {
        // Call price api, to get prices
        Map<Long, ProductPriceDto> prices = pricingClient.getPrices()
                .stream()
                .collect(Collectors.toMap(
                        ProductPriceDto::getId,
                        Function.identity(),
                        (a, b) -> b));
        // Get Product from the DB
        // Update Product with the price
        return productRepository
                .findAll()
                .stream()
                .map(product -> toProductResponseMapper.apply(product, prices.get(product.getId())))
                .toList();
    }
}
