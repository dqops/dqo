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
package com.dqops.data.checkresults.services;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.services.models.*;
import com.dqops.data.checkresults.services.models.currentstatus.TableCurrentDataQualityStatusFilterParameters;
import com.dqops.data.checkresults.services.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.metadata.sources.PhysicalTableName;

import java.time.Instant;

/**
 * Service that returns data from the check results.
 */
public interface CheckResultsDataService {
    /**
     * The name of a fake column name used as a placeholder for table level checks.
     */
    String COLUMN_NAME_TABLE_CHECKS_PLACEHOLDER = "(table)";

    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity with the data domain.
     * @return Overview of the check's recent results.
     */
    CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                CheckResultsOverviewParameters loadParameters,
                                                                UserDomainIdentity userDomainIdentity);

    /**
     * Read the results of the most recent table comparison.
     * @param connectionName The connection name of the compared table.
     * @param physicalTableName Physical table name (schema and table) of the compared table.
     * @param checkType Check type.
     * @param timeScale Optional check scale (daily, monthly) for monitoring and partitioned checks.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @param userDomainIdentity User identity with the data domain.
     * @return Returns the summary information about the table comparison.
     */
    TableComparisonResultsModel readMostRecentTableComparisonResults(String connectionName,
                                                                     PhysicalTableName physicalTableName,
                                                                     CheckType checkType,
                                                                     CheckTimeScale timeScale,
                                                                     String tableComparisonConfigurationName,
                                                                     UserDomainIdentity userDomainIdentity);

    /**
     * Retrieves complete model of the results of check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @param userDomainIdentity      User identity with the data domain.
     * @return Complete model of the check results.
     */
    CheckResultsListModel[] readCheckStatusesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                      CheckResultsDetailedFilterParameters loadParameters,
                                                      UserDomainIdentity userDomainIdentity);

    /**
     * Loads the results of failed data quality checks that are attached to the given incident, identified by the incident hash, first seen and incident until timestamps.
     * Returns only check results with a minimum severity.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @param incidentHash Incident hash.
     * @param firstSeen The timestamp when the incident was first seen.
     * @param incidentUntil The timestamp when the incident was closed or expired, returns check results up to this timestamp.
     * @param minSeverity Minimum check issue severity that is returned.
     * @param filterParameters Filter parameters.
     * @param userDomainIdentity User identity with the data domain.
     * @return An array of matching check results.
     */
    CheckResultEntryModel[] loadCheckResultsRelatedToIncident(String connectionName,
                                                              PhysicalTableName physicalTableName,
                                                              long incidentHash,
                                                              Instant firstSeen,
                                                              Instant incidentUntil,
                                                              int minSeverity,
                                                              CheckResultListFilterParameters filterParameters,
                                                              UserDomainIdentity userDomainIdentity);

    /**
     * Builds a histogram of data quality issues for an incident. The histogram returns daily counts of data quality issues,
     * also counting occurrences of data quality issues at various severity levels. The histogram also returns the counts
     * of issues per column and per check name.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Physical table name.
     * @param incidentHash      Incident hash.
     * @param firstSeen         The timestamp when the incident was first seen.
     * @param incidentUntil     The timestamp when the incident was closed or expired, returns check results up to this timestamp.
     * @param minSeverity       Minimum check issue severity that is returned.
     * @param filterParameters  Optional filter to limit the issues included in the histogram.
     * @param userDomainIdentity User identity with the data domain.
     * @return Daily histogram of failed data quality checks.
     */
    IncidentIssueHistogramModel buildDailyIssuesHistogramForIncident(String connectionName,
                                                                     PhysicalTableName physicalTableName,
                                                                     long incidentHash,
                                                                     Instant firstSeen,
                                                                     Instant incidentUntil,
                                                                     int minSeverity,
                                                                     IncidentHistogramFilterParameters filterParameters,
                                                                     UserDomainIdentity userDomainIdentity);

    /**
     * Analyzes the table to find the status of the most recent data quality check for each time series
     * and asses the most current status.
     * @param tableCurrentDataQualityStatusFilterParameters Filter parameters container.
     * @param userDomainIdentity User identity with the data domain.
     * @return The table status.
     */
    TableCurrentDataQualityStatusModel analyzeTableMostRecentQualityStatus(
            TableCurrentDataQualityStatusFilterParameters tableCurrentDataQualityStatusFilterParameters,
            UserDomainIdentity userDomainIdentity);
}
