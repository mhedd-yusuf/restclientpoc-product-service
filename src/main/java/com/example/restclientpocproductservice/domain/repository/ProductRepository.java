package com.example.restclientpocproductservice.domain.repository;

import com.example.restclientpocproductservice.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
