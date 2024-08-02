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
     * @param incidentGrouping Incident grouping and notification settings from a connection.
     * @param userIdentity User identity that also specifies the data domain where the webhooks are defined.
     * @return Effective notification settings with addresses that could be the default values.
     */
    IncidentNotificationSpec loadConfiguration(ConnectionIncidentGroupingSpec incidentGrouping,
                                               UserDomainIdentity userIdentity);

}
