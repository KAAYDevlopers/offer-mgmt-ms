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
@Table(name = "customerentries" , schema = "offermgmt")
public class CustomerDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "coupon_id")
    private UUID couponId;
    @Column(name = "coupon_used_date")
    private OffsetDateTime couponUsedDate;
}
