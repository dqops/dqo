/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.scheduler.defaults;

import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.userhome.UserHome;

/**
 * Retrieves and creates the default configuration of schedules for new connections.
 */
public interface DefaultSchedulesProvider {
    /**
     * Creates the schedules specification object with the default values of schedules. Returns null when no schedules are configured.
     *
     * @return Schedules configuration with the default schedules (only configured ones) or null when no schedules are configured.
     */
    CronSchedulesSpec createDefaultSchedules();

    /**
     * Creates a new monitoring schedule configuration for a new connection.
     *
     * @return New monitoring schedule configuration for a new connection.
     */
    CronSchedulesSpec createMonitoringSchedulesSpecForNewConnection(UserHome userHome);
}
