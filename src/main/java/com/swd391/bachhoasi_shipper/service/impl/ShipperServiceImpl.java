package com.swd391.bachhoasi_shipper.service.impl;

import com.swd391.bachhoasi_shipper.model.dto.request.ShipperRequest;
import com.swd391.bachhoasi_shipper.model.dto.response.ShipperResponseDto;
import com.swd391.bachhoasi_shipper.model.entity.Shipper;
import com.swd391.bachhoasi_shipper.model.exception.ActionFailedException;
import com.swd391.bachhoasi_shipper.model.exception.NotFoundException;
import com.swd391.bachhoasi_shipper.model.exception.ValidationFailedException;
import com.swd391.bachhoasi_shipper.repository.ShipperRepository;
import com.swd391.bachhoasi_shipper.service.ShipperService;
import com.swd391.bachhoasi_shipper.util.AuthUtils;
import com.swd391.bachhoasi_shipper.util.BaseUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;
    private final AuthUtils authUtils;
    private final PasswordEncoder passwordEncoder;

    @Value("${mail.username}")
    private String email;
    @Value("${mail.password}")
    private String pass;
    @Override
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587); // Default SMTP port for TLS
        mailSender.setUsername(email);
        mailSender.setPassword(pass);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }


	@Override
	public ShipperResponseDto getShipperDetail() {
        var loginUser = authUtils.getShipper();

        Shipper item = shipperRepository.findById(loginUser.getId()).orElseThrow(()
        -> new NotFoundException(String.format("Not found shipper with id: %s", shipperRepository.findById(loginUser.getId()).toString())));
        return ShipperResponseDto.builder()
        .id(item.getId())
        .name(item.getName())
        .phone(item.getPhone())
        .email(item.getEmail())
        .status(item.getStatus())
        .shippingStatus(item.getShippingStatus())
        .licenseNumber(item.getLicenseNumber())
        .licenseIssueDate(item.getLicenseIssueDate())
        .idCardNumber(item.getIdCardNumber())
        .idCardIssuePlace(item.getIdCardIssuePlace())
        .idCardIssueDate(item.getIdCardIssueDate())
        .vehicleType(item.getVehicleType())
        .createdDate(item.getCreatedDate())
        .updatedDate(item.getUpdatedDate())
        .isActive(item.getIsActive())
        .isLocked(item.getIsLocked())
        .build();
	}



    @Override
    public ShipperResponseDto resetPassword(String password) {


            var loginUser = authUtils.getShipper();
            if (loginUser == null)
                throw new ActionFailedException(String.format("Login user is null"));


            var shipper = shipperRepository.findById(loginUser.getId());
            if (shipper.isEmpty()) {
                throw new NotFoundException(String.format("Not found shipper with id: %s", loginUser.getId().toString()));
            }


            /*passwordEncoder.encode(password);*/
            var hashPass = passwordEncoder.encode(password);
            Shipper shipperEntity = shipper.get();
            shipperEntity.setHashPassword(hashPass);
            try {
                var dbResult = shipperRepository.save(shipperEntity);
                return getShipperDetail();
            }catch (Exception ex) {
                throw new ActionFailedException(
                        String.format("Something happen when reset password: %s", ex.getMessage()));
            }

    }

    @Override
    public ShipperResponseDto registerNewShipper(ShipperRequest shipperRequest) throws MessagingException {
        Shipper shipper = Shipper.builder()
                .name(shipperRequest.getName())
                .phone(shipperRequest.getPhone())
                .licenseNumber(shipperRequest.getLicenseNumber())
                .licenseIssueDate(shipperRequest.getLicenseIssueDate())
                .idCardNumber(shipperRequest.getIdCardNumber())
                .idCardIssueDate(shipperRequest.getIdCardIssueDate())
                .idCardIssuePlace(shipperRequest.getIdCardIssuePlace())
                .vehicleType(shipperRequest.getVehicleType())
                .isActive(true)
                .isLocked(false)
                .build();;
        shipper.setEmail(shipperRequest.getEmail());
        JavaMailSender javaMailSender = getJavaMailSender();
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
        String password = BaseUtils.generatePassword(12);

        helper.setFrom(email);
        helper.setTo(shipperRequest.getEmail());
        helper.setSubject("SEND RESET PASSWORD");
        helper.setText(password);
        javaMailSender.send(msg);
        var hashPass = passwordEncoder.encode(password);
        shipper.setHashPassword(hashPass);
        try {
            var dbResult = shipperRepository.save(shipper);
            return convertToDto(dbResult);
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Something happen when adding new shipper to system: %s", ex.getMessage()));
        }
    }

    @Override
    public ShipperResponseDto updateUser(ShipperRequest shipperRequest) {
        var loginUser = authUtils.getShipper();

        Shipper userDb = shipperRepository.findById(loginUser.getId()).orElseThrow(() -> new NotFoundException(
                String.format("Can't found shipper with id: %s", loginUser.getId().toString())));
        userDb.setName(shipperRequest.getName());
        userDb.setPhone(shipperRequest.getPhone());
        userDb.setEmail(shipperRequest.getEmail());
        userDb.setLicenseNumber(shipperRequest.getLicenseNumber());
        userDb.setLicenseIssueDate(shipperRequest.getLicenseIssueDate());
        userDb.setIdCardNumber(shipperRequest.getIdCardNumber());
        userDb.setIdCardIssueDate(shipperRequest.getIdCardIssueDate());
        userDb.setIdCardIssuePlace(shipperRequest.getIdCardIssuePlace());
        try {
            Shipper dbResult = shipperRepository.save(userDb);
            return ShipperResponseDto.builder()
                    .id(dbResult.getId())
                    .name(userDb.getName())
                    .email(userDb.getEmail())
                    .phone(userDb.getPhone())
                    .licenseNumber(userDb.getLicenseNumber())
                    .licenseIssueDate(userDb.getLicenseIssueDate())
                    .idCardNumber(userDb.getIdCardNumber())
                    .idCardIssueDate(userDb.getIdCardIssueDate())
                    .idCardIssuePlace(userDb.getIdCardIssuePlace())
                    .vehicleType(userDb.getVehicleType())
                    .updatedDate(userDb.getUpdatedDate())
                    .createdDate(userDb.getCreatedDate())
                    .isActive(userDb.getIsActive())
                    .isLocked(userDb.getIsLocked())
                    .status(userDb.getStatus())
                    .shippingStatus(userDb.getShippingStatus())
                    .build();
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update Shipper, please check again !!!");
        }
    }

    private ShipperResponseDto convertToDto(Shipper item) {
        return ShipperResponseDto.builder()
                .id(item.getId())
                .email(item.getEmail())
                .name(item.getName())
                .isActive(item.getIsActive())
                .isLocked(item.getIsLocked())
                .build();
    }

    private Shipper addUpdateFieldToShipperEntity(ShipperRequest request, Shipper shipperEntity) {
        if (shipperEntity == null || request == null) {
            throw new ValidationFailedException(
                    "Request or Shipper entity is null on function::addUpdateFieldToShipperEntity");
        }
        shipperEntity.setName(request.getName());
        shipperEntity.setPhone(request.getPhone());
        shipperEntity.setLicenseNumber(request.getLicenseNumber());
        shipperEntity.setLicenseIssueDate(request.getLicenseIssueDate());
        shipperEntity.setIdCardNumber(request.getIdCardNumber());
        shipperEntity.setIdCardIssuePlace(request.getIdCardIssuePlace());
        shipperEntity.setLicenseIssueDate(request.getLicenseIssueDate());
        shipperEntity.setVehicleType(request.getVehicleType());

        return shipperEntity;
    }
}
