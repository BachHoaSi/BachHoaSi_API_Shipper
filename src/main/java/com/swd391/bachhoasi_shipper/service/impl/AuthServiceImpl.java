package com.swd391.bachhoasi_shipper.service.impl;


import com.swd391.bachhoasi_shipper.model.dto.response.ShipperResponse;
import com.swd391.bachhoasi_shipper.model.entity.Shipper;
import com.swd391.bachhoasi_shipper.repository.ShipperRepository;
import com.swd391.bachhoasi_shipper.util.AuthUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi_shipper.model.constant.TokenType;
import com.swd391.bachhoasi_shipper.model.dto.request.LoginDto;
import com.swd391.bachhoasi_shipper.model.dto.response.LoginResponse;
import com.swd391.bachhoasi_shipper.model.exception.AuthFailedException;
import com.swd391.bachhoasi_shipper.security.JwtProvider;
import com.swd391.bachhoasi_shipper.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ShipperRepository shipperRepository;
    private final AuthUtils authUtils;


    @Override
    public LoginResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtProvider.generateToken(authentication, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        try {
            Shipper user = shipperRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow();
            return new LoginResponse(accessToken, refreshToken);
        }catch (Exception e){
            throw new AuthFailedException("Your account may have been remove or disable during login");
        }
    }

    public LoginResponse createAccessToken(String refreshToken) {
        try {
            String username = jwtProvider.getUsernameFromJWT(refreshToken, TokenType.REFRESH_TOKEN);
            String accessToken = jwtProvider.generateToken(username, TokenType.ACCESS_TOKEN);
            Shipper user = shipperRepository.findByEmail(username)
                    .orElseThrow();
            return new LoginResponse(accessToken, refreshToken);
        }catch (Exception e){
            throw new AuthFailedException("Refresh token isn't valid, please try again");
        }
    }

    @Override
    public ShipperResponse cellDetail() {
        var shipper = authUtils.getShipper();

        return ShipperResponse.builder()
                .id(shipper.getId())
                .name(shipper.getName())
                .phone(shipper.getPhone())
                .email(shipper.getEmail())
                .status(shipper.getStatus())
                .licenseIssueDate(shipper.getLicenseIssueDate())
                .idCardNumber(shipper.getIdCardNumber())
                .idCardIssuePlace(shipper.getIdCardIssuePlace())
                .idCardIssueDate(shipper.getIdCardIssueDate())
                .vehicleType(shipper.getVehicleType())
                .createdDate(shipper.getCreatedDate())
                .updatedDate(shipper.getUpdatedDate())
                .build();

    }
}
