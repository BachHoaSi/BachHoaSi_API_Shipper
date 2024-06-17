package com.swd391.bachhoasi_shipper.model.dto.response;

import com.swd391.bachhoasi_shipper.model.constant.Role;
import com.swd391.bachhoasi_shipper.model.constant.TokenType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Role role;
    private TokenType tokenType;
    public LoginResponse(String accessToken, String refreshToken, Role role) {
        this.accessToken = accessToken;
        this.role = role;
        this.refreshToken = refreshToken;
        this.tokenType = TokenType.BEARER;
    }

}
