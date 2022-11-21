package ai.dqo.core.dqocloud.synchronization;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import ai.dqo.core.jobqueue.BaseDqoQueueJob;

/**
 * DQO queue job that runs synchronization with DQO Cloud in the background for one user home's root folder (sources, sensors, etc.).
 */
public class SynchronizeRootDqoQueueJob extends BaseDqoQueueJob<Void> {
    private DqoRoot rootType;
    private FileSystemSynchronizationListener fileSystemSynchronizationListener;
    private DqoCloudSynchronizationService cloudSynchronizationService;

    /**
     * Creates a synchronization job.
     * @param rootType User home's root folder type to synchronize.
     * @param fileSystemSynchronizationListener File synchronization progress listener. Must be thread save because will be called from another thread (but ony one thread, no concurrent modifications).
     * @param cloudSynchronizationService DQO Cloud synchronization service to use (provided as a dependency).
     */
    public SynchronizeRootDqoQueueJob(
            DqoRoot rootType,
            FileSystemSynchronizationListener fileSystemSynchronizationListener,
            DqoCloudSynchronizationService cloudSynchronizationService) {
        this.rootType = rootType;
        this.fileSystemSynchronizationListener = fileSystemSynchronizationListener;
        this.cloudSynchronizationService = cloudSynchronizationService;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute() {
        this.cloudSynchronizationService.synchronizeFolder(rootType, this.fileSystemSynchronizationListener);
        return null;
    }
}
