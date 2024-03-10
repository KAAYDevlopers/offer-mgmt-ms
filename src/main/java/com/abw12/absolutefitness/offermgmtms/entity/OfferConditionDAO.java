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
@Table(name = "offercondition" , schema = "offermgmt")
public class OfferConditionDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "offer_id")
    private UUID offerId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "variant_name")
    private String variantName;

}
