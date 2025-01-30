package com.ecommerce.order_server.controller;

import com.ecommerce.order_server.dto.OrderDto;
import com.ecommerce.order_server.service.OrderService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestParam("userId") Long userId) {
        OrderDto createdOrder = orderService.createOrder(userId);
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
public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long id) {
    try {
        orderService.deleteOrder(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Order deleted successfully");
        return ResponseEntity.ok(response); // ✅ Ensure JSON response
    } catch (Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

    @PostMapping("/addProduct")
    public ResponseEntity<OrderDto> addProductToOrder(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam Long userId) {
        OrderDto updatedOrder = orderService.addProductToOrder(productId, orderId, userId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
    @DeleteMapping("deleteProduct")
    public ResponseEntity<OrderDto> removeProductFromOrder(@RequestParam Long orderId, 
                                                           @RequestParam Long productId) {
        OrderDto updatedOrder = orderService.removeProductFromOrder(orderId, productId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);

    }
}
