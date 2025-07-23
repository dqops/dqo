/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.models;

import com.dqops.metadata.search.TableSearchFilters;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * Basic model for selecting a fragment of unspecified date-tagged parquet partition data.
 */
@Data
public abstract class ParquetDataFragmentFilter {
    /**
     * Filters for finding the desired table.
     */
    @NotNull
    private TableSearchFilters tableSearchFilters;

    /**
     * Start of the date range.
     */
    @NotNull
    private LocalDate dateStart;

    /**
     * End of the date range.
     */
    @NotNull
    private LocalDate dateEnd;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    public abstract Map<String, String> getColumnConditions();
}
