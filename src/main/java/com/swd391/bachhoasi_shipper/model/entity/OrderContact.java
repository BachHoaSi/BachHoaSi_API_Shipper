package com.swd391.bachhoasi_shipper.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrderContact")
public class OrderContact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @OneToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId")
    private Order order;
    @Column(name = "PhoneNumber", columnDefinition = "varchar", length = 12)
    private String phoneNumber;
    @Column(name = "BuildingNumber", columnDefinition = "varchar", length = 12)
    private String buildingNumber;
    @Column(name = "Street", columnDefinition = "varchar", length = 20)
    private String street;
    @Column(name = "CustomerName", columnDefinition = "varchar", length = 20)
    private String customerName;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;
}
