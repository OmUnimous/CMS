package com.cms.controller;

import ch.qos.logback.core.CoreConstants;
import com.cms.dao.AccountRepository;
import com.cms.dao.PackageDetailsRepository;
import com.cms.dao.PackageRepository;
import com.cms.dto.PackageCreationRequest;
import com.cms.entity.Account;
import com.cms.entity.PackageDetails;
import com.cms.entity.PackageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private PackageDetailsRepository packageDetailsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody PackageCreationRequest request) {
        try {
            // Basic validation
            if (request == null || request.getPackageEntity() == null || request.getPackageDetails() == null) {
                return ResponseEntity.badRequest().body("Invalid package data");
            } else {
                // Set package status to BOOKED
                request.getPackageEntity().setStatus(PackageEntity.PackageStatus.BOOKED);

                long accountId = request.getAccountId();
                System.out.println("Account " + accountId);
                Optional<Account> optionalAccount = accountRepository.findById(accountId);
                System.out.println("Account " + optionalAccount);
                if (optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();

                     //Set account in PackageEntity
                    request.getPackageEntity().setAccount(account);

                    // Associate PackageEntity with PackageDetails
                    PackageEntity packageEntity = request.getPackageEntity();
                    PackageDetails packageDetails = request.getPackageDetails();

                    packageEntity.setPackageDetails(packageDetails);
                    packageDetails.setPackageEntity(packageEntity);

                    // Save package and details together
                    packageRepository.save(packageEntity);

                    return ResponseEntity.ok("Package and details created successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
                }
            }
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editPackageDetails(@PathVariable("id") Long id,@RequestBody PackageDetails updatedDetails){

        try {
            if(id == null || updatedDetails == null){
                return ResponseEntity.badRequest().body("Invalid Input Data");
            }
            // Fetch the existing package details
            Optional<PackageDetails> optionalDetails = packageDetailsRepository.findById(id);
            if (optionalDetails.isPresent()) {
                PackageDetails existingDetails = optionalDetails.get();
                existingDetails.setSenderName(updatedDetails.getSenderName());
                existingDetails.setSenderAddress(updatedDetails.getSenderAddress());
                existingDetails.setSenderState(updatedDetails.getSenderState());
                existingDetails.setSenderPincode(updatedDetails.getSenderPincode());
                existingDetails.setSenderEmail(updatedDetails.getSenderEmail());
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

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        try {
            if(id == null){
                return ResponseEntity.badRequest().body("Invalid Input Data");
            }
            System.out.println(id);
            Optional<PackageEntity> deletePackage = packageRepository.findById(id);
            packageRepository.deleteById(id);
            if(deletePackage.isPresent()){
                PackageEntity packageEntity = deletePackage.get();
                if(packageEntity.getPackageDetails() != null){
                    packageEntity.getPackageDetails().setPackageEntity(null);
                    packageDetailsRepository.delete(packageEntity.getPackageDetails());
                }
                packageRepository.deleteById(id);
                return ResponseEntity.ok("Package Deleted Successfully");
            }
            return ResponseEntity.ok("Package deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while deleting the package");
        }
    }

    @GetMapping("/packages/{accountId}")
    public ResponseEntity<List<PackageEntity>> getPackagesByAccountId(@PathVariable Long accountId) {
        try {
            List<PackageEntity> packages = packageRepository.findAllByAccountId(accountId);
            if (packages.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(packages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}



