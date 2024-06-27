package com.swd391.bachhoasi_shipper.service;

import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderResponse;

public interface OrderService
{
    OrderResponse updateOrder(OrderRequest orderRequest);

}
