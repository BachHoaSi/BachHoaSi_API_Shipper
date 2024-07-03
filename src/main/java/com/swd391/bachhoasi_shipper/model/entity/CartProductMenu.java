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
@Entity(name = "CartProductMenu")
public class CartProductMenu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @ManyToOne(targetEntity = Cart.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "CartId", referencedColumnName = "id", columnDefinition = "bigint")
    private Cart cart;
    @ManyToOne(targetEntity = ProductMenu.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductId", referencedColumnName = "id")
    private ProductMenu product;
    @Column(name = "Quantity")
    private Integer quantity;
    @Column(name = "Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;
}
