package com.example.restclientpocproductservice.service;

import com.example.restclientpocproductservice.common.PageableSanitizer;
import com.example.restclientpocproductservice.domain.repository.ProductRepository;
import com.example.restclientpocproductservice.exception.ProductNotFoundException;
import com.example.restclientpocproductservice.external.pricing.PricingClient;
import com.example.restclientpocproductservice.mapper.ToProductResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

    @InjectMocks
    ProductQueryService productQueryService;

    @Mock
    ProductRepository productRepository;
    @Mock
    ToProductResponseMapper toProductResponseMapper;
    @Mock
    PricingClient pricingClient;
    @Mock
    PageableSanitizer pageableSanitizer;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findProduct_whenNotFound_throwsProductNotFoundException() {
        // given
        long id = 42L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThrows(ProductNotFoundException.class, () -> productQueryService.findProduct(id));

        // and: no downstream calls should have happened
        verify(productRepository).findById(id);
        verifyNoInteractions(pricingClient, toProductResponseMapper);
    }

}