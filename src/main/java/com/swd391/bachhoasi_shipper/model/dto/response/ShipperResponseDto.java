package com.swd391.bachhoasi_shipper.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd391.bachhoasi_shipper.model.constant.ShipperStatus;
import com.swd391.bachhoasi_shipper.model.constant.ShippingStatus;
import com.swd391.bachhoasi_shipper.model.constant.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
public class ShipperResponseDto {
    private BigDecimal id;
    private String name;
    private String phone;
    private String email;
    private ShipperStatus status;
    private ShippingStatus shippingStatus;
    private String licenseNumber;
    private Date licenseIssueDate;
    private String idCardNumber;
    private String idCardIssuePlace;
    private Date idCardIssueDate;
    private VehicleType vehicleType;
    private Date createdDate;
    private Date updatedDate;
    private Boolean isActive;
    private Boolean isLocked;
}
