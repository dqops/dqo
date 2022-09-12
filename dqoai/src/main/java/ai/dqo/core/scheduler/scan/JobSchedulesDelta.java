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
