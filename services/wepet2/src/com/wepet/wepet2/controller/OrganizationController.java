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

import com.wepet.wepet2.Animal;
import com.wepet.wepet2.Complain;
import com.wepet.wepet2.Donation;
import com.wepet.wepet2.Organization;
import com.wepet.wepet2.Volunteer;
import com.wepet.wepet2.service.OrganizationService;


/**
 * Controller object for domain model class Organization.
 * @see Organization
 */
@RestController("wepet2.OrganizationController")
@Api(value = "OrganizationController", description = "Exposes APIs to work with Organization resource.")
@RequestMapping("/wepet2/Organization")
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
	@Qualifier("wepet2.OrganizationService")
	private OrganizationService organizationService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Organization instance.")
    @PostMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Organization createOrganization(@RequestBody Organization organization) {
		LOGGER.debug("Create Organization with information: {}" , organization);

		organization = organizationService.create(organization);
		LOGGER.debug("Created Organization with information: {}" , organization);

	    return organization;
	}

    @ApiOperation(value = "Returns the Organization instance associated with the given id.")
    @GetMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Organization getOrganization(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting Organization with id: {}" , id);

        Organization foundOrganization = organizationService.getById(id);
        LOGGER.debug("Organization details with id: {}" , foundOrganization);

        return foundOrganization;
    }

    @ApiOperation(value = "Updates the Organization instance associated with the given id.")
    @PutMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Organization editOrganization(@PathVariable("id") Integer id, @RequestBody Organization organization) {
        LOGGER.debug("Editing Organization with id: {}" , organization.getId());

        organization.setId(id);
        organization = organizationService.update(organization);
        LOGGER.debug("Organization details with id: {}" , organization);

        return organization;
    }
    
    @ApiOperation(value = "Partially updates the Organization instance associated with the given id.")
    @PatchMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Organization patchOrganization(@PathVariable("id") Integer id, @RequestBody @MapTo(Organization.class) Map<String, Object> organizationPatch) {
        LOGGER.debug("Partially updating Organization with id: {}" , id);

        Organization organization = organizationService.partialUpdate(id, organizationPatch);
        LOGGER.debug("Organization details after partial update: {}" , organization);

        return organization;
    }

    @ApiOperation(value = "Deletes the Organization instance associated with the given id.")
    @DeleteMapping(value = "/{id:.+}")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteOrganization(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting Organization with id: {}" , id);

        Organization deletedOrganization = organizationService.delete(id);

        return deletedOrganization != null;
    }

    @GetMapping(value = "/email/{email}" )
    @ApiOperation(value = "Returns the matching Organization with given unique key values.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Organization getByEmail(@PathVariable("email") String email) {
        LOGGER.debug("Getting Organization with uniques key Email");
        return organizationService.getByEmail(email);
    }

    @GetMapping(value = "/phoneNo/{phoneNo}" )
    @ApiOperation(value = "Returns the matching Organization with given unique key values.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Organization getByPhoneNo(@PathVariable("phoneNo") int phoneNo) {
        LOGGER.debug("Getting Organization with uniques key PhoneNo");
        return organizationService.getByPhoneNo(phoneNo);
    }

    /**
     * @deprecated Use {@link #findOrganizations(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Organization instances matching the search criteria.")
    @PostMapping(value = "/search")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Organization> searchOrganizationsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Organizations list by query filter:{}", (Object) queryFilters);
        return organizationService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Organization instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @GetMapping
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Organization> findOrganizations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Organizations list by filter:", query);
        return organizationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Organization instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @PostMapping(value="/filter", consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Organization> filterOrganizations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Organizations list by filter", query);
        return organizationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @GetMapping(value = "/export/{exportType}", produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportOrganizations(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return organizationService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @PostMapping(value = "/export", consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportOrganizationsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Organization.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> organizationService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Organization instances matching the optional query (q) request param.")
	@GetMapping(value = "/count")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countOrganizations( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Organizations");
		return organizationService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@PostMapping(value = "/aggregations")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getOrganizationAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return organizationService.getAggregatedValues(aggregationInfo, pageable);
    }

    @GetMapping(value="/{id:.+}/animals")
    @ApiOperation(value = "Gets the animals instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Animal> findAssociatedAnimals(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated animals");
        return organizationService.findAssociatedAnimals(id, pageable);
    }

    @GetMapping(value="/{id:.+}/complains")
    @ApiOperation(value = "Gets the complains instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Complain> findAssociatedComplains(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated complains");
        return organizationService.findAssociatedComplains(id, pageable);
    }

    @GetMapping(value="/{id:.+}/donations")
    @ApiOperation(value = "Gets the donations instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Donation> findAssociatedDonations(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated donations");
        return organizationService.findAssociatedDonations(id, pageable);
    }

    @GetMapping(value="/{id:.+}/volunteers")
    @ApiOperation(value = "Gets the volunteers instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Volunteer> findAssociatedVolunteers(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated volunteers");
        return organizationService.findAssociatedVolunteers(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service OrganizationService instance
	 */
	protected void setOrganizationService(OrganizationService service) {
		this.organizationService = service;
	}

}