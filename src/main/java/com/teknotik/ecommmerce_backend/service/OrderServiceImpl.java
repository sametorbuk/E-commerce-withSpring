package com.teknotik.ecommmerce_backend.service;


import com.teknotik.ecommmerce_backend.dto.DtoConverter;
import com.teknotik.ecommmerce_backend.dto.OrderResponse;
import com.teknotik.ecommmerce_backend.entity.Order;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderResponse findById(long id) {
        Optional<Order> foundOrder=orderRepository.findById(id);
        if(foundOrder.isPresent()){
            return DtoConverter.orderToOrderResponse(foundOrder.get());
        }else{
            throw new EcommerceException("There is no order with this id", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public OrderResponse save(Order order) {
        return DtoConverter.orderToOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse delete(Order order) {
        Optional<Order> foundOrder=orderRepository.findById(order.getId());
        if(foundOrder.isPresent()){
            orderRepository.delete(foundOrder.get());
            return DtoConverter.orderToOrderResponse(foundOrder.get());
        }else{
            throw new EcommerceException("There is no order with this id" + order.getId() , HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public OrderResponse update(Order order) {
        Optional<Order> existOrder=orderRepository.findById(order.getId());
        if(existOrder.isPresent()){
            Order newVersion=existOrder.get();
            newVersion.setCvv(order.getCvv());
            newVersion.setCardNo(order.getCardNo());
            newVersion.setAddressId(order.getAddressId());
            newVersion.setOrderDate(order.getOrderDate());
            newVersion.setCardExpireYear(order.getCardExpireYear());
            newVersion.setPrice(order.getPrice());
            newVersion.setOrderProducts(order.getOrderProducts());
            newVersion.setCardExpireMonth(order.getCardExpireMonth());
            orderRepository.save(newVersion);
            return DtoConverter.orderToOrderResponse(newVersion);
        }

        throw new EcommerceException("There is no order with this id",HttpStatus.NOT_FOUND);

    }


}