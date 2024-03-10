//package com.abw12.absolutefitness.offermgmtms.entity;
//
//import com.abw12.absolutefitness.offermgmtms.dto.OfferConditionDTO;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.OffsetDateTime;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "coupons" , schema = "offermgmt")
//public class CouponsDAO {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "coupon_id")
//    private UUID couponId;
//    private String description;
//    @Column(name = "discount_type")
//    private String discountType;
//    @Column(name = "discount_value")
//    private Integer discountValue;
////    @Column(name = "applicable_variant_ids")
////    @Convert(converter = StringListConverter.class)
////    private List<String> applicableVariantIds;
////    @Column(name = "applicable_categories")
////    @Convert(converter = StringListConverter.class)
////    private List<String> applicableCategories;
//    @Column(name = "start_date ")
//    private OffsetDateTime startDate;
//    @Column(name = "end_date ")
//    private OffsetDateTime endDate;
//    @Column(name = "conditions", columnDefinition = "jsonb")
//    private  conditions;
//    private String couponCode;
//    @Column(name = "usage_limit")
//    private Integer usageLimit;
//    @Column(name = "usage_per_user")
//    private Integer usagePerUser;
//    @Column(name = "is_active")
//    private Boolean isActive;
//
//}
