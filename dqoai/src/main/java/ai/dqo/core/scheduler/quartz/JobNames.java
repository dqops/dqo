package ai.dqo.core.scheduler.quartz;

/**
 * Constants with the default Quartz job names.
 */
public final class JobNames {
    /**
     * Job name to synchronize the metadata.
     */
    public static final String SYNCHRONIZE_METADATA = "SYNCHRONIZE_METADATA";

    /**
     * Job name to periodically runs data quality checks.
     */
    public static final String RUN_CHECKS = "RUN_CHECKS";
}
