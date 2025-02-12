package com.swd391.bachhoasi_shipper.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StoreTypeRequest {
    private BigDecimal id;
    @NotEmpty(message = "StoreType name should not be null or empty")
    private String name;
    @NotEmpty(message = "Description should not be null or empty")
    private String description;
    private Boolean status;

}
