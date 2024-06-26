package com.swd391.bachhoasi_shipper.model.dto.request;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.constant.PayingMethod;
import com.swd391.bachhoasi_shipper.model.entity.OrderContact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
