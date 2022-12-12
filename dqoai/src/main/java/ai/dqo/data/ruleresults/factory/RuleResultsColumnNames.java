package ai.dqo.data.ruleresults.factory;

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;

/**
 * Class with constants - the column names in the rule_results parquet table.
 */
public class RuleResultsColumnNames extends SensorReadoutsColumnNames {
    /**
     * Rule severity (0, 1, 2, 3) for none, low, medium and high alerts.
     */
    public static final String SEVERITY_COLUMN_NAME = "severity";

    /**
     * Column name for a boolean column that identifies data quality rule results that should be counted in the data quality KPI.
     */
    public static final String INCLUDE_IN_KPI_COLUMN_NAME = "include_in_kpi";

    /**
     * Column name for a boolean column that identifies data quality rule results that should be counted in the data quality SLA.
     */
    public static final String INCLUDE_IN_SLA_COLUMN_NAME = "include_in_sla";

    /**
     * Column name for the warning lower bound, returned by the fatal severity rule.
     */
    public static final String FATAL_LOWER_BOUND_COLUMN_NAME = "fatal_lower_bound";

    /**
     * Column name for the fatal upper bound, returned by the fatal severity rule.
     */
    public static final String FATAL_UPPER_BOUND_COLUMN_NAME = "fatal_upper_bound";

    /**
     * Column name for the error lower bound, returned by the error (medium) severity rule.
     */
    public static final String ERROR_LOWER_BOUND_COLUMN_NAME = "error_lower_bound";

    /**
     * Column name for the error upper bound, returned by the error severity rule.
     */
    public static final String ERROR_UPPER_BOUND_COLUMN_NAME = "error_upper_bound";

    /**
     * Column name for the warning lower bound, returned by the warning severity rule.
     */
    public static final String WARNING_LOWER_BOUND_COLUMN_NAME = "warning_lower_bound";

    /**
     * Column name for the warning upper bound, returned by the warning severity rule.
     */
    public static final String WARNING_UPPER_BOUND_COLUMN_NAME = "warning_upper_bound";
}
