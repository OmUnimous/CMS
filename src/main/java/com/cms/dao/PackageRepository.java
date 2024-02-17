package com.cms.dao;
import com.cms.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    Optional<PackageEntity> findById(PackageEntity packageEntity);
    List<PackageEntity> findAllByAccountId(Long accountId);
}
