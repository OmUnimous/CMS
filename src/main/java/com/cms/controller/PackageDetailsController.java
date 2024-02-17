package com.cms.controller;

import com.cms.dao.PackageDetailsRepository;
import com.cms.entity.PackageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class PackageDetailsController {
    @Autowired
    private PackageDetailsRepository packageDetailsRepository;

    @GetMapping("/details/{id}")
    public ResponseEntity<PackageDetails> getPackageDetails(@PathVariable Long id) {
        try {
            Optional<PackageDetails> packageDetails = packageDetailsRepository.findById(id);
            if (packageDetails.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(packageDetails.get());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
