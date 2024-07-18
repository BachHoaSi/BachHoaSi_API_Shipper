package com.swd391.bachhoasi_shipper.service;

import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderDetailResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

public interface OrderService
{
    OrderResponse updateOrder(BigDecimal id, OrderRequest orderRequest);
    PaginationResponse<OrderResponse> getShipperOrders(SearchRequestParamsDto request);
    OrderDetailResponse getDetailOrder(BigDecimal orderId);

}
