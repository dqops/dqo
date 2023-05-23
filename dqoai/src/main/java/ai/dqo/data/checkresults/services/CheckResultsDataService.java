/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.checkresults.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.data.checkresults.services.models.CheckResultDetailedSingleModel;
import ai.dqo.data.checkresults.services.models.CheckResultListFilterParameters;
import ai.dqo.data.checkresults.services.models.CheckResultsDetailedDataModel;
import ai.dqo.data.checkresults.services.models.CheckResultsOverviewDataModel;
import ai.dqo.metadata.sources.PhysicalTableName;

import java.time.Instant;

/**
 * Service that returns data from the check results.
 */
public interface CheckResultsDataService {
    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Overview of the check's recent results.
     */
    CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                CheckResultsOverviewParameters loadParameters);

    /**
     * Retrieves complete model of the results of check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Complete model of the check results.
     */
    CheckResultsDetailedDataModel[] readCheckStatusesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                              CheckResultsDetailedParameters loadParameters);

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
     * @return An array of matching check results.
     */
    CheckResultDetailedSingleModel[] loadCheckResultsRelatedToIncident(String connectionName,
                                                                       PhysicalTableName physicalTableName,
                                                                       long incidentHash,
                                                                       Instant firstSeen,
                                                                       Instant incidentUntil,
                                                                       int minSeverity,
                                                                       CheckResultListFilterParameters filterParameters);
}
