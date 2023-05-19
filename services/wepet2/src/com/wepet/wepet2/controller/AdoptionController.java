/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet2.controller;

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

import com.wepet.wepet2.Adoption;
import com.wepet.wepet2.service.AdoptionService;


/**
 * Controller object for domain model class Adoption.
 * @see Adoption
 */
@RestController("wepet2.AdoptionController")
@Api(value = "AdoptionController", description = "Exposes APIs to work with Adoption resource.")
@RequestMapping("/wepet2/Adoption")
public class AdoptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdoptionController.class);

    @Autowired
	@Qualifier("wepet2.AdoptionService")
	private AdoptionService adoptionService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Adoption instance.")
    @PostMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Adoption createAdoption(@RequestBody Adoption adoption) {
		LOGGER.debug("Create Adoption with information: {}" , adoption);

		adoption = adoptionService.create(adoption);
		LOGGER.debug("Created Adoption with information: {}" , adoption);

	    return adoption;
	}

    @ApiOperation(value = "Returns the Adoption instance associated with the given id.")
    @GetMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Adoption getAdoption(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting Adoption with id: {}" , id);

        Adoption foundAdoption = adoptionService.getById(id);
        LOGGER.debug("Adoption details with id: {}" , foundAdoption);

        return foundAdoption;
    }

    @ApiOperation(value = "Updates the Adoption instance associated with the given id.")
    @PutMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Adoption editAdoption(@PathVariable("id") Integer id, @RequestBody Adoption adoption) {
        LOGGER.debug("Editing Adoption with id: {}" , adoption.getId());

        adoption.setId(id);
        adoption = adoptionService.update(adoption);
        LOGGER.debug("Adoption details with id: {}" , adoption);

        return adoption;
    }
    
    @ApiOperation(value = "Partially updates the Adoption instance associated with the given id.")
    @PatchMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Adoption patchAdoption(@PathVariable("id") Integer id, @RequestBody @MapTo(Adoption.class) Map<String, Object> adoptionPatch) {
        LOGGER.debug("Partially updating Adoption with id: {}" , id);

        Adoption adoption = adoptionService.partialUpdate(id, adoptionPatch);
        LOGGER.debug("Adoption details after partial update: {}" , adoption);

        return adoption;
    }

    @ApiOperation(value = "Deletes the Adoption instance associated with the given id.")
    @DeleteMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteAdoption(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting Adoption with id: {}" , id);

        Adoption deletedAdoption = adoptionService.delete(id);

        return deletedAdoption != null;
    }

    /**
     * @deprecated Use {@link #findAdoptions(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Adoption instances matching the search criteria.")
    @PostMapping(value = "/search")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Adoption> searchAdoptionsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Adoptions list by query filter:{}", (Object) queryFilters);
        return adoptionService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Adoption instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @GetMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Adoption> findAdoptions(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Adoptions list by filter:", query);
        return adoptionService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Adoption instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @PostMapping(value="/filter", consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Adoption> filterAdoptions(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Adoptions list by filter", query);
        return adoptionService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @GetMapping(value = "/export/{exportType}", produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportAdoptions(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return adoptionService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @PostMapping(value = "/export", consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportAdoptionsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Adoption.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> adoptionService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Adoption instances matching the optional query (q) request param.")
	@GetMapping(value = "/count")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countAdoptions( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Adoptions");
		return adoptionService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@PostMapping(value = "/aggregations")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getAdoptionAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return adoptionService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service AdoptionService instance
	 */
	protected void setAdoptionService(AdoptionService service) {
		this.adoptionService = service;
	}

}