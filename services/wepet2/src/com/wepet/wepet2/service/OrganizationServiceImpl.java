/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet2.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;

import com.wepet.wepet2.Animal;
import com.wepet.wepet2.Complain;
import com.wepet.wepet2.Donation;
import com.wepet.wepet2.Organization;
import com.wepet.wepet2.Volunteer;


/**
 * ServiceImpl object for domain model class Organization.
 *
 * @see Organization
 */
@Service("wepet2.OrganizationService")
@Validated
@EntityService(entityClass = Organization.class, serviceId = "wepet2")
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("wepet2.VolunteerService")
    private VolunteerService volunteerService;

    @Lazy
    @Autowired
    @Qualifier("wepet2.DonationService")
    private DonationService donationService;

    @Lazy
    @Autowired
    @Qualifier("wepet2.ComplainService")
    private ComplainService complainService;

    @Lazy
    @Autowired
    @Qualifier("wepet2.AnimalService")
    private AnimalService animalService;

    @Autowired
    @Qualifier("wepet2.OrganizationDao")
    private WMGenericDao<Organization, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Organization, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public Organization create(Organization organization) {
        LOGGER.debug("Creating a new Organization with information: {}", organization);

        Organization organizationCreated = this.wmGenericDao.create(organization);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(organizationCreated);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Organization getById(Integer organizationId) {
        LOGGER.debug("Finding Organization by id: {}", organizationId);
        return this.wmGenericDao.findById(organizationId);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Organization findById(Integer organizationId) {
        LOGGER.debug("Finding Organization by id: {}", organizationId);
        try {
            return this.wmGenericDao.findById(organizationId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Organization found with id: {}", organizationId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public List<Organization> findByMultipleIds(List<Integer> organizationIds, boolean orderedReturn) {
        LOGGER.debug("Finding Organizations by ids: {}", organizationIds);

        return this.wmGenericDao.findByMultipleIds(organizationIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Organization getByEmail(String email) {
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("email", email);

        LOGGER.debug("Finding Organization by unique keys: {}", emailMap);
        return this.wmGenericDao.findByUniqueKey(emailMap);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Organization getByPhoneNo(int phoneNo) {
        Map<String, Object> phoneNoMap = new HashMap<>();
        phoneNoMap.put("phoneNo", phoneNo);

        LOGGER.debug("Finding Organization by unique keys: {}", phoneNoMap);
        return this.wmGenericDao.findByUniqueKey(phoneNoMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "wepet2TransactionManager")
    @Override
    public Organization update(Organization organization) {
        LOGGER.debug("Updating Organization with information: {}", organization);

        this.wmGenericDao.update(organization);
        this.wmGenericDao.refresh(organization);

        return organization;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public Organization partialUpdate(Integer organizationId, Map<String, Object>organizationPatch) {
        LOGGER.debug("Partially Updating the Organization with id: {}", organizationId);

        Organization organization = getById(organizationId);

        try {
            ObjectReader organizationReader = this.objectMapper.reader().forType(Organization.class).withValueToUpdate(organization);
            organization = organizationReader.readValue(this.objectMapper.writeValueAsString(organizationPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", organizationPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        organization = update(organization);

        return organization;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public Organization delete(Integer organizationId) {
        LOGGER.debug("Deleting Organization with id: {}", organizationId);
        Organization deleted = this.wmGenericDao.findById(organizationId);
        if (deleted == null) {
            LOGGER.debug("No Organization found with id: {}", organizationId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Organization.class.getSimpleName(), organizationId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public void delete(Organization organization) {
        LOGGER.debug("Deleting Organization with {}", organization);
        this.wmGenericDao.delete(organization);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Organization> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Organizations");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Organization> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Organizations");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service wepet2 for table Organization to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service wepet2 for table Organization to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Animal> findAssociatedAnimals(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated animals");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("organization.id = '" + id + "'");

        return animalService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Complain> findAssociatedComplains(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated complains");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("organization.id = '" + id + "'");

        return complainService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Donation> findAssociatedDonations(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated donations");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("organization.id = '" + id + "'");

        return donationService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Volunteer> findAssociatedVolunteers(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated volunteers");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("organization.id = '" + id + "'");

        return volunteerService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service VolunteerService instance
     */
    protected void setVolunteerService(VolunteerService service) {
        this.volunteerService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service DonationService instance
     */
    protected void setDonationService(DonationService service) {
        this.donationService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service ComplainService instance
     */
    protected void setComplainService(ComplainService service) {
        this.complainService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service AnimalService instance
     */
    protected void setAnimalService(AnimalService service) {
        this.animalService = service;
    }

}