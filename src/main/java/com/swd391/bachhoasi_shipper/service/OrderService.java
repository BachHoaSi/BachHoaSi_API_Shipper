package com.swd391.bachhoasi_shipper.service;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.StoreTypeResponse;

public interface OrderService
{
    OrderResponse updateOrder(OrderRequest orderRequest);

}
