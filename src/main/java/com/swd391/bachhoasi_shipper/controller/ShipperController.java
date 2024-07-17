package com.swd391.bachhoasi_shipper.controller;

import com.swd391.bachhoasi_shipper.model.dto.request.ShipperRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;
import com.swd391.bachhoasi_shipper.service.ShipperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shippers")
@Tag(name = "Shippers")
@RequiredArgsConstructor
public class ShipperController {
    private final ShipperService shipperService;

    @GetMapping()
    public ResponseEntity<ResponseObject> getShipperOne() {
        var result = shipperService.getShipperDetail();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("GET_SHIPPER_SUCCESS")
                        .isSuccess(true)
                        .message("Get Shipper Success")
                        .data(result)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Operation(summary = "Register new shipper", description = "Register new shipper, system will send password to shipper email")
    @PostMapping
    public ResponseEntity<ResponseObject> registerNewShipper(@RequestBody ShipperRequest shipperRequest) throws MessagingException {
        var result = shipperService.registerNewShipper(shipperRequest);
        var responseObject = ResponseObject
                .builder()
                .code("ADD_SHIPPER_SUCCESS")
                .message("Add shipper Successfully")
                .isSuccess(true)
                .data(result)
                .build();
        return ResponseEntity.ok(responseObject);
    }

    @Operation(summary = "Change Password", description = "input password and password will be hashed and saved")
    @PatchMapping("reset-password")
    public ResponseEntity<ResponseObject> ChangePassoword(@RequestParam String password)  {
        var result = shipperService.resetPassword(password);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("RESET_PASSWORD_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Rest Password Success")
                        .status(HttpStatus.OK)
                        .build()
        );
    }


    @Operation(summary = "Update shipper")
    @PutMapping()
    public ResponseEntity<ResponseObject> updateShipper (@RequestBody ShipperRequest request) {
        var result = shipperService.updateUser(request);
        var responseObject = ResponseObject
                .builder()
                .code("UPDATE_SHIPPER_SUCCESS")
                .message("Update Shipper Successfully")
                .isSuccess(true)
                .data(result)
                .build();
        return ResponseEntity.ok(responseObject);
    }
}
