package ai.dqo.checks;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of data quality check types: adhoc, checkpoint, partitioned.
 */
public enum CheckType {
    @JsonProperty("adhoc")
    ADHOC("adhoc"),

    @JsonProperty("checkpoint")
    CHECKPOINT("checkpoint"),

    @JsonProperty("partitioned")
    PARTITIONED("partitioned");

    private final String displayName;

    CheckType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a lower case display name used for the check. The value is stored in parquet files.
     * @return Lower case display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
