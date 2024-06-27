package com.swd391.bachhoasi_shipper.model.dto.response;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private String deliveryFeedback;
    private OrderStatus orderStatus;
}
