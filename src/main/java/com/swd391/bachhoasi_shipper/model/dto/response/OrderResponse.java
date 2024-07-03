package com.swd391.bachhoasi_shipper.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.entity.OrderContact;
import com.swd391.bachhoasi_shipper.model.entity.Shipper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private BigDecimal id;
    private String deliveryFeedback;
    private OrderStatus orderStatus;
    private OrderContact orderContact;
    private Shipper shipper;



}
