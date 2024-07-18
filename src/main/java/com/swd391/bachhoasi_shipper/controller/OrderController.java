package com.swd391.bachhoasi_shipper.controller;

import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi_shipper.model.dto.response.OrderDetailResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;
import com.swd391.bachhoasi_shipper.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PutMapping
    public ResponseEntity<ResponseObject> updateOrder(@RequestParam BigDecimal id, @RequestBody @Valid OrderRequest orderRequest) {
        var result = orderService.updateOrder(id, orderRequest);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("ORDER_UPDATE_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Update Order Success")
                        .status(HttpStatus.OK)
                        .build());
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getAllByShipper(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pagination,
            @RequestParam(required = false, name = "q") String query
    ) {
        var queryDto = SearchRequestParamsDto.builder()
                .search(query)
                .wrapSort(pagination)
                .build();
        var result = orderService.getShipperOrders(queryDto);
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
    @GetMapping("{orderId}")
    public ResponseEntity<ResponseObject> getOrderDetail(@PathVariable(name = "orderId") BigDecimal orderId) {
        OrderDetailResponse orderResponse = orderService.getDetailOrder(orderId);
        var responseObject = ResponseObject.builder()
                .data(orderResponse)
                .code("GET_ORDER_DETAILED_SUCCESS")
                .message("Get order details successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }
}
