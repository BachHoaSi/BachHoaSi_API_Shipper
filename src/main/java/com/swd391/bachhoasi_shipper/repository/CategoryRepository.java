package com.swd391.bachhoasi_shipper.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi_shipper.model.entity.Category;

@Repository
public interface CategoryRepository extends BaseBachHoaSiRepository<Category, BigDecimal> {
    Optional<Category> findByCategoryCodeAndIdNot(String categoryCode, BigDecimal id);
}
