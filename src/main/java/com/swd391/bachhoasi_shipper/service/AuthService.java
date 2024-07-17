package com.swd391.bachhoasi_shipper.service;

import com.swd391.bachhoasi_shipper.model.dto.request.ShipperLoginDto;
import com.swd391.bachhoasi_shipper.model.dto.response.ShipperLoginResponse;
import com.swd391.bachhoasi_shipper.model.dto.response.ShipperResponse;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi_shipper.model.dto.request.LoginDto;
import com.swd391.bachhoasi_shipper.model.dto.response.LoginResponse;

@Service
public interface AuthService {
    LoginResponse login(LoginDto loginDto);
    LoginResponse createAccessToken(String refreshToken);
    public ShipperResponse cellDetail();
}
