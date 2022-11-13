package ai.dqo.checks;

/**
 * Enumeration of data quality dimension names that are used in checks.
 */
public enum DefaultDataQualityDimensions {
    COMPLETENESS("Completeness"),
    TIMELINESS("Timeliness"),
    CONSISTENCY("Consistency"),
    REASONABLENESS("Reasonableness"),
    ACCURACY("Accuracy"),
    VALIDITY("Validity");

    private final String displayName;

    DefaultDataQualityDimensions(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the data quality dimension that should be stored in parquet.
     * @return Data quality dimension display name.
     */
    public String getDisplayName() {
        return displayName;
    }
}
