package com.swd391.bachhoasi_shipper.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi_shipper.model.constant.StoreStatus;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.StoreResponseDto;

@Service
public interface StoreService {
    StoreResponseDto disableStore(BigDecimal id);
    StoreResponseDto updateStoreRegisterReview(BigDecimal id, StoreStatus status);
    PaginationResponse<StoreResponseDto> getAllStore(SearchRequestParamsDto request);
}
