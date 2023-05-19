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
import com.wepet.wepet2.AnimalType;


/**
 * ServiceImpl object for domain model class AnimalType.
 *
 * @see AnimalType
 */
@Service("wepet2.AnimalTypeService")
@Validated
@EntityService(entityClass = AnimalType.class, serviceId = "wepet2")
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalTypeServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("wepet2.AnimalService")
    private AnimalService animalService;

    @Autowired
    @Qualifier("wepet2.AnimalTypeDao")
    private WMGenericDao<AnimalType, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<AnimalType, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public AnimalType create(AnimalType animalType) {
        LOGGER.debug("Creating a new AnimalType with information: {}", animalType);

        AnimalType animalTypeCreated = this.wmGenericDao.create(animalType);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(animalTypeCreated);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public AnimalType getById(Integer animaltypeId) {
        LOGGER.debug("Finding AnimalType by id: {}", animaltypeId);
        return this.wmGenericDao.findById(animaltypeId);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public AnimalType findById(Integer animaltypeId) {
        LOGGER.debug("Finding AnimalType by id: {}", animaltypeId);
        try {
            return this.wmGenericDao.findById(animaltypeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No AnimalType found with id: {}", animaltypeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public List<AnimalType> findByMultipleIds(List<Integer> animaltypeIds, boolean orderedReturn) {
        LOGGER.debug("Finding AnimalTypes by ids: {}", animaltypeIds);

        return this.wmGenericDao.findByMultipleIds(animaltypeIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public AnimalType getByName(String name) {
        Map<String, Object> nameMap = new HashMap<>();
        nameMap.put("name", name);

        LOGGER.debug("Finding AnimalType by unique keys: {}", nameMap);
        return this.wmGenericDao.findByUniqueKey(nameMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "wepet2TransactionManager")
    @Override
    public AnimalType update(AnimalType animalType) {
        LOGGER.debug("Updating AnimalType with information: {}", animalType);

        this.wmGenericDao.update(animalType);
        this.wmGenericDao.refresh(animalType);

        return animalType;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public AnimalType partialUpdate(Integer animaltypeId, Map<String, Object>animalTypePatch) {
        LOGGER.debug("Partially Updating the AnimalType with id: {}", animaltypeId);

        AnimalType animalType = getById(animaltypeId);

        try {
            ObjectReader animalTypeReader = this.objectMapper.reader().forType(AnimalType.class).withValueToUpdate(animalType);
            animalType = animalTypeReader.readValue(this.objectMapper.writeValueAsString(animalTypePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", animalTypePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        animalType = update(animalType);

        return animalType;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public AnimalType delete(Integer animaltypeId) {
        LOGGER.debug("Deleting AnimalType with id: {}", animaltypeId);
        AnimalType deleted = this.wmGenericDao.findById(animaltypeId);
        if (deleted == null) {
            LOGGER.debug("No AnimalType found with id: {}", animaltypeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), AnimalType.class.getSimpleName(), animaltypeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "wepet2TransactionManager")
    @Override
    public void delete(AnimalType animalType) {
        LOGGER.debug("Deleting AnimalType with {}", animalType);
        this.wmGenericDao.delete(animalType);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<AnimalType> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all AnimalTypes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager")
    @Override
    public Page<AnimalType> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all AnimalTypes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service wepet2 for table AnimalType to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "wepet2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service wepet2 for table AnimalType to {} format", options.getExportType());
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
        queryBuilder.append("animalType.id = '" + id + "'");

        return animalService.findAll(queryBuilder.toString(), pageable);
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