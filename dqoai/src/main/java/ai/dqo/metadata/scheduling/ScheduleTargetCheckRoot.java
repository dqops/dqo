package ai.dqo.metadata.scheduling;

/**
 * Identifies the schedule type (profiling, daily checks, monthly checks, etc) that should be used.
 */
public enum ScheduleTargetCheckRoot {
    /**
     * Schedule for profiling checks.
     */
    profiling,

    /**
     * Schedule for checks that should be executed daily because they capture daily snapshot of a data quality metrics or they are analyzing data for daily periods.
     */
    daily,

    /**
     * Schedule for checks that should be executed monthly because they capture monthly snapshot of a data quality metrics or they are analyzing data for monthly periods.
     */
    monthly;
}
