package com.ecommerce.order_server.repository;

import com.ecommerce.order_server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
