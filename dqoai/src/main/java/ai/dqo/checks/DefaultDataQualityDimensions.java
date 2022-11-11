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

    public final String name;

    DefaultDataQualityDimensions(String name) {
        this.name = name;
    }
}
