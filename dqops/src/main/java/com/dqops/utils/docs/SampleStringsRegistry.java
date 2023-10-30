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

package com.dqops.utils.docs;

import com.dqops.metadata.sources.PhysicalTableName;

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

    public static String getCategoryName() {
        return "sample_category";
    }

    public static String getSensorName() {
        return "sample_sensor";
    }

    public static String getRuleName() {
        return "sample_rule";
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

    public static String getTableComparison() {
        return "sample_table_comparison";
    }

    /**
     * Gets sample string fitting the parameter.
     * @param parameterName Parameter name.
     * @return Sample string for the parameter.
     */
    public static String getMatchingStringForParameter(String parameterName) {
        String parameterNameLower = parameterName.toLowerCase();

        if (parameterNameLower.contains("connection")) {
            return getConnectionName();
        } else if (parameterNameLower.contains("sensor")) {
            return getSensorName();
        } else if (parameterNameLower.contains("rule")) {
            return getRuleName();
        } else if (parameterNameLower.contains("category")) {
            return getCategoryName();
        } else if (parameterNameLower.contains("check")) {
            return getCheckName();
        }

        throw new IllegalArgumentException("No value found fitting the parameter \"" + parameterName + "\".");
    }
}
