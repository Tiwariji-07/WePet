/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.commons.file.manager.ExportedFileManager;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.wepet.wepet.Animal;
import com.wepet.wepet.AnimalType;
import com.wepet.wepet.service.AnimalTypeService;


/**
 * Controller object for domain model class AnimalType.
 * @see AnimalType
 */
@RestController("wepet.AnimalTypeController")
@Api(value = "AnimalTypeController", description = "Exposes APIs to work with AnimalType resource.")
@RequestMapping("/wepet/AnimalType")
public class AnimalTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalTypeController.class);

    @Autowired
	@Qualifier("wepet.AnimalTypeService")
	private AnimalTypeService animalTypeService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new AnimalType instance.")
    @PostMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AnimalType createAnimalType(@RequestBody AnimalType animalType) {
		LOGGER.debug("Create AnimalType with information: {}" , animalType);

		animalType = animalTypeService.create(animalType);
		LOGGER.debug("Created AnimalType with information: {}" , animalType);

	    return animalType;
	}

    @ApiOperation(value = "Returns the AnimalType instance associated with the given id.")
    @GetMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AnimalType getAnimalType(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting AnimalType with id: {}" , id);

        AnimalType foundAnimalType = animalTypeService.getById(id);
        LOGGER.debug("AnimalType details with id: {}" , foundAnimalType);

        return foundAnimalType;
    }

    @ApiOperation(value = "Updates the AnimalType instance associated with the given id.")
    @PutMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AnimalType editAnimalType(@PathVariable("id") Integer id, @RequestBody AnimalType animalType) {
        LOGGER.debug("Editing AnimalType with id: {}" , animalType.getId());

        animalType.setId(id);
        animalType = animalTypeService.update(animalType);
        LOGGER.debug("AnimalType details with id: {}" , animalType);

        return animalType;
    }
    
    @ApiOperation(value = "Partially updates the AnimalType instance associated with the given id.")
    @PatchMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AnimalType patchAnimalType(@PathVariable("id") Integer id, @RequestBody @MapTo(AnimalType.class) Map<String, Object> animalTypePatch) {
        LOGGER.debug("Partially updating AnimalType with id: {}" , id);

        AnimalType animalType = animalTypeService.partialUpdate(id, animalTypePatch);
        LOGGER.debug("AnimalType details after partial update: {}" , animalType);

        return animalType;
    }

    @ApiOperation(value = "Deletes the AnimalType instance associated with the given id.")
    @DeleteMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteAnimalType(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting AnimalType with id: {}" , id);

        AnimalType deletedAnimalType = animalTypeService.delete(id);

        return deletedAnimalType != null;
    }

    @GetMapping(value = "/name/{name}" )
    @ApiOperation(value = "Returns the matching AnimalType with given unique key values.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AnimalType getByName(@PathVariable("name") String name) {
        LOGGER.debug("Getting AnimalType with uniques key Name");
        return animalTypeService.getByName(name);
    }

    /**
     * @deprecated Use {@link #findAnimalTypes(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of AnimalType instances matching the search criteria.")
    @PostMapping(value = "/search")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<AnimalType> searchAnimalTypesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering AnimalTypes list by query filter:{}", (Object) queryFilters);
        return animalTypeService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of AnimalType instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @GetMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<AnimalType> findAnimalTypes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering AnimalTypes list by filter:", query);
        return animalTypeService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of AnimalType instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @PostMapping(value="/filter", consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<AnimalType> filterAnimalTypes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering AnimalTypes list by filter", query);
        return animalTypeService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @GetMapping(value = "/export/{exportType}", produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportAnimalTypes(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return animalTypeService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @PostMapping(value = "/export", consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportAnimalTypesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = AnimalType.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> animalTypeService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of AnimalType instances matching the optional query (q) request param.")
	@GetMapping(value = "/count")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countAnimalTypes( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting AnimalTypes");
		return animalTypeService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@PostMapping(value = "/aggregations")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getAnimalTypeAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return animalTypeService.getAggregatedValues(aggregationInfo, pageable);
    }

    @GetMapping(value="/{id:.+}/animals")
    @ApiOperation(value = "Gets the animals instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Animal> findAssociatedAnimals(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated animals");
        return animalTypeService.findAssociatedAnimals(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service AnimalTypeService instance
	 */
	protected void setAnimalTypeService(AnimalTypeService service) {
		this.animalTypeService = service;
	}

}