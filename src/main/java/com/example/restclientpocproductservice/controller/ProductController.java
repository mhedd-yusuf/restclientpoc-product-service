package com.example.restclientpocproductservice.controller;

import com.example.restclientpocproductservice.api.response.ProductResponse;
import com.example.restclientpocproductservice.common.api.ApiResponse;
import com.example.restclientpocproductservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {
        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponse>>builder()
                        .data(productService.getProducts())
                        .build()
        );
    }
}
