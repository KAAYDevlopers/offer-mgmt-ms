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
@Table(name = "offervariant" ,schema = "offermgmt")
public class OfferVariantDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "offer_id")
    private UUID offerId;

    @Column(name = "variant_id")
    private String variantId;

}
