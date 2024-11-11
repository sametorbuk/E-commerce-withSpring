package com.teknotik.ecommmerce_backend.controller;

import com.teknotik.ecommmerce_backend.dto.OrderResponse;
import com.teknotik.ecommmerce_backend.entity.Order;
import com.teknotik.ecommmerce_backend.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

     private OrderServiceImpl orderService;

     @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public OrderResponse saveOrder(@RequestHeader("Authorization") String token , @RequestBody Order order){
         return orderService.save(token, order);
    }

    @PutMapping
    public OrderResponse updateOrder(@RequestHeader("Authorization") String token,@RequestBody Order order){
         return orderService.update(token, order);
    }
}
