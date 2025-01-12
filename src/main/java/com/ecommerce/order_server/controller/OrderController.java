package com.ecommerce.order_server.controller;

import com.ecommerce.order_server.dto.OrderDto;
import com.ecommerce.order_server.service.OrderService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto,@RequestParam("userId") Long userId) {
        OrderDto createdOrder = orderService.createOrder(orderDto,userId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        OrderDto orderDto = orderService.getOrderById(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orderDtos = orderService.getAllOrders();
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDto updateOrder) {
        OrderDto orderDto = orderService.updateOrder(id, updateOrder);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }

    @PostMapping("/addProduct")
    public ResponseEntity<OrderDto> addProductToOrder(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam Long userId) {
        OrderDto updatedOrder = orderService.addProductToOrder(productId, orderId, userId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
    
}
