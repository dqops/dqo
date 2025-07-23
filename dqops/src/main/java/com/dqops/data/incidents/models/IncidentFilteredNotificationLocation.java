/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.incidents.models;

/**
 * Enumeration that says where a filtered notification for an incident is defined. Is it defined on a connection level, or on the global level.
 */
public enum IncidentFilteredNotificationLocation {
    /**
     * The notification filter is configured on a connection.
     */
    connection,

    /**
     * The notification s configured on a global level.
     */
    global
}
