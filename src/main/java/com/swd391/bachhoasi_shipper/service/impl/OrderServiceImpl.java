package com.swd391.bachhoasi_shipper.service.impl;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderResponse;
import com.swd391.bachhoasi_shipper.model.entity.Order;
import com.swd391.bachhoasi_shipper.model.exception.ValidationFailedException;
import com.swd391.bachhoasi_shipper.repository.OrderRepository;
import com.swd391.bachhoasi_shipper.service.OrderService;
import com.swd391.bachhoasi_shipper.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AuthUtils authUtils;
    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest)
    {
        var loginUser = authUtils.getShipper();
        if (orderRequest == null) {
            throw new ValidationFailedException("Order request is null, please check again !!!");
        }
        Optional<Order> orderOptional = orderRepository.findById(orderRequest.getId());
        if (orderOptional.isEmpty()) {
            throw new ValidationFailedException("Order not found, please check again !!!");
        }
        Order orderEntity = orderOptional.get();
        if (!orderEntity.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new ValidationFailedException("Order is " + orderEntity.getOrderStatus().toString() + ", cannot change shipper!!!");
        }
        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        orderEntity.setShipper(loginUser);
        orderEntity.setOrderFeedback(orderRequest.getDeliveryFeedback());

        try {
            Order updatedOrder = orderRepository.save(orderEntity);
            return mapToOrderRespone(updatedOrder);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update Order, please check again !!!");
        }
    }
    public OrderResponse mapToOrderRespone(Order order) {
        return OrderResponse.builder()
                .orderStatus(order.getOrderStatus())

                .build();
    }

}
