package com.swd391.bachhoasi_shipper.service.impl;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi_shipper.model.entity.Order;
import com.swd391.bachhoasi_shipper.model.exception.ActionFailedException;
import com.swd391.bachhoasi_shipper.model.exception.ValidationFailedException;
import com.swd391.bachhoasi_shipper.repository.OrderRepository;
import com.swd391.bachhoasi_shipper.service.OrderService;
import com.swd391.bachhoasi_shipper.util.AuthUtils;
import com.swd391.bachhoasi_shipper.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (loginUser.getId() == orderRepository.findById(orderRequest.getId()).get().getShipper().getId()){
            throw new ValidationFailedException("The order is delivered to another shipper, please check again !!!");
        }

        if (orderRequest.getId() == null) {
            throw new ValidationFailedException("Order request is null, please check again !!!");
        }
        if (orderRequest.getOrderStatus().equals(OrderStatus.CANCELLED) || orderRequest.getOrderStatus().equals(OrderStatus.PICKED_UP)) {
            throw new ValidationFailedException("You can change status to" + orderRequest.getOrderStatus());

        }
        Optional<Order> orderOptional = orderRepository.findById(orderRequest.getId());
        if (orderOptional.isEmpty()) {
            throw new ValidationFailedException("Order not found, please check again !!!");
        }
        Order orderEntity = orderOptional.get();
        if (orderEntity.getOrderStatus().equals(OrderStatus.DELIVERED) || orderEntity.getOrderStatus().equals(OrderStatus.CANCELLED) ) {
            throw new ValidationFailedException("Order is in status" + orderEntity.getOrderStatus().toString() + ", cannot change !!!");
        }
        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        orderEntity.setShipper(loginUser);

        try {
            Order updatedOrder = orderRepository.save(orderEntity);
            return mapToOrderRespone(updatedOrder);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update Order, please check again !!!");
        }
    }



    @Override
    public PaginationResponse<OrderResponse> getShipperOrders(SearchRequestParamsDto request) {
        var loginUser = authUtils.getShipper();

        try {
            Page<OrderResponse> orderPage = orderRepository.searchByParameterAndShipperId(request.search(), request.pagination(), loginUser.getId())
                    .map(item -> OrderResponse.builder()
                            .id(item.getId())
                            .orderContact(item.getOrderContact())
                            .orderStatus(item.getOrderStatus())
                            .deliveryFeedback(item.getDeliveryFeedback())
                            .build());
            return new PaginationResponse<>(orderPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "ORDER_GET_FAILED");
        }
    }

    public OrderResponse mapToOrderRespone(Order order) {
        return OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
