package com.swd391.bachhoasi_shipper.repository;

import com.swd391.bachhoasi_shipper.model.entity.Order;
import com.swd391.bachhoasi_shipper.model.entity.Product;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends BaseBachHoaSiRepository<Order, BigDecimal> {
}
