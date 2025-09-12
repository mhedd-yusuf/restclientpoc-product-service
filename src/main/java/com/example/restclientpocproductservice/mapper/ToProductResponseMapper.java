package com.example.restclientpocproductservice.mapper;

import com.example.restclientpocproductservice.api.response.ProductResponse;
import com.example.restclientpocproductservice.domain.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ToProductResponseMapper implements Function<Product, ProductResponse> {

    @Override
    public ProductResponse apply(final Product product) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }
}
