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
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;

/**
 * Incident notification configuration loader.
 */
public interface IncidentNotificationsConfigurationLoader {

    /**
     * Returns a combined list of addresses, combining the default notification channels with the notification settings on a connection.
     * @param connectionIncidentGrouping Incident grouping and notification settings from a connection.
     * @param userIdentity User identity that also specifies the data domain where the webhooks are defined.
     * @return Effective notification settings with addresses that could be the default values.
     */
    IncidentNotificationConfigurations loadConfiguration(ConnectionIncidentGroupingSpec connectionIncidentGrouping,
                                                         UserDomainIdentity userIdentity);

}
