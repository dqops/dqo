package ai.dqo.cli;

/**
 * Spring boot shutdown manager - allows the application to request the shutdown, because the web server must be stopped.
 */
public interface ApplicationShutdownManager {
    /**
     * Initializes an application shutdown, given the application return code to return.
     *
     * @param returnCode Return code.
     */
    void initiateShutdown(int returnCode);
}
