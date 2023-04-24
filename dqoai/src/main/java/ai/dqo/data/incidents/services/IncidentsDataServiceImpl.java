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

import ai.dqo.core.incidents.IncidentImportQueueService;
import ai.dqo.core.incidents.IncidentNotificationMessage;
import ai.dqo.core.incidents.IncidentNotificationService;
import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.data.incidents.snapshot.IncidentsSnapshot;
import ai.dqo.data.incidents.snapshot.IncidentsSnapshotFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Data quality incident management service. Supports changing the statuses of incidents.
 */
@Service
public class IncidentsDataServiceImpl {
    private IncidentsSnapshotFactory incidentsSnapshotFactory;
    private IncidentImportQueueService incidentImportQueueService;
    private IncidentNotificationService incidentNotificationService;

    /**
     * Creates a new incident data service, given all required dependencies.
     * @param incidentsSnapshotFactory Incident snapshot factory.
     * @param incidentImportQueueService Incident import queue service. Queues all update operations on the incident table.
     * @param incidentNotificationService Incident notification service.
     */
    @Autowired
    public IncidentsDataServiceImpl(IncidentsSnapshotFactory incidentsSnapshotFactory,
                                    IncidentImportQueueService incidentImportQueueService,
                                    IncidentNotificationService incidentNotificationService) {
        this.incidentsSnapshotFactory = incidentsSnapshotFactory;
        this.incidentImportQueueService = incidentImportQueueService;
        this.incidentNotificationService = incidentNotificationService;
    }

    /**
     * Sets a new incident status on an incident.
     * @param connectionName Connection name.
     * @param firstSeenYear The year (four digit) when the incident was first seen.
     * @param firstSeenMonth The month (1-12) when the incident was first seen.
     * @param incidentId Incident id.
     * @param newIncidentStatus New incident status.
     */
    public void setIncidentStatus(String connectionName,
                                  int firstSeenYear,
                                  int firstSeenMonth,
                                  String incidentId,
                                  IncidentStatus newIncidentStatus) {

    }
}
