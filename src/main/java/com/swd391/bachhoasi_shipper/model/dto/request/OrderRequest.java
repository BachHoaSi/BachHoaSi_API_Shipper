package com.swd391.bachhoasi_shipper.model.dto.request;

import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class OrderRequest {

    @NotBlank(message = "Order Status should't empty")
    private OrderStatus orderStatus;

}
