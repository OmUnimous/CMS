package com.cms.controller;

import com.cms.dao.PackageRepository;
import com.cms.entity.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PackageRepository packageRepository;
    @PostMapping("/packages")
    public ResponseEntity<List<PackageEntity>> findAllPackages() {
        try {
            List<PackageEntity> packages = packageRepository.findAll();
            return ResponseEntity.ok(packages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/packages/status/{id}")
    public ResponseEntity<String> updatePackageStatus(@PathVariable Long id, @RequestBody String newStatus) {
        System.out.println(newStatus);
        try {
            //String newStatus = requestBody.get("newStatus");
            Optional<PackageEntity> optionalPackage = packageRepository.findById(id);
            if (optionalPackage.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            PackageEntity packageEntity = optionalPackage.get();
            System.out.println("Fetched Package Data:");
            System.out.println("Tracking Number: " + packageEntity.getTrackingNumber());
            System.out.println("Current Status: " + packageEntity.getStatus());
            try {
                System.out.println("-----------------");
//                PackageEntity.PackageStatus status = PackageEntity.PackageStatus.valueOf(newStatus);
//                packageEntity.setStatus(status);
                packageEntity.setStatus(PackageEntity.PackageStatus.valueOf(newStatus));


                packageRepository.save(packageEntity);
                return ResponseEntity.ok("Package status updated successfully");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Invalid package status: " + newStatus);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update package status: " + e.getMessage());
        }
    }

}
