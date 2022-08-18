package ai.dqo.core.scheduler.quartz;

import org.quartz.JobKey;

/**
 * Predefined Quartz job keys for built-in jobs (synchronization, running checks).
 */
public final class JobKeys {
    /**
     * Predefined job that synchronizes the metadata.
     */
    public static final JobKey SYNCHRONIZE_METADATA = new JobKey(JobNames.SYNCHRONIZE_METADATA, null);

    /**
     * Predefined job that runs the data quality checks.
     */
    public static final JobKey RUN_CHECKS = new JobKey(JobNames.RUN_CHECKS, null);
}
