/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.checks;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class contains constants used in generating the documentation for check categories.
 */
public class CheckCategoryDocumentationConstants {
    /**
     * Alternative file names for check categories.
     */
    public static final Map<String, String> CATEGORY_FILE_NAMES = new LinkedHashMap<>() {{
        put("timeliness", "how-to-detect-timeliness-and-freshness-issues.md");
        put("schema", "how-to-detect-table-schema-changes.md");
        put("patterns", "how-to-detect-bad-values-not-matching-patterns.md");
        put("comparisons", "how-to-reconcile-data-and-detect-differences.md");
        put("text", "how-to-detect-data-quality-issues-in-text-fields.md");
        put("bool", "how-to-detect-data-quality-issues-in-bool-fields.md");
        put("numeric", "how-to-detect-data-quality-issues-in-numeric-fields.md");
        put("conversions", "how-to-verify-text-values-are-parsable.md");
        put("datetime", "how-to-detect-invalid-dates.md");
        put("datatype", "how-to-detect-data-type-changes.md");
        put("integrity", "how-to-detect-data-referential-integrity-issues.md");
        put("whitespace", "how-to-detect-blank-and-whitespace-values.md");
        put("availability", "how-to-table-availability-issues-and-downtimes.md");
        put("accepted_values", "how-to-validate-accepted-values-in-columns.md");
        put("volume", "how-to-detect-data-volume-issues-and-changes.md");
        put("custom_sql", "how-to-detect-data-quality-issues-with-custom-sql.md");
        put("uniqueness", "how-to-detect-data-uniqueness-issues-and-duplicates.md");
        put("pii", "how-to-detect-pii-values-and-sensitive-data.md");
        put("nulls", "how-to-detect-empty-or-incomplete-columns-with-nulls.md");
    }};

    /**
     * Alternative link names for check categories used in the table of content of the mkdocs.yml.
     */
    public static final Map<String, String> CATEGORY_LINK_NAMES = new LinkedHashMap<>() {{
        put("timeliness", "Timeliness and freshness");
        put("schema", "Table schema changes");
        put("pii", "PII values");
        put("integrity", "Referential integrity");
        put("comparisons", "Comparing tables");
        put("whitespace", "Blanks and whitespaces");
        put("patterns", "Text patterns");
        put("datatype", "Data type detection");
        put("anomaly", "Anomaly detection");
        put("availability", "Table availability");
        put("bool", "Boolean values");
        put("numeric", "Numeric statistics");
        put("datetime", "Invalid dates");
        put("conversions", "Data type conversions");
        put("text", "Text statistics");
        put("nulls", "Empty and incomplete columns");
        put("accuracy", "Data accuracy");
        put("custom_sql", "Custom SQL and multi-column checks");
        put("volume", "Data volume");
        put("uniqueness", "Uniqueness and duplicates");
    }};

    /**
     * Help texts for the category names on tables.
     */
    public static final Map<String, String> TABLE_CATEGORY_HELP = new LinkedHashMap<>() {{
        put("volume", "Evaluates the overall quality of the table by verifying the number of rows.");
        put("timeliness", "Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.");
        put("accuracy", "Compares the tested table with another (reference) table.");
        put("custom_sql", "Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.");
        put("availability", "Checks whether the table is accessible and available for use.");
        put("anomaly", "Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.");
        put("schema", "Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.");
        put("comparisons", "Compares the table (the row count, and the column count) to another table in a different data source.");
    }};

    /**
     * Help texts for the category names on columns.
     */
    public static final Map<String, String> COLUMN_CATEGORY_HELP = new LinkedHashMap<>() {{
        put("nulls", "Checks for the presence of null or missing values in a column.");
        put("numeric", "Validates that the data in a numeric column is in the expected format or within predefined ranges.");
        put("text", "Validates that the data in a text column has a valid range.");
        put("patterns", "Validates if a text column matches predefined patterns (such as an email address) or a custom regular expression.");
        put("whitespace", "Detects text columns that contain blank values, or values that are used as placeholders for missing values: 'n/a', 'None', etc.");
        put("conversions", "Validates that the values in a text column can be parsed and converted to other data types.");
        put("datatype", "Analyzes all values in a text column to detect if all values can be safely parsed to numeric, boolean, date or timestamp data types. Used to analyze tables in the landing zone.");
        put("accepted_values", "Verifies if all values in the column are from a set of known values, such as country codes.");
        put("uniqueness", "Counts the number or percent of duplicate or unique values in a column.");
        put("datetime", "Validates that the data in a date or time column is in the expected format and within predefined ranges.");
        put("pii", "Checks for the presence of sensitive or personally identifiable information (PII) in a column such as an email, phone, zip code, IP4, and IP6 addresses.");
        put("custom_sql", "Validate data against user-defined SQL queries at the column level. Checks in this group allow to validate whether a set percentage of rows has passed a custom SQL expression or whether the custom SQL expression is not outside the set range.");
        put("bool", "Calculates the percentage of data in boolean columns.");
        put("integrity", "Checks the referential integrity of a column against a column in another table.");
        put("anomaly", "Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.");
        put("schema", "Detects schema drifts such as a column is missing or the data type has changed.");
        put("comparisons", "Compares the columns in a table to another column in another table that is in a different data source.");
    }};

    /**
     * The names of the check types pages.
     */
    public static final Map<String, String> CHECK_TYPE_PAGES = new LinkedHashMap<>() {{
        put("profiling", "data-profiling-checks.md");
        put("monitoring", "data-observability-monitoring-checks.md");
        put("partitioned", "partition-checks.md");
    }};
}
