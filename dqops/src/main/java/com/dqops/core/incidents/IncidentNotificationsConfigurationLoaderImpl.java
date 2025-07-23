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
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.id.HierarchyId;
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
     * @param connectionIncidentGrouping Incident grouping and notification settings from a connection.
     * @param userIdentity User identity that also specifies the data domain where the webhooks are defined.
     * @return Effective notification settings with addresses that could be the default values.
     */
    public IncidentNotificationConfigurations loadConfiguration(ConnectionIncidentGroupingSpec connectionIncidentGrouping,
                                                                UserDomainIdentity userIdentity){
        ExecutionContext executionContext = executionContextFactory.create(userIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        DefaultIncidentNotificationsWrapper defaultIncidentNotificationsWrapper = userHome.getDefaultIncidentNotifications();
        IncidentNotificationSpec defaultIncidentNotifications = defaultIncidentNotificationsWrapper.getSpec();
        if (defaultIncidentNotifications == null) {
            defaultIncidentNotifications = new IncidentNotificationSpec();
            defaultIncidentNotifications.setHierarchyId(new HierarchyId(defaultIncidentNotificationsWrapper.getHierarchyId(), "spec"));
        }

        IncidentNotificationSpec connectionNotifications = connectionIncidentGrouping.getIncidentNotification();
        if (connectionNotifications == null) {
            connectionNotifications = new IncidentNotificationSpec();
            ConnectionIncidentGroupingSpec connectionIncidentGroupingClone = connectionIncidentGrouping.deepClone();
            connectionIncidentGroupingClone.setIncidentNotification(connectionNotifications); // this call is done only to get the hierarchy ID, which is required to identify the source of notifications
        }

        connectionNotifications = connectionNotifications.combineWithDefaults(defaultIncidentNotifications); // apply default notification addresses
        return new IncidentNotificationConfigurations(connectionNotifications, defaultIncidentNotifications);
    }
}
