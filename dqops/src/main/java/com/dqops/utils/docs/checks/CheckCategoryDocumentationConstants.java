/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        put("datetime", "how-to-detect-data-quality-issues-in-dates.md");
        put("datatype", "how-to-detect-data-type-changes.md");
        put("integrity", "how-to-detect-data-referential-integrity-issues.md");
        put("blanks", "how-to-detect-blank-and-whitespace-values.md");
        put("availability", "how-to-table-availability-issues-and-downtimes.md");
        put("accepted_values", "how-to-validate-accepted-values-in-columns.md");
        put("volume", "how-to-detect-data-volume-issues-and-changes.md");
        put("custom_sql", "how-to-detect-data-quality-issues-with-custom-sql.md");
        put("uniqueness", "how-to-detect-data-uniqueness-issues-and-duplicates.md");
        put("pii", "how-to-detect-pii-values-and-sensitive-data.md");
    }};

    /**
     * Alternative link names for check categories used in the table of content of the mkdocs.yml.
     */
    public static final Map<String, String> CATEGORY_LINK_NAMES = new LinkedHashMap<>() {{
        put("timeliness", "Timeliness and freshness");
        put("schema", "Table schema drifts");
        put("pii", "PII values");
        put("integrity", "Referential integrity");
        put("comparisons", "Comparing tables");
        put("blanks", "Blanks and whitespaces");
        put("patterns", "Text patterns");
        put("datatype", "Data type detection");
        put("anomaly", "Anomalies");
        put("availability", "Table availability");
        put("bool", "Booleans");
        put("numeric", "Numerics");
        put("datetime", "Dates");
        put("text", "Text statistics");
        put("nulls", "Nulls");
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
        put("text", "Validates that the data in a text column has a valid range, or can be parsed to other data types.");
        put("patterns", "Validates if a text column matches predefined patterns (such as an email address) or a custom regular expression.");
        put("blanks", "Detects text columns that contain blank values, or values that are used as placeholders for missing values: 'n/a', 'None', etc.");
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
