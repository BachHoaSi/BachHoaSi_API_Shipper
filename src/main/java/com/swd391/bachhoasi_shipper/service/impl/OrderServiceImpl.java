package com.swd391.bachhoasi_shipper.service.impl;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderDetailResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderProductMenuResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi_shipper.model.entity.Order;
import com.swd391.bachhoasi_shipper.model.exception.ActionFailedException;
import com.swd391.bachhoasi_shipper.model.exception.NotFoundException;
import com.swd391.bachhoasi_shipper.model.exception.ValidationFailedException;
import com.swd391.bachhoasi_shipper.repository.OrderRepository;
import com.swd391.bachhoasi_shipper.service.OrderService;
import com.swd391.bachhoasi_shipper.util.AuthUtils;
import com.swd391.bachhoasi_shipper.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AuthUtils authUtils;
    @Override
    public OrderResponse updateOrder(BigDecimal id, OrderRequest orderRequest)
    {
        var loginUser = authUtils.getShipper();
        var orderEntity = orderRepository.findById(id);
        var shipperid = orderEntity.get().getShipper().getId();
        if (orderRepository.findById(id) == null)
            throw new ValidationFailedException("Order request is null, please check again !!!");
        if (loginUser == null)
            throw new ValidationFailedException("Login user is null, please check again !!!");
        if (!Objects.equals(loginUser.getId(), shipperid))
            throw new ValidationFailedException("Login user is not the shipper");
        if (orderRequest.getOrderStatus().equals(OrderStatus.CANCELLED) || orderRequest.getOrderStatus().equals(OrderStatus.PICKED_UP) || orderRequest.getOrderStatus().equals(OrderStatus.ACCEPTED)) {
            throw new ValidationFailedException("You can change status to" + orderRequest.getOrderStatus());

        }

        if (orderEntity.get().getOrderStatus().equals(OrderStatus.DELIVERED) || orderEntity.get().getOrderStatus().equals(OrderStatus.CANCELLED) ) {
            throw new ValidationFailedException("Order is in status" + orderEntity.get().getOrderStatus().toString() + ", cannot change !!!");
        }
        orderEntity.get().setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.get().setUpdatedDate(new Date(System.currentTimeMillis()));
        try {
            Order updatedOrder = orderRepository.save(orderEntity.get());
            return OrderResponse.builder()
                    .id(updatedOrder.getId())
                    .orderContact(updatedOrder.getOrderContact())
                    .orderStatus(updatedOrder.getOrderStatus())
                    .deliveryFeedback(updatedOrder.getDeliveryFeedback())
                    .orderStatus(updatedOrder.getOrderStatus())
                    .build();
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

    @Override
    public OrderDetailResponse getDetailOrder(BigDecimal orderId) {
        var loginUser = authUtils.getShipper();
        var orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Not found this order"));
        if (loginUser == null)
            throw new ValidationFailedException("Login user is null, please check again !!!");
        if (loginUser != orderEntity.getShipper())
            throw new ValidationFailedException("Login user is not the shipper");
        var orderContact = orderEntity.getOrderContact();
        var orderProductList = orderEntity.getOrderProducts();
        List<OrderProductMenuResponse> orderProductListResponse = Collections.emptyList();
        if (orderProductList != null) {
            orderProductListResponse = orderProductList.stream().map(item -> {
                var productMenu = item.getProduct();
                var product = productMenu.getComposeId().getProduct();
                return OrderProductMenuResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .productName(product.getName())
                        .url(product.getUrlImages())
                        .category(product.getCategory().getName())
                        .build();
            }).toList();
        }
        return OrderDetailResponse.builder()
                .orderId(orderId)
                .storeName(orderContact.getCustomerName())
                .orderStatus(orderEntity.getOrderStatus())
                .total(orderEntity.getSubTotal())
                .createdAt(orderEntity.getCreatedDate())
                .deliveryTime(null)
                .feedback(orderEntity.getOrderFeedback())
                .orderContactId(orderContact.getId())
                .buildingNumber(orderContact.getBuildingNumber())
                .phoneNumber(orderContact.getPhoneNumber())
                .street(orderContact.getStreet())
                .paymentMethod(orderEntity.getPayingMethod())
                .grandTotal(orderEntity.getGrandTotal())
                .orderProductMenu(orderProductListResponse)
                .build();
    }

    public OrderResponse mapToOrderRespone(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderContact(order.getOrderContact())
                .orderStatus(order.getOrderStatus())
                .deliveryFeedback(order.getDeliveryFeedback())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
