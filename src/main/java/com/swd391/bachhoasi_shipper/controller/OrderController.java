package com.swd391.bachhoasi_shipper.controller;

import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;
import com.swd391.bachhoasi_shipper.model.entity.Order;
import com.swd391.bachhoasi_shipper.model.entity.StoreType;
import com.swd391.bachhoasi_shipper.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PutMapping
    public ResponseEntity<ResponseObject> updateStoreType(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.updateOrder(orderRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getAll(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pagination,
            @RequestParam(required = false, name = "q") String query
    ) {
        var queryDto = SearchRequestParamsDto.builder()
                .search(query)
                .wrapSort(pagination)
                .build();
        var result = orderService.getShipperOrders(queryDto.pagination(),queryDto.search());
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("ORDER_GET_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Get Order Success")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

}
