package ai.dqo.core.scheduler.synchronization;

/**
 * File synchronization service used by the job scheduler to synchronize the data (after checks were executed) and metadata.
 */
public interface SchedulerFileSynchronizationService {
    /**
     * Synchronizes the whole user home, both the metadata (checks, rules, sensors) and the parquet data files. Should be called in the job that updates the metadata.
     *
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    boolean synchronizeAll();

    /**
     * Synchronizes only the data files (parquet files). Should be called in the job that executes the data quality checks.
     *
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    boolean synchronizeData();
}
