package com.cms.controller;

import com.cms.dao.PackageDetailsRepository;
import com.cms.entity.PackageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class PackageDetailsController {
    @Autowired
    private PackageDetailsRepository packageDetailsRepository;
    @PostMapping("/bookPackageDetails")
    public ResponseEntity<String> bookPackageDetails(@RequestBody PackageDetails packageDetails){
        try{
            if(packageDetails == null){
                return ResponseEntity.badRequest().body("Invalid Package Details");
            }
            PackageDetails savePackageDetails = packageDetailsRepository.save(packageDetails);
            if(savePackageDetails != null){
                return ResponseEntity.ok("Package Deatils Saved Successfully");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to save package details");
            }
            }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Unexpected Error Occured");
        }
    }


}
