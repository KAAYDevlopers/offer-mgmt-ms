package com.abw12.absolutefitness.offermgmtms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "couponvariant" , schema = "offermgmt")
public class CouponVariantDAO {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "coupon_id")
    private UUID couponId;

    @Column(name = "variant_id")
    private String variantId;

}
