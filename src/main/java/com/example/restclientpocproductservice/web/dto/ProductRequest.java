package com.example.restclientpocproductservice.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank
    @Size(max = 50)
    private String name;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal price;
    @NotBlank
    private String currency;
    @NotBlank
    private String priceType;
}
