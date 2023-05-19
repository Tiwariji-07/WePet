/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.wepet.wepet2.service;

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

import com.wepet.wepet2.Login;

/**
 * Service object for domain model class {@link Login}.
 */
public interface LoginService {

    /**
     * Creates a new Login. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Login if any.
     *
     * @param login Details of the Login to be created; value cannot be null.
     * @return The newly created Login.
     */
    Login create(@Valid Login login);


	/**
     * Returns Login by given id if exists.
     *
     * @param loginId The id of the Login to get; value cannot be null.
     * @return Login associated with the given loginId.
	 * @throws EntityNotFoundException If no Login is found.
     */
    Login getById(Integer loginId);

    /**
     * Find and return the Login by given id if exists, returns null otherwise.
     *
     * @param loginId The id of the Login to get; value cannot be null.
     * @return Login associated with the given loginId.
     */
    Login findById(Integer loginId);

	/**
     * Find and return the list of Logins by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param loginIds The id's of the Login to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return Logins associated with the given loginIds.
     */
    List<Login> findByMultipleIds(List<Integer> loginIds, boolean orderedReturn);


    /**
     * Updates the details of an existing Login. It replaces all fields of the existing Login with the given login.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Login if any.
     *
     * @param login The details of the Login to be updated; value cannot be null.
     * @return The updated Login.
     * @throws EntityNotFoundException if no Login is found with given input.
     */
    Login update(@Valid Login login);


    /**
     * Partially updates the details of an existing Login. It updates only the
     * fields of the existing Login which are passed in the loginPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Login if any.
     *
     * @param loginId The id of the Login to be deleted; value cannot be null.
     * @param loginPatch The partial data of Login which is supposed to be updated; value cannot be null.
     * @return The updated Login.
     * @throws EntityNotFoundException if no Login is found with given input.
     */
    Login partialUpdate(Integer loginId, Map<String, Object> loginPatch);

    /**
     * Deletes an existing Login with the given id.
     *
     * @param loginId The id of the Login to be deleted; value cannot be null.
     * @return The deleted Login.
     * @throws EntityNotFoundException if no Login found with the given id.
     */
    Login delete(Integer loginId);

    /**
     * Deletes an existing Login with the given object.
     *
     * @param login The instance of the Login to be deleted; value cannot be null.
     */
    void delete(Login login);

    /**
     * Find all Logins matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Logins.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<Login> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all Logins matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Logins.
     *
     * @see Pageable
     * @see Page
     */
    Page<Login> findAll(String query, Pageable pageable);

    /**
     * Exports all Logins matching the given input query to the given exportType format.
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
     * Exports all Logins matching the given input query to the given exportType format.
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
     * Retrieve the count of the Logins in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the Login.
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