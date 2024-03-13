package com.abw12.absolutefitness.offermgmtms.repository;

import com.abw12.absolutefitness.offermgmtms.entity.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO, UUID> {

    @Query("SELECT c FROM CustomerDAO c WHERE c.couponId =:couponId")
    Optional<CustomerDAO> fetchCouponEntries(UUID couponId);

    @Query("SELECT u FROM CustomerDAO u WHERE u.userId =:userId")
    Set<CustomerDAO> getUserEntries(String userId);

}
