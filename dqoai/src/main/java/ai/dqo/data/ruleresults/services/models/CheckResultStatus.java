package ai.dqo.data.ruleresults.services.models;

/**
 * Enumeration of check execution statuses. It is the highest severity or an error if the sensor could not be executed due to a configuration issue.
 */
public enum CheckResultStatus {
    success,
    warning,
    error,
    fatal,

    /**
     * Sensor or rule failed to execute due to an implementation issue.
     */
    execution_error
}
