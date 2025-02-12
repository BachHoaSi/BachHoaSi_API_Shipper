package com.swd391.bachhoasi_shipper.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "OrderProductMenu")
public class OrderProductMenu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", referencedColumnName = "id", columnDefinition = "bigint")
    private Order order;
    @Column(name = "Quantity")
    private Integer quantity;
    @ManyToOne(targetEntity = ProductMenu.class, fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "ProductMenuId", referencedColumnName = "id", nullable = false)
    private ProductMenu product;
}
