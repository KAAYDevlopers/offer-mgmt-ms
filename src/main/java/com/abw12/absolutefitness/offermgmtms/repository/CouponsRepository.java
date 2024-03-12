package com.abw12.absolutefitness.offermgmtms.repository;

import com.abw12.absolutefitness.offermgmtms.entity.CouponsDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouponsRepository extends JpaRepository<CouponsDAO, UUID> {

    @Query("SELECT c FROM CouponsDAO c WHERE c.couponCode =:couponCode")
    Optional<CouponsDAO> fetchCouponByCode(String couponCode);
}
