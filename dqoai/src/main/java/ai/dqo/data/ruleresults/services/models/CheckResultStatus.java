package ai.dqo.data.ruleresults.services.models;

/**
 * Enumeration of check execution statuses. It is the highest severity or an error if the sensor could not be executed due to a configuration issue.
 */
public enum CheckResultStatus {
    success(0),
    warning(1),
    error(2),
    fatal(3),

    /**
     * Sensor or rule failed to execute due to an implementation issue.
     */
    execution_error(4);

    private int severity;

    /**
     * Initializes an enum with a matching severity value.
     * @param severity Severity value (0..4), where 4 is the execution error.
     */
    CheckResultStatus(int severity) {
        this.severity = severity;
    }

    /**
     * Returns the severity value (0..4), where 0..3 are check severity levels and 4 is an execution error.
     * @return Severity value.
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Creates a result status object from a severity number.
     * @param severity Severity value (0..4).
     * @return Matching result status.
     */
    public static CheckResultStatus fromSeverity(int severity) {
        switch (severity) {
            case 0: return success;
            case 1: return warning;
            case 2: return error;
            case 3: return fatal;
            case 4: return execution_error;
            default:
                throw new IllegalArgumentException("Severity " + severity + " is out of range");
        }
    }
}
