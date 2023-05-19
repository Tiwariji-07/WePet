/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;

import com.wepet.wepet.Adoption;

/**
 * Service object for domain model class {@link Adoption}.
 */
public interface AdoptionService {

    /**
     * Creates a new Adoption. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Adoption if any.
     *
     * @param adoption Details of the Adoption to be created; value cannot be null.
     * @return The newly created Adoption.
     */
    Adoption create(@Valid Adoption adoption);


	/**
     * Returns Adoption by given id if exists.
     *
     * @param adoptionId The id of the Adoption to get; value cannot be null.
     * @return Adoption associated with the given adoptionId.
	 * @throws EntityNotFoundException If no Adoption is found.
     */
    Adoption getById(Integer adoptionId);

    /**
     * Find and return the Adoption by given id if exists, returns null otherwise.
     *
     * @param adoptionId The id of the Adoption to get; value cannot be null.
     * @return Adoption associated with the given adoptionId.
     */
    Adoption findById(Integer adoptionId);

	/**
     * Find and return the list of Adoptions by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param adoptionIds The id's of the Adoption to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return Adoptions associated with the given adoptionIds.
     */
    List<Adoption> findByMultipleIds(List<Integer> adoptionIds, boolean orderedReturn);


    /**
     * Updates the details of an existing Adoption. It replaces all fields of the existing Adoption with the given adoption.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Adoption if any.
     *
     * @param adoption The details of the Adoption to be updated; value cannot be null.
     * @return The updated Adoption.
     * @throws EntityNotFoundException if no Adoption is found with given input.
     */
    Adoption update(@Valid Adoption adoption);


    /**
     * Partially updates the details of an existing Adoption. It updates only the
     * fields of the existing Adoption which are passed in the adoptionPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Adoption if any.
     *
     * @param adoptionId The id of the Adoption to be deleted; value cannot be null.
     * @param adoptionPatch The partial data of Adoption which is supposed to be updated; value cannot be null.
     * @return The updated Adoption.
     * @throws EntityNotFoundException if no Adoption is found with given input.
     */
    Adoption partialUpdate(Integer adoptionId, Map<String, Object> adoptionPatch);

    /**
     * Deletes an existing Adoption with the given id.
     *
     * @param adoptionId The id of the Adoption to be deleted; value cannot be null.
     * @return The deleted Adoption.
     * @throws EntityNotFoundException if no Adoption found with the given id.
     */
    Adoption delete(Integer adoptionId);

    /**
     * Deletes an existing Adoption with the given object.
     *
     * @param adoption The instance of the Adoption to be deleted; value cannot be null.
     */
    void delete(Adoption adoption);

    /**
     * Find all Adoptions matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Adoptions.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<Adoption> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all Adoptions matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Adoptions.
     *
     * @see Pageable
     * @see Page
     */
    Page<Adoption> findAll(String query, Pageable pageable);

    /**
     * Exports all Adoptions matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all Adoptions matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the Adoptions in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the Adoption.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}