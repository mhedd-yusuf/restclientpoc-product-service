package com.example.restclientpocproductservice.external.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceDto {
    private Long id;

    private BigDecimal amount;

    private String currency;

    private String priceType;
}