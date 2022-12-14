package ai.dqo.data.errors.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identifies the source of the error: sensor (when the sensor failed to execute) or rule (when the rule failed to execute).
 */
public enum ErrorSource {
    /**
     * The error was raised in a sensor.
     */
    @JsonProperty("sensor")
    sensor,

    /**
     * The error was raised in a rule.
     */
    @JsonProperty("rule")
    rule
}
