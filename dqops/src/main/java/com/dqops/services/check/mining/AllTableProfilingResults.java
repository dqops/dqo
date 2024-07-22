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

package com.dqops.services.check.mining;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data container object that contains profiling and statistics results for the table, and for all its columns.
 * It is used for mining checks based on statistics.
 */
public class AllTableProfilingResults {
    private DataAssetProfilingResults tableProfilingResults = new DataAssetProfilingResults();
    private Map<String, DataAssetProfilingResults> columns = new LinkedHashMap<>();

    /**
     * Returns data profiling results for the table-level statistics and profiling checks.
     * @return Table level profiling results.
     */
    public DataAssetProfilingResults getTableProfilingResults() {
        return this.tableProfilingResults;
    }

    /**
     * Retrieves statistics and recent profiling check results for a given column.
     * @param columnName Column name.
     * @return Column statistics summary or null, when there are no results for that column.
     */
    public DataAssetProfilingResults getColumnProfilingResults(String columnName) {
        return this.columns.get(columnName);
    }
}
