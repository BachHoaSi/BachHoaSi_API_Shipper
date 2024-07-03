package com.swd391.bachhoasi_shipper.model.entity;


import com.swd391.bachhoasi_shipper.model.constant.OrderStatus;
import com.swd391.bachhoasi_shipper.model.constant.PayingMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
@Entity(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @Column(name = "SubTotal")
    private BigDecimal subTotal;
    @Column(name = "Discount")
    private Double discount;
    @Column(name = "GrandTotal")
    private BigDecimal grandTotal;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "OrderStatus")
    private OrderStatus orderStatus;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "PayingMethod")
    private PayingMethod payingMethod;
    @Column(name = "Point")
    private Integer point;
    @Column(name = "OrderFeedback", columnDefinition = "text")
    private String orderFeedback;
    @Column(name = "DeliveryFeedback", columnDefinition = "text")
    private String deliveryFeedback;
    @ManyToOne(targetEntity = Admin.class, optional = true)
    @JoinColumn(name = "AdminId")
    private Admin admin;
    @OneToOne(targetEntity = OrderContact.class, optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private OrderContact orderContact;
    @OneToMany(targetEntity = OrderProductMenu.class,mappedBy = "order" , cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<OrderProductMenu> orderProducts;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "CreatedDate", nullable = false)
    private Date createdDate;
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;
    @ManyToOne(targetEntity = Shipper.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipperId",referencedColumnName = "id", columnDefinition = "bigint")
    private Shipper shipper;
    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "StoreId")
    private Store store;
}
