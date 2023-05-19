/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Organization generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`organization`", uniqueConstraints = {
            @UniqueConstraint(name = "`email`", columnNames = {"`email`"}),
            @UniqueConstraint(name = "`phone_no`", columnNames = {"`phone_no`"})})
public class Organization implements Serializable {

    private Integer id;
    private String name;
    private String license;
    private String email;
    private int phoneNo;
    private String password;
    private String approvalStatus;
    private String street;
    private String city;
    private String district;
    private String state;
    private String country;
    private Integer pincode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String profilePic;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`name`", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "`license`", nullable = true, length = 2048)
    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Column(name = "`email`", nullable = false, length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "`phone_no`", nullable = false, scale = 0, precision = 10)
    public int getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Column(name = "`password`", nullable = false, length = 100)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "`approval_status`", nullable = false, length = 20)
    public String getApprovalStatus() {
        return this.approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Column(name = "`street`", nullable = true, length = 100)
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "`city`", nullable = true, length = 100)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "`district`", nullable = true, length = 100)
    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "`state`", nullable = true, length = 100)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "`country`", nullable = true, length = 100)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "`pincode`", nullable = true, scale = 0, precision = 10)
    public Integer getPincode() {
        return this.pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    @Column(name = "`longitude`", nullable = true, scale = 9, precision = 22)
    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Column(name = "`latitude`", nullable = true, scale = 9, precision = 22)
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Column(name = "`profile_pic`", nullable = true, length = 2048)
    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        final Organization organization = (Organization) o;
        return Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}