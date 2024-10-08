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
package com.dqops.data.statistics.services;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.statistics.models.StatisticsResultsForColumnModel;
import com.dqops.data.statistics.models.StatisticsResultsForTableModel;
import com.dqops.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Service that provides access to read requested data statistics results.
 */
public interface StatisticsDataService {
    /**
     * Retrieves the most recent table statistics results for a given table.
     * @param connectionName Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param dataGroup Data group name.
     * @param includeColumnLevelStatistics True when column level statistics should also be included.
     * @param userDomainIdentity User identity with the data domain.
     * @return Statistics results for the given table.
     */
    StatisticsResultsForTableModel getMostRecentStatisticsForTable(String connectionName,
                                                                   PhysicalTableName physicalTableName,
                                                                   String dataGroup,
                                                                   boolean includeColumnLevelStatistics,
                                                                   UserDomainIdentity userDomainIdentity);

    /**
     * Retrieves the most recent table statistics results for a given column.
     * @param connectionName    Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param columName         Column name.
     * @param dataGroup         Data group name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Statistics results for the given table.
     */
    StatisticsResultsForColumnModel getMostRecentStatisticsForColumn(String connectionName,
                                                                     PhysicalTableName physicalTableName,
                                                                     String columName,
                                                                     String dataGroup,
                                                                     UserDomainIdentity userDomainIdentity);

    /**
     * Checks if there are any recent partition files with the results of basic statistics for the given table.
     * This operation is used to propose the user to collect statistics.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Not null date of the most recent statistics results within a reasonable period. Null when recent statistics are not present.
     */
    LocalDate getMostRecentStatisticsPartitionMonth(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity);

    /**
     * Returns the last modification timestamp of the most recent partition with statistics.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Not null timestamp when statistics are present - returns the file modification timestamp.
     */
    Instant getStatisticsLastModified(String connectionName, PhysicalTableName physicalTableName, UserDomainIdentity userDomainIdentity);

    /**
     * Parses and filters results from statistics and returns a statistics model.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param dataGroup Data group name.
     * @param includeColumnLevelStatistics True when column level statistics should be included.
     * @param statisticsDataTable Dataset (table) with statistics to parse.
     * @param targetStatisticsResultsModel Target model to import additional results.
     * @return Target model returned.
     */
    StatisticsResultsForTableModel parseStatisticsResults(String connectionName,
                                                          PhysicalTableName physicalTableName,
                                                          String dataGroup,
                                                          boolean includeColumnLevelStatistics,
                                                          Table statisticsDataTable,
                                                          StatisticsResultsForTableModel targetStatisticsResultsModel);
}
