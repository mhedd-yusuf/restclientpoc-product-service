package com.example.restclientpocproductservice.mapper;

import com.example.restclientpocproductservice.web.dto.ProductResponse;
import com.example.restclientpocproductservice.external.pricing.dto.ProductPriceDto;
import com.example.restclientpocproductservice.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ToProductResponseMapper implements BiFunction<Product, ProductPriceDto, ProductResponse> {

    @Override
    public ProductResponse apply(final Product product, final ProductPriceDto productPriceDto) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(productPriceDto.getAmount())
                .currency(productPriceDto.getCurrency())
                .priceType(productPriceDto.getPriceType())
                .build();
    }
}
