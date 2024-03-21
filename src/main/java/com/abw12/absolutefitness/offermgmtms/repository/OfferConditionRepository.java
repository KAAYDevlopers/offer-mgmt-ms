package com.abw12.absolutefitness.offermgmtms.repository;

import com.abw12.absolutefitness.offermgmtms.entity.OfferConditionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OfferConditionRepository extends JpaRepository<OfferConditionDAO, UUID> {

    @Query("SELECT c FROM OfferConditionDAO c WHERE c.offerId =:offerId")
    Optional<Set<OfferConditionDAO>> getConditions(UUID offerId);

    @Modifying
    @Query("DELETE FROM OfferConditionDAO o WHERE o.offerId =:offerId")
    Optional<Integer> deleteOfferConditionByOfferId(UUID offerId);
}
