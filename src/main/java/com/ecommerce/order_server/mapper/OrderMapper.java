package com.ecommerce.order_server.mapper;

import com.ecommerce.order_server.dto.OrderDto;
import com.ecommerce.order_server.entity.Order;
import com.ecommerce.order_server.entity.Product;
import com.ecommerce.order_server.entity.User;
import com.ecommerce.order_server.dto.ProductDto;
import com.ecommerce.order_server.dto.UserDto;
import com.ecommerce.order_server.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    
    // Convert Order entity to OrderDto
    public static OrderDto toOrderDto(Order order) {
        List<ProductDto> productDtos = order.getProducts().stream()
                .map(ProductMapper::toProductDto)
                .collect(Collectors.toList());

                UserDto userDto = UserMapper.toUserDto(order.getUser());

        return new OrderDto(
                order.getId(),
                productDtos,
                order.getTotalPrice(),
                userDto
        );
    }

    // Convert OrderDto to Order entity
    public static Order toOrder(OrderDto orderDto) {
        List<Product> products = orderDto.getProducts().stream()
                .map(ProductMapper::toProduct)
                .collect(Collectors.toList());

                User user = UserMapper.toUser(orderDto.getUser());

                return new Order(
                    orderDto.getId(),
                    products,
                    orderDto.getTotalPrice(),
                    user
            );
    }
}
