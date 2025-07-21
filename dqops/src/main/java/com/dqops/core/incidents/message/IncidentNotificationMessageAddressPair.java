/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.incidents.message;

/**
 * A pair of an IncidentNotificationMessage and a notification address that is sent as a notification.
 */
public class IncidentNotificationMessageAddressPair {
    private final IncidentNotificationMessage incidentNotificationMessage;
    private final String notificationAddress;

    /**
     * Creates an incident notification info with the target address.
     * @param incidentNotificationMessage Notification message.
     * @param notificationAddress Notification address.
     */
    public IncidentNotificationMessageAddressPair(IncidentNotificationMessage incidentNotificationMessage, String notificationAddress) {
        this.incidentNotificationMessage = incidentNotificationMessage;
        this.notificationAddress = notificationAddress;
    }

    /**
     * Returns the notification message object.
     * @return Notification message.
     */
    public IncidentNotificationMessage getIncidentNotificationMessage() {
        return incidentNotificationMessage;
    }

    /**
     * Target notification address.
     * @return Notification address.
     */
    public String getNotificationAddress() {
        return notificationAddress;
    }

    @Override
    public String toString() {
        return "MessageAddressPair{" +
                "incidentNotificationMessage=" + incidentNotificationMessage +
                ", notificationAddress='" + notificationAddress + '\'' +
                '}';
    }
}
