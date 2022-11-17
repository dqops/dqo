/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.scheduler.scan;

import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;

/**
 * Delta object that contains a difference between the new job schedule list and the current job list (already scheduled).
 * The delta is divided into two objects: new schedules to add to the job scheduler and outdated (unused) job schedules
 * that should be unregistered.
 */
public class JobSchedulesDelta {
    private UniqueSchedulesCollection schedulesToAdd;
    private UniqueSchedulesCollection schedulesToDelete;

    /**
     * Creates a new delta object.
     * @param schedulesToAdd New schedules to add to the scheduler.
     * @param schedulesToDelete Outdated schedules to be removed from the scheduler.
     */
    public JobSchedulesDelta(UniqueSchedulesCollection schedulesToAdd,
                             UniqueSchedulesCollection schedulesToDelete) {
        this.schedulesToAdd = schedulesToAdd;
        this.schedulesToDelete = schedulesToDelete;
    }

    /**
     * Returns the new schedules to add to the scheduler.
     * @return New schedules to add to the scheduler.
     */
    public UniqueSchedulesCollection getSchedulesToAdd() {
        return schedulesToAdd;
    }

    /**
     * Returns the outdated schedules to be removed from the scheduler.
     * @return Outdated schedules to be removed from the scheduler.
     */
    public UniqueSchedulesCollection getSchedulesToDelete() {
        return schedulesToDelete;
    }
}
