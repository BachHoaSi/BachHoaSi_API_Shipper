package com.swd391.bachhoasi_shipper.service;

import org.springframework.data.domain.Pageable;

import com.swd391.bachhoasi_shipper.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi_shipper.model.entity.StoreType;

import java.math.BigDecimal;
import java.util.Optional;

public interface StoreTypeService {
    StoreTypeResponse createNewStoreType(StoreTypeRequest storeTypeRequest);
    StoreTypeResponse updateStoreType(StoreTypeRequest storeTypeRequest);
    Optional<StoreType> findById(BigDecimal id);
    PaginationResponse<StoreType> getStoreTypes(Pageable pagination, String keyword);
    StoreTypeResponse deleteStoreTypeById(BigDecimal id);
}
