package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.metadata.FileDifference;
import ai.dqo.core.filesystem.synchronization.BaseFileSystemSynchronizationListener;
import org.springframework.stereotype.Component;

/**
 * Synchronization listener used by the scheduler when synchronizing the whole folder (definition and data). Silently skips the progress.
 */
@Component
public class SchedulerAllFileSystemSynchronizationListener extends BaseFileSystemSynchronizationListener {
    /**
     * Called when the synchronization is about to begin. The synchronization is from the source to the target.
     *
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    @Override
    public void onSynchronizationBegin(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem) {
        super.onSynchronizationBegin(sourceFileSystem, targetFileSystem);
    }

    /**
     * Called when the synchronization has finished. The synchronization is from the source to the target.
     *
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     */
    @Override
    public void onSynchronizationFinished(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem) {
        super.onSynchronizationFinished(sourceFileSystem, targetFileSystem);
    }

    /**
     * Called when a local change (from the source) was applied on the target file system.
     *
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the source file system that was applied (uploaded, deleted, etc.)
     */
    @Override
    public void onSourceChangeAppliedToTarget(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem, FileDifference fileDifference) {
        super.onSourceChangeAppliedToTarget(sourceFileSystem, targetFileSystem, fileDifference);
    }

    /**
     * Called when a remote change (from the target system) was applied on the source file system (downloaded).
     *
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param fileDifference   Change in the target (remote) file system that was applied (uploaded, deleted, etc.) on the source system (downloaded).
     */
    @Override
    public void onTargetChangeAppliedToSource(DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem, FileDifference fileDifference) {
        super.onTargetChangeAppliedToSource(sourceFileSystem, targetFileSystem, fileDifference);
    }
}
