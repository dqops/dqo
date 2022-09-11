package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;

/**
 * File synchronization service used by the job scheduler to synchronize the data (after checks were executed) and metadata.
 */
public interface SchedulerFileSynchronizationService {
    /**
     * Synchronizes the whole user home, both the metadata (checks, rules, sensors) and the parquet data files. Should be called in the job that updates the metadata.
     *
     * @param synchronizationReportingMode File system synchronization mode.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    boolean synchronizeAll(FileSystemSynchronizationReportingMode synchronizationReportingMode);

    /**
     * Synchronizes only the data files (parquet files). Should be called in the job that executes the data quality checks.
     *
     * @param synchronizationReportingMode File system synchronization mode.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    boolean synchronizeData(FileSystemSynchronizationReportingMode synchronizationReportingMode);
}
