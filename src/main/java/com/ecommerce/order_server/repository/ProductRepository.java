package com.ecommerce.order_server.repository;

import com.ecommerce.order_server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
