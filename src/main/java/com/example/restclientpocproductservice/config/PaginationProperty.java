package com.example.restclientpocproductservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@ConfigurationProperties(prefix = "pagination")
@Data
public class PaginationProperty {
    private int defaultSize = 50;
    private int maxSize = 100;
    private boolean clampToLast = false; // when requested page >= totalPages
    private long maxOffset = 0;          // 0 = disabled
    private List<String> allowedSort = List.of("id");
}
