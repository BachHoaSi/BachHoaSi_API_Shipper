package com.swd391.bachhoasi_shipper.model.entity;

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
@Entity(name = "StoreType")
public class StoreType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description", columnDefinition = "text")
    private String description;
    @Column(name = "Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;
}
