package com.swd391.bachhoasi_shipper.model.exception;

import org.springframework.http.HttpStatus;

import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;

public class AuthFailedException extends BachHoaSiBaseException{

    public AuthFailedException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
        .code("AUTH-FAILED")
        .data(null)
        .message(message)
        .isSuccess(false)
        .status(HttpStatus.UNAUTHORIZED)
        .build();
    }
}
