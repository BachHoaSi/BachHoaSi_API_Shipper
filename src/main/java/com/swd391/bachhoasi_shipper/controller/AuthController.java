package com.swd391.bachhoasi_shipper.controller;

import com.swd391.bachhoasi_shipper.model.dto.request.ShipperLoginDto;
import com.swd391.bachhoasi_shipper.model.dto.response.ShipperLoginResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi_shipper.model.dto.request.LoginDto;
import com.swd391.bachhoasi_shipper.model.dto.request.RefreshTokenRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.LoginResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;
import com.swd391.bachhoasi_shipper.service.AuthService;
import com.swd391.bachhoasi_shipper.util.AuthUtils;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authentication")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDto loginDto){
        LoginResponse jwtAuthResponse = authService.login(loginDto);
        var responseObject = ResponseObject.builder()
            .code("AUTH_SUCCESS")
            .message("Welcome To Bach Hoa Si")
            .status(HttpStatus.OK)
            .isSuccess(true)
            .data(jwtAuthResponse)
            .build();
        return ResponseEntity.ok()
            .headers(AuthUtils.getAuthenticationHeader(jwtAuthResponse))
            .body(responseObject);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseObject> refreshAccessToken(@RequestBody RefreshTokenRequest refreshToken) {
        LoginResponse jwtAuthResponse = authService.createAccessToken(refreshToken.getRefreshToken());
        var responseObject = ResponseObject.builder()
            .code("REFRESH_SUCCESS")
            .message("Welcome To Bach Hoa Si")
            .status(HttpStatus.OK)
            .isSuccess(true)
            .data(jwtAuthResponse)
            .build();
        return ResponseEntity.ok()
            .headers(AuthUtils.getAuthenticationHeader(jwtAuthResponse))
            .body(responseObject);
    }



}
