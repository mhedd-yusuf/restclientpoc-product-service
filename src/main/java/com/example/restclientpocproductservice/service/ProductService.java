package com.example.restclientpocproductservice.service;

import com.example.restclientpocproductservice.api.response.ProductResponse;
import com.example.restclientpocproductservice.mapper.ToProductResponseMapper;
import com.example.restclientpocproductservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ToProductResponseMapper toProductResponseMapper;

    public List<ProductResponse> getProducts() {
        // Call price api, to get prices
        // Get Product from the DB
        // Update Product with the price
        return productRepository
                .findAll()
                .stream()
                .map(toProductResponseMapper)
                .collect(Collectors.toList());
    }
}
