package com.swd391.bachhoasi_shipper.service;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi_shipper.model.dto.request.ProductRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.ProductResponse;


@Service
public interface ProductService {

    PaginationResponse<ProductResponse> getProducts(SearchRequestParamsDto request);

    ProductResponse addNewProduct(ProductRequest request);

    void deleteProduct(String code);

    ProductResponse updateProduct(ProductRequest request, String code);
}
