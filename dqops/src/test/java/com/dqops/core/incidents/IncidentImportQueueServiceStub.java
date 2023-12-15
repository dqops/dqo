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

package com.dqops.core.incidents;

import com.dqops.core.principal.UserDomainIdentity;

/**
 * Stubbed (fake) incident import queue service.
 */
public class IncidentImportQueueServiceStub implements IncidentImportQueueService {

    /**
     * Imports incidents detected on a single table to a connection level incidents table.
     *
     * @param tableIncidentImportBatch Issues (failed data quality check results) detected on a single table that should be loaded to the incidents table.
     */
    @Override
    public void importTableIncidents(TableIncidentImportBatch tableIncidentImportBatch,
                                     UserDomainIdentity userDomainIdentity) {

    }

    /**
     * Sets a new incident status on an incident.
     *
     * @param incidentStatusChangeParameters Parameters of the incident whose status will be updated.
     */
    @Override
    public void setIncidentStatus(IncidentStatusChangeParameters incidentStatusChangeParameters,
                                  UserDomainIdentity userDomainIdentity) {

    }

    /**
     * Sets a new incident issueUrl on an incident.
     *
     * @param incidentIssueUrlChangeParameters Parameters of the incident whose issueUrl will be updated.
     */
    @Override
    public void setIncidentIssueUrl(IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters,
                                    UserDomainIdentity userDomainIdentity) {

    }
}
