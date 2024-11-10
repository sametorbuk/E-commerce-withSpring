package com.teknotik.ecommmerce_backend.dto;

import com.teknotik.ecommmerce_backend.entity.OrderProduct;

import java.util.List;

public record OrderResponse(long addressId , String orderDate, String cardNo,
                            String cardName, double price, List<OrderProduct> orderProducts) {
}
