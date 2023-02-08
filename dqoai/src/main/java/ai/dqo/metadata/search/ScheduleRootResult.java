package ai.dqo.metadata.search;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;

/**
 * Result object returned by the schedule root search operation (to find nodes that have a cron schedule that is applicable to all nodes inside).
 * This object is a pair of the node ({@link ai.dqo.metadata.sources.ConnectionSpec}, {@link ai.dqo.metadata.sources.TableSpec} or {@link ai.dqo.checks.AbstractCheckSpec})
 * and a scheduling group type (profiling, daily, monthly, etc).
 */
public class ScheduleRootResult {
    private CheckRunRecurringScheduleGroup scheduleGroup;
    private AbstractSpec scheduleRootNode;

    /**
     * Creates a schedule root find result.
     * @param scheduleGroup Scheduling group that identifies the type of schedule to use.
     * @param scheduleRootNode Metadata root node ({@link ai.dqo.metadata.sources.ConnectionSpec}, {@link ai.dqo.metadata.sources.TableSpec} or {@link ai.dqo.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public ScheduleRootResult(CheckRunRecurringScheduleGroup scheduleGroup, AbstractSpec scheduleRootNode) {
        this.scheduleGroup = scheduleGroup;
        this.scheduleRootNode = scheduleRootNode;
    }

    /**
     * Returns the scheduling group - the name of the schedule to use and filter the check types.
     * @return Scheduling group.
     */
    public CheckRunRecurringScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    /**
     * Returns the metadata node do be scheduled.
     * @return Metadata root node ({@link ai.dqo.metadata.sources.ConnectionSpec}, {@link ai.dqo.metadata.sources.TableSpec} or {@link ai.dqo.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public AbstractSpec getScheduleRootNode() {
        return scheduleRootNode;
    }
}
