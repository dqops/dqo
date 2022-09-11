package ai.dqo.core.filesystem.synchronization.listeners;

/**
 * File synchronization listener factory.
 */
public interface FileSystemSynchronizationListenerProvider {
    /**
     * Returns the requested synchronization listener for the given reporting mode.
     *
     * @param reportingMode Reporting mode (silent, summary, debug).
     * @return Synchronization listener.
     */
    FileSystemSynchronizationListener getSynchronizationListener(FileSystemSynchronizationReportingMode reportingMode);
}
