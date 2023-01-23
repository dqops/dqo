/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.data.profilingresults.services;

import ai.dqo.data.profilingresults.services.models.ProfilerResultsForColumnModel;
import ai.dqo.data.profilingresults.services.models.ProfilerResultsForTableModel;
import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Service that provides access to read requested data profiling results.
 */
public interface ProfilerDataService {
    /**
     * Retrieves the most recent table profiler results for a given table.
     * @param connectionName Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param dataStreamName Data stream name.
     * @return Profiler results for the given table.
     */
    ProfilerResultsForTableModel getMostRecentProfilerMetricsForTable(String connectionName,
                                                                      PhysicalTableName physicalTableName,
                                                                      String dataStreamName);

    /**
     * Retrieves the most recent table profiler results for a given column.
     * @param connectionName    Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param columName         Column name.
     * @param dataStreamName    Data stream name.
     * @return Profiler results for the given table.
     */
    ProfilerResultsForColumnModel getMostRecentProfilerMetricsForColumn(String connectionName,
                                                                        PhysicalTableName physicalTableName,
                                                                        String columName,
                                                                        String dataStreamName);
}
