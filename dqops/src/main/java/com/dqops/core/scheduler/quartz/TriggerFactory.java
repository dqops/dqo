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
package com.dqops.core.scheduler.quartz;

import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * Quartz trigger factory that creates triggers from the schedule configuration.
 */
public interface TriggerFactory {
    /**
     * Creates a Quartz trigger for a given schedule.
     * @param scheduleSpec Schedule specification.
     * @param jobKey Job key to identify a predefined job.
     * @return Trigger.
     */
    Trigger createTrigger(MonitoringScheduleSpec scheduleSpec, JobKey jobKey);
}
