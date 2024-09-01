package com.eduardoschelive.deliverbills.filter.repository;


import com.eduardoschelive.deliverbills.filter.entity.FilterTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterTestRepository extends JpaRepository<FilterTestEntity, Long>, JpaSpecificationExecutor<FilterTestEntity> {
}
