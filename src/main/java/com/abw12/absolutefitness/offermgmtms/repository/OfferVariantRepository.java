package com.abw12.absolutefitness.offermgmtms.repository;

import com.abw12.absolutefitness.offermgmtms.entity.OfferVariantDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OfferVariantRepository extends JpaRepository<OfferVariantDAO, UUID> {

    @Query("SELECT v FROM OfferVariantDAO v WHERE v.offerId =:offerId")
    Optional<Set<OfferVariantDAO>> getVariantIds(UUID offerId);

    @Modifying
    @Query("DELETE FROM OfferVariantDAO o WHERE o.offerId =:offerId")
    Optional<Integer> deleteOfferVariantByOfferId(UUID offerId);
}
