package com.dqops.core.incidents;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentNotificationsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Incident notification configuration loader.
 */
@Component
public class IncidentNotificationsConfigurationLoaderImpl implements IncidentNotificationsConfigurationLoader {

    private final ExecutionContextFactory executionContextFactory;

    /**
     * Creates an incident notification service.
     *
     * @param executionContextFactory                      Execution context factory.
     */
    @Autowired
    public IncidentNotificationsConfigurationLoaderImpl(ExecutionContextFactory executionContextFactory) {
        this.executionContextFactory = executionContextFactory;
    }

    /**
     * Returns a combined list of addresses, combining the default notification channels with the notification settings on a connection.
     * @param incidentGrouping Incident grouping and notification settings from a connection.
     * @param userIdentity User identity that also specifies the data domain where the webhooks are defined.
     * @return Effective notification settings with addresses that could be the default values.
     */
    public IncidentNotificationSpec loadConfiguration(ConnectionIncidentGroupingSpec incidentGrouping,
                                                      UserDomainIdentity userIdentity){
        ExecutionContext executionContext = executionContextFactory.create(userIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        DefaultIncidentNotificationsWrapper defaultIncidentNotificationsWrapper = userHome.getDefaultIncidentNotifications();
        IncidentNotificationSpec defaultIncidentNotifications = defaultIncidentNotificationsWrapper.getSpec();
        if (defaultIncidentNotifications == null) {
            defaultIncidentNotifications = new IncidentNotificationSpec();
        }

        if (incidentGrouping == null || incidentGrouping.getIncidentNotification() == null){
            return defaultIncidentNotifications.deepClone();
        } else {
            return incidentGrouping.getIncidentNotification().combineWithDefaults(defaultIncidentNotifications);
        }
    }
}
