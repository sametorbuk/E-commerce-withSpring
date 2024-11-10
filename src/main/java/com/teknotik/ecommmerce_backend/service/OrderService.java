package com.teknotik.ecommmerce_backend.service;

import com.teknotik.ecommmerce_backend.dto.OrderResponse;
import com.teknotik.ecommmerce_backend.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();

    OrderResponse findById(long id);

    OrderResponse save(Order order);

    OrderResponse delete(Order order);

    OrderResponse update(Order order);

}
