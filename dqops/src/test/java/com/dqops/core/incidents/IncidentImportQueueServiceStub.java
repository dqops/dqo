/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
