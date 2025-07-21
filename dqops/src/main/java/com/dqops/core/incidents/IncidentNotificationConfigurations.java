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
import com.dqops.data.incidents.models.IncidentModel;
import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Stores a pair of the connection level incident configuration (which takes priority when evaluating notifications) and the default incident notifications.
 */
public class IncidentNotificationConfigurations {
    private IncidentNotificationSpec connectionNotifications;
    private IncidentNotificationSpec globalNotifications;

    /**
     * Creates a DTO object with a pair of two configurations.
     * @param connectionNotifications Connection level configuration.
     * @param globalNotifications Global configuration.
     */
    public IncidentNotificationConfigurations(IncidentNotificationSpec connectionNotifications, IncidentNotificationSpec globalNotifications) {
        this.connectionNotifications = connectionNotifications;
        this.globalNotifications = globalNotifications;
    }

    /**
     * The notification configuration on the connection level.
     * @return Configuration on the connection level.
     */
    public IncidentNotificationSpec getConnectionNotifications() {
        return connectionNotifications;
    }

    /**
     * Configuration on the global level.
     * @return Global level configuration.
     */
    public IncidentNotificationSpec getGlobalNotifications() {
        return globalNotifications;
    }

    /**
     * Verifies whether the incident should be excluded based on the filters from incident notification configuration with the doNotCreateIncidents flag set.
     * Finds the first matching notification.
     * @param message The incident notification message with its details.
     * @return The first matching notification.
     */
    public FilteredNotificationSpec findFirstMatchingNotification(IncidentNotificationMessage message) {
        Collection<FilteredNotificationSpec> connectionNotifications = this.connectionNotifications != null && this.connectionNotifications.getFilteredNotifications() != null ?
                this.connectionNotifications.getFilteredNotifications().values() : new ArrayList<>();
        Collection<FilteredNotificationSpec> globalNotifications = this.globalNotifications != null && this.globalNotifications.getFilteredNotifications() != null ?
                this.globalNotifications.getFilteredNotifications().values() : new ArrayList<>();

        Optional<FilteredNotificationSpec> firstFilteredNotification = Stream.concat(
                        connectionNotifications.stream().sorted(Comparator.comparingInt(n -> n.getPriority())),
                        globalNotifications.stream().sorted(Comparator.comparingInt(n -> n.getPriority())))
                .filter(notification -> !notification.isDisabled() &&
                        notification.getFilter().isMatch(message))
                .findFirst();

        return firstFilteredNotification.orElse(null);
    }

    /**
     * Verifies whether the incident should be excluded based on the filters from incident notification configuration with the doNotCreateIncidents flag set.
     * Finds the first matching notification.
     * @param incidentDetails The incident details with its details.
     * @return The first matching notification.
     */
    public FilteredNotificationSpec findFirstMatchingNotification(IncidentModel incidentDetails) {
        Collection<FilteredNotificationSpec> connectionNotifications = this.connectionNotifications != null && this.connectionNotifications.getFilteredNotifications() != null ?
                this.connectionNotifications.getFilteredNotifications().values() : new ArrayList<>();
        Collection<FilteredNotificationSpec> globalNotifications = this.globalNotifications != null && this.globalNotifications.getFilteredNotifications() != null ?
                this.globalNotifications.getFilteredNotifications().values() : new ArrayList<>();

        Optional<FilteredNotificationSpec> firstFilteredNotification = Stream.concat(
                        connectionNotifications.stream().sorted(Comparator.comparingInt(n -> n.getPriority())),
                        globalNotifications.stream().sorted(Comparator.comparingInt(n -> n.getPriority())))
                .filter(notification -> !notification.isDisabled() &&
                        notification.getFilter().isMatch(incidentDetails))
                .findFirst();

        return firstFilteredNotification.orElse(null);
    }
}
