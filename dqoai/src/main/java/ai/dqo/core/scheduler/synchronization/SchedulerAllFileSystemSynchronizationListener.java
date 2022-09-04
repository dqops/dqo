package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.filesystem.synchronization.BaseFileSystemSynchronizationListener;
import org.springframework.stereotype.Component;

/**
 * Synchronization listener used by the scheduler when synchronizing the whole folder (definition and data). Silently skips the progress.
 */
@Component
public class SchedulerAllFileSystemSynchronizationListener extends BaseFileSystemSynchronizationListener {
}
