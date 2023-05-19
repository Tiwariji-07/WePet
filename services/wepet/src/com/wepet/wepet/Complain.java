/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Complain generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`complain`")
public class Complain implements Serializable {

    private Integer id;
    private int userId;
    private int pictureId;
    private String description;
    private String serviceType;
    private String status;
    private Integer organizationId;
    private int locationId;
    private User user;
    private Picture picture;
    private Location location;
    private Organization organization;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`user_id`", nullable = false, scale = 0, precision = 10)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "`picture_id`", nullable = false, scale = 0, precision = 10)
    public int getPictureId() {
        return this.pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    @Column(name = "`description`", nullable = false, length = 250)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "`service_type`", nullable = false, length = 100)
    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "`status`", nullable = false, length = 50)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "`organization_id`", nullable = true, scale = 0, precision = 10)
    public Integer getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    @Column(name = "`location_id`", nullable = false, scale = 0, precision = 10)
    public int getLocationId() {
        return this.locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`user_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`complain_ibfk_1`"))
    @Fetch(FetchMode.JOIN)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        if(user != null) {
            this.userId = user.getId();
        }

        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`picture_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`complain_ibfk_2`"))
    @Fetch(FetchMode.JOIN)
    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        if(picture != null) {
            this.pictureId = picture.getId();
        }

        this.picture = picture;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`location_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`complain_ibfk_3`"))
    @Fetch(FetchMode.JOIN)
    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        if(location != null) {
            this.locationId = location.getId();
        }

        this.location = location;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`organization_id`", referencedColumnName = "`id`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`complain_ibfk_4`"))
    @Fetch(FetchMode.JOIN)
    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        if(organization != null) {
            this.organizationId = organization.getId();
        }

        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complain)) return false;
        final Complain complain = (Complain) o;
        return Objects.equals(getId(), complain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}