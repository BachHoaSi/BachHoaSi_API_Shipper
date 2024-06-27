package com.swd391.bachhoasi_shipper.model.dto.request;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderRequest {
    private BigDecimal id;
    @NotBlank(message = "FeedBack should't empty")
    private String deliveryFeedback;
    @NotBlank(message = "Order Status should't empty")
    private OrderStatus orderStatus;
}
