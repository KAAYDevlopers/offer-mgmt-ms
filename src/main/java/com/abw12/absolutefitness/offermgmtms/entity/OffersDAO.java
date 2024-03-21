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
@Table(name = "offers" ,schema = "offermgmt")
public class OffersDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Adjusted for UUID
    @Column(name = "offer_id")
    private UUID offerId; // Adjusted type for UUID generation

    private String description;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private String discountValue;

    @Column(name = "start_date")
    private OffsetDateTime startDate;

    @Column(name = "end_date")
    private OffsetDateTime endDate;
    @Column(name = "is_active")
    private Boolean isActive; //offer validity
}
