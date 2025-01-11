package com.ecommerce.order_server.repository;

import com.ecommerce.order_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
