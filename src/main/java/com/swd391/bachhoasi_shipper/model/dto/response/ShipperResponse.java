package com.swd391.bachhoasi_shipper.model.dto.response;

import com.swd391.bachhoasi_shipper.model.constant.ShipperStatus;
import com.swd391.bachhoasi_shipper.model.constant.VehicleType;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipperResponse {
    private BigDecimal id;
    private String name;
    private String phone;
    private String email;
    private ShipperStatus status;
    private Date licenseIssueDate;
    private String idCardNumber;
    private String idCardIssuePlace;
    private Date idCardIssueDate;
    private VehicleType vehicleType;
    private Date createdDate;
    private Date updatedDate;
}
