/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
