package com.abw12.absolutefitness.offermgmtms.repository;

import com.abw12.absolutefitness.offermgmtms.entity.CouponVariantDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Repository
public interface CouponVariantRepository extends JpaRepository<CouponVariantDAO, UUID> {

    @Query("SELECT v FROM CouponVariantDAO v WHERE v.couponId =:couponId")
    Optional<Set<CouponVariantDAO>> fetchApplicableVariantIds(UUID couponId);
}
