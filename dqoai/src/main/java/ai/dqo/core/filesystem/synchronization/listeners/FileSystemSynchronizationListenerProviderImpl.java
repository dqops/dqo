package ai.dqo.core.filesystem.synchronization.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File synchronization listener factory.
 */
@Component
public class FileSystemSynchronizationListenerProviderImpl implements FileSystemSynchronizationListenerProvider {
    private SilentFileSystemSynchronizationListener silentFileSystemSynchronizationListener;
    private SummaryFileSystemSynchronizationListener summaryFileSystemSynchronizationListener;
    private DebugFileSystemSynchronizationListener debugFileSystemSynchronizationListener;

    @Autowired
    public FileSystemSynchronizationListenerProviderImpl(SilentFileSystemSynchronizationListener silentFileSystemSynchronizationListener,
                                                         SummaryFileSystemSynchronizationListener summaryFileSystemSynchronizationListener,
                                                         DebugFileSystemSynchronizationListener debugFileSystemSynchronizationListener) {
        this.silentFileSystemSynchronizationListener = silentFileSystemSynchronizationListener;
        this.summaryFileSystemSynchronizationListener = summaryFileSystemSynchronizationListener;
        this.debugFileSystemSynchronizationListener = debugFileSystemSynchronizationListener;
    }

    /**
     * Returns the requested synchronization listener for the given reporting mode.
     * @param reportingMode Reporting mode (silent, summary, debug).
     * @return Synchronization listener.
     */
    @Override
    public FileSystemSynchronizationListener getSynchronizationListener(FileSystemSynchronizationReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return this.silentFileSystemSynchronizationListener;
            case summary:
                return this.summaryFileSystemSynchronizationListener;
            case debug:
                return debugFileSystemSynchronizationListener;
            default:
                return this.silentFileSystemSynchronizationListener;
        }
    }
}
