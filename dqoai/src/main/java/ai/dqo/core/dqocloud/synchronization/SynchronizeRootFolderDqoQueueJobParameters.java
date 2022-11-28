package ai.dqo.core.dqocloud.synchronization;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Parameters object for a job that synchronizes one folder with DQO Cloud.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SynchronizeRootFolderDqoQueueJobParameters {
    private DqoRoot rootType;
    @JsonIgnore
    private FileSystemSynchronizationListener fileSystemSynchronizationListener;

    /**
     * Creates a new parameters object for a synchronize folder job.
     * @param rootType User home's root folder type to synchronize.
     * @param fileSystemSynchronizationListener File synchronization progress listener. Must be thread save because will be called from another thread (but ony one thread, no concurrent modifications).
     */
    public SynchronizeRootFolderDqoQueueJobParameters(DqoRoot rootType,
                                                      FileSystemSynchronizationListener fileSystemSynchronizationListener) {
        this.rootType = rootType;
        this.fileSystemSynchronizationListener = fileSystemSynchronizationListener;
    }

    /**
     * Returns the user home folder type to synchronize.
     * @return User home folder type.
     */
    public DqoRoot getRootType() {
        return rootType;
    }

    /**
     * Returns the file system synchronization listener that will receive progress messages during the synchronization process.
     * @return Synchronization listener.
     */
    public FileSystemSynchronizationListener getFileSystemSynchronizationListener() {
        return fileSystemSynchronizationListener;
    }
}
