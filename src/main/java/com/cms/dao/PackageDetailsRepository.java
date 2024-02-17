package com.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.entity.PackageDetails;

import java.util.Optional;

public interface PackageDetailsRepository extends JpaRepository<PackageDetails, Long> {
    Optional<PackageDetails> findById(PackageDetails packageDetails);
}