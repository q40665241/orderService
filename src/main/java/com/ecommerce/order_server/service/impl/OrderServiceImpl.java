package com.ecommerce.order_server.service.impl;

import com.ecommerce.order_server.dto.OrderDto;
import com.ecommerce.order_server.entity.Order;
import com.ecommerce.order_server.entity.Product;
import com.ecommerce.order_server.entity.User;
import com.ecommerce.order_server.exception.InsufficientStockException;
import com.ecommerce.order_server.exception.ResourceNotFoundException;
import com.ecommerce.order_server.mapper.OrderMapper;
import com.ecommerce.order_server.mapper.ProductMapper;
import com.ecommerce.order_server.repository.OrderRepository;
import com.ecommerce.order_server.repository.ProductRepository;
import com.ecommerce.order_server.repository.UserRepository;
import com.ecommerce.order_server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public OrderDto createOrder(OrderDto orderDto, Long userId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Order order = OrderMapper.toOrder(orderDto);
        order.setUser(user);
        orderRepository.save(order);
        return OrderMapper.toOrderDto(order);
    }
    
    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist with given id: " + orderId));

        order.setTotalPrice(orderDto.getTotalPrice());

        List<Product> products = orderDto.getProducts().stream()
        .map(productDto -> ProductMapper.toProduct(productDto))  // Assuming you have a ProductMapper to convert ProductDto to Product
        .collect(Collectors.toList());

        order.setProducts(products);

        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist with given id: " + orderId));
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist with given id: " + orderId));
        return OrderMapper.toOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                    .map(OrderMapper::toOrderDto)
                    .collect(Collectors.toList());
    }

    @Override
    public OrderDto addProductToOrder(Long productId,Long orderId,Long userId) {
        Product product = productRepository.findById(productId)
        .orElseThrow(()->new ResourceNotFoundException("Product is not exists with given id: "+productId));
      
        if(product.getQuantity()<=0)
        {
            throw new InsufficientStockException("Not enough stock for product: " + product.getName());
        }
        Order order = orderRepository.findById(orderId).orElse(null);
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        // If order does not exist, create a new one

        if (order == null) {
             
            order = new Order();
            order.setUser(user);
            order.setTotalPrice(0.0); // Initialize total price
            

        }
        else{
            if (!order.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("Order does not belong to the specified user");
            }
        }
        if (order.getProducts() == null) {
        order.setProducts(new ArrayList<>());
     }

        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);
        order.getProducts().add(product);
        
        // Recalculate the total price
        double totalPrice = calculateTotalPrice(order.getProducts());

        // Update the order's total price
        order.setTotalPrice(totalPrice);
        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(updatedOrder);
        
    }
    private double calculateTotalPrice(List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();  // Assumes quantity in order
        }
        return totalPrice;
    }

}
