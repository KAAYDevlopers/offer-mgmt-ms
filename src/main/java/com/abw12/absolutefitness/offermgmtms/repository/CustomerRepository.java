package com.abw12.absolutefitness.offermgmtms.repository;

import com.abw12.absolutefitness.offermgmtms.entity.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO, UUID> {
}
