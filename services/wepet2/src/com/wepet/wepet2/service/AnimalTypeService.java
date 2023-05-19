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

import com.wepet.wepet2.Animal;
import com.wepet.wepet2.AnimalType;

/**
 * Service object for domain model class {@link AnimalType}.
 */
public interface AnimalTypeService {

    /**
     * Creates a new AnimalType. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on AnimalType if any.
     *
     * @param animalType Details of the AnimalType to be created; value cannot be null.
     * @return The newly created AnimalType.
     */
    AnimalType create(@Valid AnimalType animalType);


	/**
     * Returns AnimalType by given id if exists.
     *
     * @param animaltypeId The id of the AnimalType to get; value cannot be null.
     * @return AnimalType associated with the given animaltypeId.
	 * @throws EntityNotFoundException If no AnimalType is found.
     */
    AnimalType getById(Integer animaltypeId);

    /**
     * Find and return the AnimalType by given id if exists, returns null otherwise.
     *
     * @param animaltypeId The id of the AnimalType to get; value cannot be null.
     * @return AnimalType associated with the given animaltypeId.
     */
    AnimalType findById(Integer animaltypeId);

	/**
     * Find and return the list of AnimalTypes by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param animaltypeIds The id's of the AnimalType to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return AnimalTypes associated with the given animaltypeIds.
     */
    List<AnimalType> findByMultipleIds(List<Integer> animaltypeIds, boolean orderedReturn);

    /**
     * Find and return the AnimalType for given name  if exists.
     *
     * @param name value of name; value cannot be null.
     * @return AnimalType associated with the given inputs.
     * @throws EntityNotFoundException if no matching AnimalType found.
     */
    AnimalType getByName(String name);

    /**
     * Updates the details of an existing AnimalType. It replaces all fields of the existing AnimalType with the given animalType.
     *
     * This method overrides the input field values using Server side or database managed properties defined on AnimalType if any.
     *
     * @param animalType The details of the AnimalType to be updated; value cannot be null.
     * @return The updated AnimalType.
     * @throws EntityNotFoundException if no AnimalType is found with given input.
     */
    AnimalType update(@Valid AnimalType animalType);


    /**
     * Partially updates the details of an existing AnimalType. It updates only the
     * fields of the existing AnimalType which are passed in the animalTypePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on AnimalType if any.
     *
     * @param animaltypeId The id of the AnimalType to be deleted; value cannot be null.
     * @param animalTypePatch The partial data of AnimalType which is supposed to be updated; value cannot be null.
     * @return The updated AnimalType.
     * @throws EntityNotFoundException if no AnimalType is found with given input.
     */
    AnimalType partialUpdate(Integer animaltypeId, Map<String, Object> animalTypePatch);

    /**
     * Deletes an existing AnimalType with the given id.
     *
     * @param animaltypeId The id of the AnimalType to be deleted; value cannot be null.
     * @return The deleted AnimalType.
     * @throws EntityNotFoundException if no AnimalType found with the given id.
     */
    AnimalType delete(Integer animaltypeId);

    /**
     * Deletes an existing AnimalType with the given object.
     *
     * @param animalType The instance of the AnimalType to be deleted; value cannot be null.
     */
    void delete(AnimalType animalType);

    /**
     * Find all AnimalTypes matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching AnimalTypes.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<AnimalType> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all AnimalTypes matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching AnimalTypes.
     *
     * @see Pageable
     * @see Page
     */
    Page<AnimalType> findAll(String query, Pageable pageable);

    /**
     * Exports all AnimalTypes matching the given input query to the given exportType format.
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
     * Exports all AnimalTypes matching the given input query to the given exportType format.
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
     * Retrieve the count of the AnimalTypes in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the AnimalType.
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

    /*
     * Returns the associated animals for given AnimalType id.
     *
     * @param id value of id; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated Animal instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<Animal> findAssociatedAnimals(Integer id, Pageable pageable);

}