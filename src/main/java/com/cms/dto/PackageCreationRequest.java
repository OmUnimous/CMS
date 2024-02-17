package com.cms.dto;

import com.cms.entity.PackageDetails;
import com.cms.entity.PackageEntity;

public class PackageCreationRequest {
    private PackageEntity packageEntity;
    private PackageDetails packageDetails;

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public PackageDetails getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(PackageDetails packageDetails) {
        this.packageDetails = packageDetails;
    }
}
