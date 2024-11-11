package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.dto.OrderResponse;
import com.teknotik.ecommmerce_backend.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();

    OrderResponse findById(long id);

    OrderResponse save(String token,Order order);

    OrderResponse delete(String token,Order order);

    OrderResponse update(String token,Order order);

}
