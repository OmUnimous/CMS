package com.cms.controller;

import com.cms.dao.AccountRepository;
import com.cms.dao.PackageDetailsRepository;
import com.cms.dao.PackageRepository;
import com.cms.entity.Account;
import com.cms.entity.PackageEntity;
import com.cms.entity.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private PackageDetailsRepository packageDetailsRepository;

    @Autowired
    private AccountRepository accountRepository;
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

        try {

            Optional<PackageEntity> optionalPackage = packageRepository.findById(id);
            if (optionalPackage.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            PackageEntity packageEntity = optionalPackage.get();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(newStatus);
                String statusValue = jsonNode.get("newStatus").asText();
                PackageEntity.PackageStatus status = PackageEntity.PackageStatus.valueOf(statusValue);
                if (packageEntity.getStatus() == status) {
                    return ResponseEntity.status(HttpStatus.OK).body("Status is already updated.");
                }
                packageEntity.setStatus(status);
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

    @PostMapping("/allUsers")
    public ResponseEntity<List<Account>> allAccounts(){
        try{
            List<Account> accounts = accountRepository.findAll();
            return ResponseEntity.ok(accounts);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
