package com.example.restclientpocproductservice.web;

import com.example.restclientpocproductservice.web.dto.ProductResponse;
import com.example.restclientpocproductservice.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(@PageableDefault(
            page = 0,
            size = 30,
            direction = Sort.Direction.ASC)
                                                                          Pageable pageable) {
        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponse>>builder()
                        .data(productQueryService.findProducts(pageable))
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<ProductResponse>builder()
                        .data(productQueryService.findProduct(id))
                        .build()
        );
    }
}
