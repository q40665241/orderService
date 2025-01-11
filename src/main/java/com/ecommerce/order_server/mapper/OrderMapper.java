package com.ecommerce.order_server.mapper;

import com.ecommerce.order_server.dto.OrderDto;
import com.ecommerce.order_server.entity.Order;
import com.ecommerce.order_server.entity.Product;
import com.ecommerce.order_server.dto.ProductDto;
import com.ecommerce.order_server.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    
    // Convert Order entity to OrderDto
    public static OrderDto toOrderDto(Order order) {
        List<ProductDto> productDtos = order.getProducts().stream()
                .map(ProductMapper::toProductDto)
                .collect(Collectors.toList());
        
        return new OrderDto(
                order.getId(),
                productDtos,
                order.getTotalPrice()
        );
    }

    // Convert OrderDto to Order entity
    public static Order toOrder(OrderDto orderDto) {
        List<Product> products = orderDto.getProducts().stream()
                .map(ProductMapper::toProduct)
                .collect(Collectors.toList());

                return new Order(
                    orderDto.getId(),
                    products,
                    orderDto.getTotalPrice()
            );
    }
}
