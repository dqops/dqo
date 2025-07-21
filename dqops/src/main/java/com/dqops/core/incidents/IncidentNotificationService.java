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

import com.dqops.core.incidents.message.IncidentNotificationMessage;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;

import java.util.List;

/**
 * Incident notification service that sends notifications when new data quality incidents are detected.
 */
public interface IncidentNotificationService {
    /**
     * Sends new incident notifications to the notification target addresses specified in the incident grouping configuration.
     * @param newMessages List of new data quality incidents that will be sent as a payload.
     * @param incidentGrouping Incident grouping that identifies the notification target (where to send the notifications).
     * @param userIdentity     User identity that specifies the data domain where the addresses are defined.
     */
    void sendNotifications(List<IncidentNotificationMessage> newMessages,
                           ConnectionIncidentGroupingSpec incidentGrouping,
                           UserDomainIdentity userIdentity);
}
