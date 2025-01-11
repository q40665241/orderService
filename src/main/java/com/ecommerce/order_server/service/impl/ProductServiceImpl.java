package com.ecommerce.order_server.service.impl;

import com.ecommerce.order_server.dto.ProductDto;
import com.ecommerce.order_server.entity.Product;
import com.ecommerce.order_server.exception.ResourceNotFoundException;
import com.ecommerce.order_server.mapper.ProductMapper;
import com.ecommerce.order_server.repository.ProductRepository;
import com.ecommerce.order_server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.toProduct(productDto);
        productRepository.save(ProductMapper.toProduct(productDto));
        return ProductMapper.toProductDto(product);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
        .orElseThrow(()->new ResourceNotFoundException("Product is not exists with given id: "+productId));
  
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setQuantity(productDto.getQuantity());
            Product updatedProduct = productRepository.save(product);
            return ProductMapper.toProductDto(updatedProduct);
     
      
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
        .orElseThrow(()->new ResourceNotFoundException("Product is not exists with given id: "+productId));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProductById(Long productId) {
     Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product is not exists with given id: "+productId));
        return ProductMapper.toProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map((product)->ProductMapper.toProductDto(product))
                                .collect(Collectors.toList());
    }
}
