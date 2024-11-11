package com.teknotik.ecommmerce_backend.service;


import com.teknotik.ecommmerce_backend.Util.JwtUtil;
import com.teknotik.ecommmerce_backend.dto.DtoConverter;
import com.teknotik.ecommmerce_backend.dto.OrderResponse;
import com.teknotik.ecommmerce_backend.entity.Order;
import com.teknotik.ecommmerce_backend.entity.User;
import com.teknotik.ecommmerce_backend.exceptions.EcommerceException;
import com.teknotik.ecommmerce_backend.repository.OrderRepository;
import com.teknotik.ecommmerce_backend.repository.UserRepository;
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
    private UserRepository userRepository;
    private JwtUtil jwtService;

    @Autowired
    public OrderServiceImpl(JwtUtil jwtService, OrderRepository orderRepository, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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
    public OrderResponse save(String token,Order order) {
       tokenIsValid(token);
       Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
       if(foundUser.isPresent()){
           User user = foundUser.get();
           user.addOrder(order);
           userRepository.save(user);
           orderRepository.save(order);
           return DtoConverter.orderToOrderResponse(order);

       }else{
           throw new EcommerceException("There is no user with this token",HttpStatus.NOT_FOUND);
       }

    }

    @Override
    public OrderResponse delete(String token,Order order) {
        tokenIsValid(token);
        Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
        if(foundUser.isPresent()){
            User user = foundUser.get();
            user.getOrders().remove(order);
            orderRepository.delete(order);
            userRepository.save(user);
            return DtoConverter.orderToOrderResponse(order);
        }else{
            throw new EcommerceException("There is no user with this id",HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public OrderResponse update(String token,Order order) {
     tokenIsValid(token);
     Optional<User> foundUser = userRepository.findByEmail(jwtService.extractUsername(token));
     if(foundUser.isPresent()){
         User user =foundUser.get();
         Optional<Order> existOrder=user.getOrders().stream().filter(ord -> ord.getId() == order.getId()).findFirst();
        existOrder.get().setOrderProducts(order.getOrderProducts());
        existOrder.get().setOrderDate(order.getOrderDate());
        existOrder.get().setCvv(order.getCvv());
        existOrder.get().setPrice(order.getPrice());
        existOrder.get().setCardExpireMonth(order.getCardExpireMonth());
        existOrder.get().setCardExpireYear(order.getCardExpireYear());
        existOrder.get().setAddressId(order.getAddressId());
        existOrder.get().setCardNo(order.getCardNo());
        existOrder.get().setCardName(order.getCardName());
        userRepository.save(user);
        return DtoConverter.orderToOrderResponse(order);
     }else{
         throw new EcommerceException("There is no user with this token",HttpStatus.NOT_FOUND);
     }
    }



    public void tokenIsValid(String token) {
        if (jwtService.isTokenExpired(token)) {
            throw new EcommerceException("This token has expired", HttpStatus.BAD_REQUEST);
        }
        if (jwtService.extractUsername(token) == null) {
            throw new EcommerceException("There is no user with this token", HttpStatus.NOT_FOUND);
        }
    }


}