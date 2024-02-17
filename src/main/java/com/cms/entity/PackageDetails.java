package com.cms.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "tbl_package_details")
public class PackageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String senderName;
    private String senderAddress;
    private String senderState;
    private int senderPincode;
    @Column(name = "sender_mobile_number", columnDefinition = "BIGINT")
    private long senderMobileNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    private String receiverName;
    private String receiverAddress;
    private String receiverState;
    private int receiverPincode;
    @Column(name = "receiver_mobile_number", columnDefinition = "BIGINT")
    private long receiverMobileNumber;



    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String senderEmail;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderState() {
        return senderState;
    }

    public void setSenderState(String senderState) {
        this.senderState = senderState;
    }

    public int getSenderPincode() {
        return senderPincode;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String receiverEmail;

    public void setSenderPincode(int senderPincode) {
        this.senderPincode = senderPincode;
    }


    public long getSenderMobileNumber() {
        return senderMobileNumber;
    }

    public void setSenderMobileNumber(long senderMobileNumber) {
        this.senderMobileNumber = senderMobileNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public int getReceiverPincode() {
        return receiverPincode;
    }

    public void setReceiverPincode(int receiverPincode) {
        this.receiverPincode = receiverPincode;
    }

    public long getReceiverMobileNumber() {
        return receiverMobileNumber;
    }

    public void setReceiverMobileNumber(long receiverMobileNumber) {
        this.receiverMobileNumber = receiverMobileNumber;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }
}
