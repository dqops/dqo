/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.core.scheduler.defaults;

import com.dqops.metadata.scheduling.MonitoringSchedulesSpec;
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
    MonitoringSchedulesSpec createDefaultMonitoringSchedules();

    /**
     * Creates a new monitoring schedule configuration for a new connection.
     *
     * @return New monitoring schedule configuration for a new connection.
     */
    MonitoringSchedulesSpec createMonitoringSchedulesSpecForNewConnection(UserHome userHome);
}
