package com.ecommerce.order_server.service;

import com.ecommerce.order_server.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder( Long userId);

    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto addProductToOrder(Long productId,Long orderId, Long userId);

    OrderDto removeProductFromOrder(Long orderId, Long productId);
         

}
