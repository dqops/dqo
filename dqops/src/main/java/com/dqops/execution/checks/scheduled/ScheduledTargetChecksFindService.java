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
package com.dqops.execution.checks.scheduled;

import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.userhome.UserHome;

/**
 * Service that finds all checks that should be executed for a given schedule.
 * Checks are divided by target tables.
 */
public interface ScheduledTargetChecksFindService {
    /**
     * Traverses the user home and finds all checks that should be executed because their schedule
     * or a schedule of their parent nodes (connection, table, column) matches the requested schedule.
     *
     * @param userHome User home to find target checks to execute.
     * @param schedule Schedule to match.
     * @return List of target checks, grouped by a target table.
     */
    ScheduledChecksCollection findChecksForSchedule(UserHome userHome, MonitoringScheduleSpec schedule);
}
