/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.core.scheduler.quartz;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;

import java.time.LocalDateTime;

/**
 * Service for utilities regarding schedules.
 */
public interface SchedulesUtilityService {
    /**
     * Gets the time of the upcoming execution.
     * @param scheduleSpec Schedule spec for which to get the scheduled time.
     * @return Date and time of the next execution.
     */
    LocalDateTime getTimeOfNextExecution(RecurringScheduleSpec scheduleSpec);
}
