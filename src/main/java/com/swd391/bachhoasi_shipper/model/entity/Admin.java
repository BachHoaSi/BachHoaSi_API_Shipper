package com.swd391.bachhoasi_shipper.model.entity;


import com.swd391.bachhoasi_shipper.model.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Admin")
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @Column(name = "UserName", nullable = false, columnDefinition = "varchar", length = 32)
    private String username;
    @Column(name = "HashPassword", nullable = false, columnDefinition = "varchar", length = 64)
    private String hashPassword;
    @Column(name = "Fullname")
    private String fullName;
    @Column(name = "Role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "IsActive",nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isActive;
    @Column(name = "IsLocked",nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isLocked;
}
