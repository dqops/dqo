package ai.dqo.cli.commands.check;

/**
 * Enumeration of the issue severity level that will cause the "check run" command to fail.
 */
public enum CheckRunCommandFailThreshold {
    /**
     * Any warning detected or any higher severity level (error, fatal) causes the "check run" command to fail.
     */
    warning,

    /**
     * Any error detected or any higher severity level (fatal) causes the "check run" command to fail.
     */
    error,

    /**
     * Only fatal severity issues that were detected will cause the "check run" command to fail.
     */
    fatal,

    /**
     * Do not fail, just blindly run "check run" without returning an error code.
     */
    none
}
