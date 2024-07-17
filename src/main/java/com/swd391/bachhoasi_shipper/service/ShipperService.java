package com.swd391.bachhoasi_shipper.service;

import com.swd391.bachhoasi_shipper.model.dto.request.ShipperRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.ShipperResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;

public interface ShipperService {
    JavaMailSender getJavaMailSender();
    ShipperResponseDto getShipperDetail();
    ShipperResponseDto resetPassword(String password);
    ShipperResponseDto registerNewShipper(ShipperRequest shipperRequest) throws MessagingException;
    ShipperResponseDto updateUser(ShipperRequest shipperRequest);
}
