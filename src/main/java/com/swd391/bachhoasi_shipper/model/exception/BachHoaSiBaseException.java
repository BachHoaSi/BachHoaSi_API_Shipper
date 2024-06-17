package com.swd391.bachhoasi_shipper.model.exception;

import com.swd391.bachhoasi_shipper.model.dto.response.ResponseObject;

public class BachHoaSiBaseException extends RuntimeException {
    protected ResponseObject errorResponse;

    protected BachHoaSiBaseException(String message) {
        super(message);
    }

    public ResponseObject getErrorResponse() {
        return errorResponse;
    }
}
