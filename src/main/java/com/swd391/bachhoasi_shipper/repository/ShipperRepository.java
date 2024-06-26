package com.swd391.bachhoasi_shipper.repository;

import com.swd391.bachhoasi_shipper.model.entity.Admin;
import com.swd391.bachhoasi_shipper.model.entity.Shipper;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface ShipperRepository extends BaseBachHoaSiRepository<Shipper, BigDecimal> {
    @Query("select s from Shipper s where s.email = :email")
    Optional<Shipper> findByEmail(String email);
}
