package com.example.restclientpocproductservice.service;

import com.example.restclientpocproductservice.common.PageableSanitizer;
import com.example.restclientpocproductservice.domain.repository.ProductRepository;
import com.example.restclientpocproductservice.external.pricing.PricingClient;
import com.example.restclientpocproductservice.external.pricing.dto.ProductPriceDto;
import com.example.restclientpocproductservice.mapper.ToProductResponseMapper;
import com.example.restclientpocproductservice.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    public static final int PAGE_NUM = 0;
    public static final int PAGE_SIZE = 30;
    private final ProductRepository productRepository;
    private final ToProductResponseMapper toProductResponseMapper;
    private final PricingClient pricingClient;
    private final PageableSanitizer pageableSanitizer;

    public List<ProductResponse> getProducts(Pageable pageable) {
        Pageable safePageable = pageableSanitizer.apply(pageable);
        // Call price api, to get prices
        Map<Long, ProductPriceDto> prices = pricingClient.getPrices(PAGE_NUM, PAGE_SIZE)
                .stream()
                .collect(Collectors.toMap(
                        ProductPriceDto::getId,
                        Function.identity(),
                        (a, b) -> b));
        // Get Product from the DB
        // Update Product with the price
        return productRepository
                .findAll(safePageable)
                .stream()
                .map(product -> toProductResponseMapper.apply(product, prices.get(product.getId())))
                .toList();
    }

    public ProductResponse getProduct(Long id) {
        // Get the product form the DB first
        return productRepository.findById(id)
                .map(product -> {
                    // Get product price
                    ProductPriceDto ProductPrice = pricingClient.getPrices(id);
                    return toProductResponseMapper.apply(product, ProductPrice);
                })
                .orElseThrow(() -> new NoSuchElementException("Product with Id: " + id + " Not found"));
    }
}
