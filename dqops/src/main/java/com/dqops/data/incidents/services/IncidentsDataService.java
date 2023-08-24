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

package com.dqops.data.incidents.services;

import com.dqops.data.checkresults.services.models.CheckResultEntryModel;
import com.dqops.data.checkresults.services.models.CheckResultListFilterParameters;
import com.dqops.data.checkresults.services.models.IncidentHistogramFilterParameters;
import com.dqops.data.checkresults.services.models.IncidentIssueHistogramModel;
import com.dqops.data.incidents.services.models.IncidentListFilterParameters;
import com.dqops.data.incidents.services.models.IncidentModel;
import com.dqops.data.incidents.services.models.IncidentsPerConnectionModel;

import java.util.Collection;

/**
 * Data quality incident management service. Supports reading incidents from parquet tables.
 */
public interface IncidentsDataService {
    /**
     * Loads recent incidents on a connection.
     *
     * @param connectionName   Connection name.
     * @param filterParameters Incident filter parameters.
     * @return Collection of recent incidents.
     */
    Collection<IncidentModel> loadRecentIncidentsOnConnection(
            String connectionName, IncidentListFilterParameters filterParameters);

    /**
     * Loads one incident.
     * @param connectionName Connection name on which the incident was raised.
     * @param year Year when the incident was first seen.
     * @param month Month of year when the incident was first seen.
     * @param incidentId Incident id.
     * @return Incident model when the incident was found or null when the incident is not found.
     */
    IncidentModel loadIncident(String connectionName, int year, int month, String incidentId);

    /**
     * Returns a list of all connections, also counting the number of recent open incidents.
     * @return Collection of connection names, with a count of open incidents.
     */
    Collection<IncidentsPerConnectionModel> findConnectionIncidentStats();

    /**
     * Loads all failed check results covered by a given incident.
     * @param connectionName   Connection name where the incident happened.
     * @param year             Year when the incident was first seen.
     * @param month            Month of year when the incident was first seen.
     * @param incidentId       The incident id.
     * @param filterParameters Filter parameters.
     * @return Array of check results for the incident.
     */
    CheckResultEntryModel[] loadCheckResultsForIncident(String connectionName,
                                                        int year,
                                                        int month,
                                                        String incidentId,
                                                        CheckResultListFilterParameters filterParameters);

    /**
     * Builds a histogram of data quality issue occurrences per day.
     * @param connectionName   Connection name where the incident happened.
     * @param year             Year when the incident was first seen.
     * @param month            Month of year when the incident was first seen.
     * @param incidentId       The incident id.
     * @param filterParameters Optional filter to limit the issues included in the histogram.
     * @return Daily histogram of days when a data quality issue failed.
     */
    IncidentIssueHistogramModel buildDailyIssuesHistogramForIncident(String connectionName,
                                                                     int year,
                                                                     int month,
                                                                     String incidentId,
                                                                     IncidentHistogramFilterParameters filterParameters);
}
