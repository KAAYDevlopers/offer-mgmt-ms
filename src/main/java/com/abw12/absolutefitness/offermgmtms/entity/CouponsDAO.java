package com.abw12.absolutefitness.offermgmtms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupons" , schema = "offermgmt")
public class CouponsDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "coupon_id")
    private UUID couponId;
    private String description;
    @Column(name = "discount_type")
    private String discountType;
    @Column(name = "discount_value")
    private Integer discountValue;
    @Column(name = "start_date ")
    private OffsetDateTime startDate;
    @Column(name = "end_date ")
    private OffsetDateTime endDate;
    @Column(name = "min_order_value")
    private Double minOrderValue;
    @Column(name = "coupon_code")
    private String couponCode;
    @Column(name = "usage_limit")
    private Integer usageLimit;  //no. of time coupons can be used by any customer
    @Column(name = "usage_per_user")
    private Integer usagePerUser; //no. of time coupons can be used by single customer
    @Column(name = "is_active")
    private Boolean isActive; //coupon validity
}
