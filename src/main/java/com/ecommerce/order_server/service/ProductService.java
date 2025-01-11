package com.ecommerce.order_server.service;

import com.ecommerce.order_server.dto.ProductDto;
import com.ecommerce.order_server.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    void deleteProduct(Long productId);

    ProductDto getProductById(Long productId);

    List<ProductDto> getAllProducts();
}
