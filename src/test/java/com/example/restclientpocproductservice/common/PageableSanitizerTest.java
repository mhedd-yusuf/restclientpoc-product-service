package com.example.restclientpocproductservice.common;

import com.example.restclientpocproductservice.config.PaginationProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageableSanitizerTest {

    @InjectMocks
    PageableSanitizer pageableSanitizer;

    @Mock
    PaginationProperty paginationProperty;

    @BeforeEach
    void setUp() {
    }

    @Test
    void validInput_keepsPageSizeAndSort() {
        when(paginationProperty.getMaxSize()).thenReturn(70);

        Pageable input = PageRequest.of(2, 50, Sort.by(Sort.Order.asc("id")));
        Pageable out = pageableSanitizer.apply(input);

        assertEquals(2, out.getPageNumber());
        assertEquals(50, out.getPageSize());
        assertEquals(Sort.by("id").ascending(), out.getSort());
    }
}