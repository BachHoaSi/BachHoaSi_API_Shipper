package com.swd391.bachhoasi_shipper.controller;

import com.swd391.bachhoasi_shipper.model.dto.request.OrderRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;
import com.swd391.bachhoasi_shipper.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
