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

package com.dqops.services.check.mining;

import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Data quality check rule mining parameters. Configure what type of checks should be configured.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckMiningParametersModel", description = "Data quality check rule mining parameters. Configure what type of checks should be configured.")
public class CheckMiningParametersModel implements Cloneable {
    /**
     * The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.
     */
    @JsonPropertyDescription("The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.")
    private TargetRuleSeverityLevel severityLevel = TargetRuleSeverityLevel.error;

    /**
     * Optional filter for the check category names, supports filtering with prefixes and suffixes defined as a '*' character.
     */
    @JsonPropertyDescription("Optional filter for the check category names, supports filtering with prefixes and suffixes defined as a '*' character.")
    private String categoryFilter;

    /**
     * Optional filter for the check names, supports filtering with prefixes and suffixes defined as a '*' character.
     */
    @JsonPropertyDescription("Optional filter for the check names, supports filtering with prefixes and suffixes defined as a '*' character.")
    private String checkNameFilter;

    /**
     * Optional filter for the column names, supports filtering with prefixes and suffixes defined as a '*' character.
     */
    @JsonPropertyDescription("Optional filter for the column names, supports filtering with prefixes and suffixes defined as a '*' character.")
    private String columnNameFilter;

    /**
     * Copy also the configuration of profiling checks that failed.
     */
    @JsonPropertyDescription("Copy also the configuration of profiling checks that failed.")
    private boolean copyFailedProfilingChecks;

    /**
     * Copy also the configuration of profiling checks that are disabled.
     */
    @JsonPropertyDescription("Copy also the configuration of profiling checks that are disabled.")
    private boolean copyDisabledProfilingChecks;

    /**
     * Propose the rules for default checks that were activated using data quality check patterns (policies). The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the rules for default checks that were activated using data quality check patterns (policies). The default value of this parameter is 'true'.")
    private boolean proposeDefaultChecks = true;

    /**
     * Proposes the default configuration of the minimum row count for monitoring checks (full table scans). The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration of the minimum row count for monitoring checks (full table scans). The default value of this parameter is 'true'.")
    private boolean proposeMinimumRowCount = true;

    /**
     * Proposes the default configuration of the column count check. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration of the column count check. The default value of this parameter is 'true'.")
    private boolean proposeColumnCount = true;

    /**
     * Proposes the default configuration of the timeliness checks, including an accepted freshness delay. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration of the timeliness checks, including an accepted freshness delay. The default value of this parameter is 'true'.")
    private boolean proposeTimelinessChecks = true;

    /**
     * Proposes the default configuration the null checks that verify that there are no null values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration the null checks that verify that there are no null values. The default value of this parameter is 'true'.")
    private boolean proposeNullsChecks = true;

    /**
     * Proposes the default configuration the not-null checks that validate scale of not-nulls (require some null values, and require some not-null values). The default value of this parameter is 'false'.
     */
    @JsonPropertyDescription("Proposes the default configuration the not-null checks that validate scale of not-nulls (require a mix of some not-null and null values).The default value of this parameter is 'false'.")
    private boolean proposeNotNullsChecks = true;

    /**
     * Proposes the default configuration the detected data type of values in a text column check, when text columns contain an uniform type such as integers or dates. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration the detected data type of values in a text column check, when text columns contain an uniform type such as integers or dates. The default value of this parameter is 'true'.")
    private boolean proposeTextValuesDataType = true;

    /**
     * Enables a rule on the column's schema check that verifies if the column exists. It is enabled on columns that were detected as existing during data profiling. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Enables a rule on the column's schema check that verifies if the column exists. It is enabled on columns that were detected as existing during data profiling. The default value of this parameter is 'true'.")
    private boolean proposeColumnExists = true;

    /**
     * Proposes the default configuration the uniqueness checks that validate the number of distinct and duplicate values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration the uniqueness checks that validate the number of distinct and duplicate values. The default value of this parameter is 'true'.")
    private boolean proposeUniquenessChecks = true;

    /**
     * Proposes the default configuration of numeric checks that validate the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration of numeric checks that validate the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The default value of this parameter is 'true'.")
    private boolean proposeNumericRanges = true;

    /**
     * Proposes the default configuration of the text length checks. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration of the text length checks. The default value of this parameter is 'true'.")
    private boolean proposeTextLengthRanges = true;

    /**
     * Proposes the default configuration the accepted values checks. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration the accepted values checks. The default value of this parameter is 'true'.")
    private boolean proposeAcceptedValuesChecks = true;

    /**
     * The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%).
     */
    @JsonPropertyDescription("The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%).")
    private Double failChecksAtPercentErrorRows;

    /**
     * The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.
     */
    @JsonPropertyDescription("The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.")
    private Double maxPercentErrorRowsForPercentChecks;

    /**
     * Proposes the default configuration for custom checks that use built-in data quality rules. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Proposes the default configuration for custom checks that use built-in data quality rules. The default value of this parameter is 'true'.")
    private boolean proposeCustomChecks = true;

    /**
     * Creates and returns a copy of this object.
     * @return Cloned instance.
     */
    @Override
    public CheckMiningParametersModel clone() {
        try {
            return (CheckMiningParametersModel)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
