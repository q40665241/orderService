package com.ecommerce.order_server.mapper;

import com.ecommerce.order_server.dto.ProductDto;
import com.ecommerce.order_server.entity.Product;

public class ProductMapper {
    // Convert Product entity to ProductDto
    public static ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }

    // Convert ProductDto to Product entity
    public static Product toProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getQuantity()
        );
    }
}
