package com.eduardoschelive.deliverbills.bills.repository;

import com.eduardoschelive.deliverbills.bills.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID>, JpaSpecificationExecutor<Bill> {
    void removeByUuid(UUID uuid);
}
