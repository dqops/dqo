package ai.dqo.execution.checks.scheduled;

import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.metadata.userhome.UserHome;

/**
 * Service that finds all checks that should be executed for a given schedule.
 * Checks are divided by target tables.
 */
public interface ScheduledTargetChecksFindService {
    /**
     * Traverses the user home and finds all checks that should be executed because their schedule
     * or a schedule of their parent nodes (connection, table, column) matches teh requested schedule.
     *
     * @param userHome User home to find target checks to execute.
     * @param schedule Schedule to match.
     * @return List of target checks, grouped by a target table.
     */
    ScheduledChecksCollection findChecksForSchedule(UserHome userHome, RunChecksSchedule schedule);
}
