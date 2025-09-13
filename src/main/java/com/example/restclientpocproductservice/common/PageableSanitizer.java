package com.example.restclientpocproductservice.common;

import com.example.restclientpocproductservice.config.PaginationProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.function.UnaryOperator;

@Component
@RequiredArgsConstructor
public class PageableSanitizer implements UnaryOperator<Pageable> {
    private final PaginationProperty paginationProperty;


    @Override
    public Pageable apply(Pageable pageable) {
        int page = Math.max(0, pageable.getPageNumber());
        int requested = pageable.getPageSize();
        int size = (requested <= 0 || requested > paginationProperty.getMaxSize())
                ? paginationProperty.getDefaultSize()
                : requested;
        return PageRequest.of(page, size, pageable.getSort());
    }
}
