package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.filesystem.synchronization.BaseFileSystemSynchronizationListener;
import org.springframework.stereotype.Component;

/**
 * Synchronization listener used by the scheduler when synchronizing the data folders (parquet files). Silently skips the progress.
 */
@Component
public class SchedulerDataFileSystemSynchronizationListener extends BaseFileSystemSynchronizationListener {
}
