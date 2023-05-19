/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet2.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.wepet.wepet2.Complain;


/**
 * ServiceImpl object for domain model class Complain.
 *
 * @see Complain
 */
@Service("wepet2.ComplainService")
@Validated
@EntityService(entityClass = Complain.class, serviceId = "wepet2")
public class ComplainServiceImpl implements ComplainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComplainServiceImpl.class);


    @Autowired
    @Qualifier("wepet2.ComplainDao")
    private WMGenericDao<Complain, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Complain, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public Complain create(Complain complain) {
        LOGGER.debug("Creating a new Complain with information: {}", complain);

        Complain complainCreated = this.wmGenericDao.create(complain);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(complainCreated);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Complain getById(Integer complainId) {
        LOGGER.debug("Finding Complain by id: {}", complainId);
        return this.wmGenericDao.findById(complainId);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Complain findById(Integer complainId) {
        LOGGER.debug("Finding Complain by id: {}", complainId);
        try {
            return this.wmGenericDao.findById(complainId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Complain found with id: {}", complainId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public List<Complain> findByMultipleIds(List<Integer> complainIds, boolean orderedReturn) {
        LOGGER.debug("Finding Complains by ids: {}", complainIds);

        return this.wmGenericDao.findByMultipleIds(complainIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "wepet2TransactionManager")
    @Override
    public Complain update(Complain complain) {
        LOGGER.debug("Updating Complain with information: {}", complain);

        this.wmGenericDao.update(complain);
        this.wmGenericDao.refresh(complain);

        return complain;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public Complain partialUpdate(Integer complainId, Map<String, Object>complainPatch) {
        LOGGER.debug("Partially Updating the Complain with id: {}", complainId);

        Complain complain = getById(complainId);

        try {
            ObjectReader complainReader = this.objectMapper.reader().forType(Complain.class).withValueToUpdate(complain);
            complain = complainReader.readValue(this.objectMapper.writeValueAsString(complainPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", complainPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        complain = update(complain);

        return complain;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public Complain delete(Integer complainId) {
        LOGGER.debug("Deleting Complain with id: {}", complainId);
        Complain deleted = this.wmGenericDao.findById(complainId);
        if (deleted == null) {
            LOGGER.debug("No Complain found with id: {}", complainId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Complain.class.getSimpleName(), complainId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public void delete(Complain complain) {
        LOGGER.debug("Deleting Complain with {}", complain);
        this.wmGenericDao.delete(complain);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Complain> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Complains");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<Complain> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Complains");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service wepet2 for table Complain to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service wepet2 for table Complain to {} format", options.getExportType());
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



}