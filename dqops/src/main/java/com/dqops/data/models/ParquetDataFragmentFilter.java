/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
