package com.swd391.bachhoasi_shipper.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
@Entity(name = "ProductMenu")
@Data
@Builder
public class ProductMenu implements Serializable {
    @EmbeddedId
    private ProductMenuId composeId;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id",columnDefinition = "BIGINT", nullable = false, unique = true)
    private BigDecimal id;
    @Column(name = "Price")
    private BigDecimal basePrice;
    @Column(name = "CreatedDate")
    @UpdateTimestamp(source = SourceType.DB)
    private Date createdDate;
    @Column(name = "UpdatedDate")
    @UpdateTimestamp(source = SourceType.DB)
    private Date updatedDate;
    @Column(name = "Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;
    @ManyToOne(targetEntity = Admin.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "AdminId", nullable = false)
    private Admin admin;
}
