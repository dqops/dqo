/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.docs.generators;

import com.dqops.metadata.sources.PhysicalTableName;
import com.google.common.base.CaseFormat;

import java.time.LocalDate;

public class SampleStringsRegistry {
    public static String getConnectionName() {
        return "sample_connection";
    }

    public static String getSchemaName() {
        return "sample_schema";
    }

    public static String getTableName() {
        return "sample_table";
    }

    public static String getFullTableName() {
        return "sample_schema.sample_table";
    }

    public static String getSchemaTableName() {
        return new PhysicalTableName(getSchemaName(), getTableName()).toTableSearchFilter();
    }

    public static String getColumnName() {
        return "sample_column";
    }

    public static String getTarget() {
        return "sample_target";
    }

    public static String getCheckName() {
        return "sample_check";
    }

    public static String getCollectorName() {
        return "sample_collector";
    }

    public static String getCategoryName() {
        return "sample_category";
    }

    public static String getSensorName() {
        return "sample_sensor";
    }

    public static String getRuleName() {
        return "sample_rule";
    }

    public static String getPatternName() {
        return "default";
    }

    public static String getFullCheckName() {
        return String.join("/", getTarget(), getCategoryName(), getCheckName());
    }

    public static String getFullSensorName() {
        return String.join("/", getTarget(), getCategoryName(), getSensorName());
    }

    public static String getFullRuleName() {
        return String.join("/", getTarget(), getCategoryName(), getRuleName());
    }

    public static String getHelpText() {
        return "Sample help text";
    }

    public static String getUserName() {
        return "sample_user";
    }

    public static String getEmail() {
        return getUserName() + "@mail.com";
    }

    public static String getComment() {
        return "Sample comment";
    }

    public static String getQualityDimension() {
        return "sample_quality_dimension";
    }

    public static String getSampleUrl() {
        return "https://sample_url.com";
    }

    public static String getDataGrouping() {
        return "sample_data_grouping";
    }

    public static String getCredential() {
        return "sample_credential";
    }

    public static String getDictionary() {
        return "sample_dictionary";
    }

    public static String getTableComparison() {
        return "sample_table_comparison";
    }

    public static String getMonthStart() {
        return LocalDate.of(2007, 10,1).toString();
    }

    public static String getMonthEnd() {
        return LocalDate.of(2007, 10,31).toString();
    }

    public static String getIncidentId() {
        return "sample_incident";
    }

    public static String getIssueUrl() {
        return getSampleUrl() + "/sample_issue";
    }

    public static String getFolder(int nestingLevel) {
        return "sample_folder_" + nestingLevel;
    }

    public static String getDashboardName() {
        return "sample_dashboard";
    }

    public static String getWindowLocationOrigin() {
        return "window.location";
    }

    public static String getJobId() { return "123123124324324"; }

    public static String getDictionaryName() { return "status_codes.csv"; };

    /**
     * Gets sample string fitting the parameter.
     * @param parameterName Parameter name.
     * @return Sample string for the parameter.
     */
    public static String getMatchingStringForParameter(String parameterName) {
        String parameterNameLower = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, parameterName);
        String folder = "folder";

        if (parameterNameLower.contains("email")) {
            return getEmail();
        } else if (parameterNameLower.contains("collector")) {
            return getCollectorName();
        } else if (parameterNameLower.contains("credential")) {
            return getCredential();
        } else if (parameterNameLower.contains("incident_id")) {
            return getIncidentId();
        } else if (parameterNameLower.contains("issue_url")) {
            return getIssueUrl();
        } else if (parameterNameLower.contains("window_location")) {
            return getWindowLocationOrigin();
        } else if (parameterNameLower.contains("dashboard")) {
            return getDashboardName();
        } else if (parameterNameLower.contains(folder)) {
            int nestingLevelIndex = parameterNameLower.indexOf(folder) + folder.length();
            int nestingLevel;
            if (nestingLevelIndex >= parameterNameLower.length()) {
                nestingLevel = 0;
            } else {
                nestingLevel = parameterNameLower.charAt(nestingLevelIndex) - '0';
            }
            return getFolder(nestingLevel);
        } else if (parameterNameLower.contains("full_sensor")) {
            return getFullSensorName();
        } else if (parameterNameLower.contains("sensor")) {
            return getSensorName();
        } else if (parameterNameLower.contains("full_rule")) {
            return getFullRuleName();
        } else if (parameterNameLower.contains("rule")) {
            return getRuleName();
        } else if (parameterNameLower.contains("category")) {
            return getCategoryName();
        } else if (parameterNameLower.contains("quality_dimension")) {
            return getQualityDimension();
        } else if (parameterNameLower.contains("full_check")) {
            return getFullCheckName();
        } else if (parameterNameLower.contains("check")) {
            return getCheckName();
        } else if (parameterNameLower.contains("comparison")) {
            return getTableComparison();
        } else if (parameterNameLower.contains("connection")) {
            return getConnectionName();
        } else if (parameterNameLower.contains("schema")) {
            return getSchemaName();
        } else if (parameterNameLower.contains("full_table_name")) {
            return getFullTableName();
        } else if (parameterNameLower.contains("table")) {
            return getTableName();
        } else if (parameterNameLower.contains("column")) {
            return getColumnName();
        } else if (parameterNameLower.contains("group")) {
            return getDataGrouping();
        } else if (parameterNameLower.contains("month_start")) {
            return getMonthStart();
        } else if (parameterNameLower.contains("month_end")) {
            return getMonthEnd();
        } else if (parameterNameLower.contains("job_id")) {
            return getJobId();
        } else if (parameterNameLower.contains("dictionary_name")) {
            return getDictionaryName();
        } else if (parameterNameLower.contains("pattern_name")) {
            return getPatternName();
        }

        throw new IllegalArgumentException("No value found fitting the parameter \"" + parameterName + "\".");
    }
}
