package ai.dqo.checks;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of data quality check time partitions: daily, monthly.
 */
public enum CheckTimePartition {
    @JsonProperty("daily")
    DAILY("daily"),

    @JsonProperty("monthly")
    MONTHLY("monthly");

    private final String displayName;

    CheckTimePartition(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a lower case display name used for the check time partition.
     * @return Lower case display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
