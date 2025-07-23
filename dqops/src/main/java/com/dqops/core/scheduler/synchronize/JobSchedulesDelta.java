/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.synchronize;

import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;

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
