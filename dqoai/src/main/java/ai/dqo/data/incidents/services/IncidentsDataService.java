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

package ai.dqo.data.incidents.services;

import ai.dqo.data.incidents.services.models.IncidentListFilterParameters;
import ai.dqo.data.incidents.services.models.IncidentModel;

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
}
