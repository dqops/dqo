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

    /**
     * List of column names that should be loaded from the parquet files when the recent result overview is needed.
     */
    public static final String[] COLUMN_NAMES_FOR_RESULTS_OVERVIEW = new String[] {
            COLUMN_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            DATA_STREAM_NAME_COLUMN_NAME,
            ACTUAL_VALUE_COLUMN_NAME,
            SEVERITY_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            TIME_PERIOD_UTC_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME
    };

    /**
     * List of column names that should be loaded from the parquet files when the recent result detailed view is needed.
     */
    public static final String[] COLUMN_NAMES_FOR_RESULTS_DETAILED = new String[] {
            ID_COLUMN_NAME,

            ACTUAL_VALUE_COLUMN_NAME,
            EXPECTED_VALUE_COLUMN_NAME,
            WARNING_LOWER_BOUND_COLUMN_NAME,
            WARNING_UPPER_BOUND_COLUMN_NAME,
            ERROR_LOWER_BOUND_COLUMN_NAME,
            ERROR_UPPER_BOUND_COLUMN_NAME,
            FATAL_LOWER_BOUND_COLUMN_NAME,
            FATAL_UPPER_BOUND_COLUMN_NAME,
            SEVERITY_COLUMN_NAME,

            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_DISPLAY_NAME_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,

            COLUMN_NAME_COLUMN_NAME,
            DATA_STREAM_NAME_COLUMN_NAME,

            DURATION_MS_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            TIME_PERIOD_UTC_COLUMN_NAME,

            INCLUDE_IN_KPI_COLUMN_NAME,
            INCLUDE_IN_SLA_COLUMN_NAME,
            PROVIDER_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME,
            SENSOR_NAME_COLUMN_NAME
    };
}
