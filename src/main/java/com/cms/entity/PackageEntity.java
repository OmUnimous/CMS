package com.cms.entity;

import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "tbl_package")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tracking_number", unique = true)
    private String trackingNumber = generateTrackingNumber();

    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    @ManyToOne
    private Account account;



    @OneToOne(mappedBy ="packageEntity",cascade =  CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PackageDetails packageDetails;

    public com.cms.entity.PackageDetails getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(com.cms.entity.PackageDetails packageDetails) {
       this.packageDetails = packageDetails;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public PackageEntity() {
        this.trackingNumber = generateTrackingNumber();
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    private String generateTrackingNumber() {
        String prefix = "CMS";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append(prefix);
        // Generate a tracking number of desired length
        for (int i = 0; i < 7; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    public enum PackageStatus {
        BOOKED, PICKED_UP, IN_TRANSIT, DELIVERED
    }
}
