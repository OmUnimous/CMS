package com.cms.controller;

import com.cms.dao.PackageDetailsRepository;
import com.cms.dao.PackageRepository;
import com.cms.dto.PackageCreationRequest;
import com.cms.entity.PackageDetails;
import com.cms.entity.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private PackageDetailsRepository packageDetailsRepository;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody PackageCreationRequest request) {
        try {
            // Basic validation
            if (request == null || request.getPackageEntity() == null || request.getPackageDetails() == null) {
                return ResponseEntity.badRequest().body("Invalid package data");
            }

            // Set package status to BOOKED
            request.getPackageEntity().setStatus(PackageEntity.PackageStatus.BOOKED);


            // Save package and details
            PackageEntity savedPackage = packageRepository.save(request.getPackageEntity());



            if (savedPackage != null) {
                savedPackage.setPackageDetails(request.getPackageDetails());
                PackageDetails savedDetails = packageDetailsRepository.save(request.getPackageDetails());
                if(savedDetails  != null){
                    return ResponseEntity.ok("Package and details created successfully");
                }else{
                    packageRepository.delete(savedPackage);
                    return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to create package Details");
                }

            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to create package and details");
            }
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editPackageDetails(@PathVariable("id") Long id,@RequestBody PackageDetails updatedDetails){
        try {
            // Fetch the existing package details
            Optional<PackageDetails> optionalDetails = packageDetailsRepository.findById(id);
            if (optionalDetails.isPresent()) {
                PackageDetails existingDetails = optionalDetails.get();

                existingDetails.setSenderName(updatedDetails.getSenderName());
                existingDetails.setSenderAddress(updatedDetails.getSenderAddress());
                existingDetails.setSenderState(updatedDetails.getSenderState());
                existingDetails.setSenderPincode(updatedDetails.getSenderPincode());
                existingDetails.setSenderMobileNumber(updatedDetails.getSenderMobileNumber());
                existingDetails.setReceiverName(updatedDetails.getReceiverName());
                existingDetails.setReceiverEmail(updatedDetails.getReceiverEmail());
                existingDetails.setReceiverAddress(updatedDetails.getReceiverAddress());
                existingDetails.setReceiverPincode(updatedDetails.getReceiverPincode());
                existingDetails.setReceiverState(updatedDetails.getReceiverState());
                existingDetails.setReceiverMobileNumber(updatedDetails.getReceiverMobileNumber());

                // Save the updated details
                packageDetailsRepository.save(existingDetails);

                return ResponseEntity.ok("Package details updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while updating package details");
        }
    }
}


