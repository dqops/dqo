package ai.dqo.checks;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of time scale of checkpoint and partitioned data quality checks (daily, monthly, etc.)
 */
public enum CheckTimeScale {
    @JsonProperty("daily")
    daily,  // lowercase name to make swagger work

    @JsonProperty("monthly")
    monthly // lowercase name to make swagger work
}
