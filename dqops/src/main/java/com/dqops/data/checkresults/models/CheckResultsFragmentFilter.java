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
package com.dqops.data.checkresults.models;

import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.models.ParquetDataFragmentFilter;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.*;

/**
 * Filter parameters for acquiring a fragment of check results.
 */
@Data
public class CheckResultsFragmentFilter extends ParquetDataFragmentFilter {
    private List<String> columnNames;
    private String checkCategory;
    private String checkName;
    private String checkType;
    private String dataStreamName;
    private String sensorName;
    private String qualityDimension;
    private String timeGradient;
    private String tableComparisonName;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    @Override
    public Map<String, String> getColumnConditions() {
        Map<String, String> result = new LinkedHashMap<>();
        if (!Strings.isNullOrEmpty(checkCategory)) {
            result.put(CheckResultsColumnNames.CHECK_CATEGORY_COLUMN_NAME, checkCategory);
        }
        if (!Strings.isNullOrEmpty(checkName)) {
            result.put(CheckResultsColumnNames.CHECK_NAME_COLUMN_NAME, checkName);
        }
        if (!Strings.isNullOrEmpty(checkType)) {
            result.put(CheckResultsColumnNames.CHECK_TYPE_COLUMN_NAME, checkType);
        }
        if (!Strings.isNullOrEmpty(dataStreamName)) {
            result.put(CheckResultsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, dataStreamName);
        }
        if (!Strings.isNullOrEmpty(sensorName)) {
            result.put(CheckResultsColumnNames.SENSOR_NAME_COLUMN_NAME, sensorName);
        }
        if (!Strings.isNullOrEmpty(qualityDimension)) {
            result.put(CheckResultsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, qualityDimension);
        }
        if (!Strings.isNullOrEmpty(timeGradient)) {
            result.put(CheckResultsColumnNames.TIME_GRADIENT_COLUMN_NAME, timeGradient);
        }
        if (!Strings.isNullOrEmpty(tableComparisonName)) {
            result.put(CheckResultsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, tableComparisonName);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CheckResultsFragmentFilter that = (CheckResultsFragmentFilter) o;

        if (!Objects.equals(checkCategory, that.checkCategory))
            return false;
        if (!Objects.equals(checkName, that.checkName)) return false;
        if (!Objects.equals(checkType, that.checkType)) return false;
        if (!Objects.equals(columnNames, that.columnNames)) return false;
        if (!Objects.equals(dataStreamName, that.dataStreamName))
            return false;
        if (!Objects.equals(sensorName, that.sensorName)) return false;
        if (!Objects.equals(qualityDimension, that.qualityDimension))
            return false;
        if (!Objects.equals(tableComparisonName, that.tableComparisonName)) return false;
        return Objects.equals(timeGradient, that.timeGradient);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checkCategory != null ? checkCategory.hashCode() : 0);
        result = 31 * result + (checkName != null ? checkName.hashCode() : 0);
        result = 31 * result + (checkType != null ? checkType.hashCode() : 0);
        result = 31 * result + (columnNames != null ? columnNames.hashCode() : 0);
        result = 31 * result + (dataStreamName != null ? dataStreamName.hashCode() : 0);
        result = 31 * result + (sensorName != null ? sensorName.hashCode() : 0);
        result = 31 * result + (qualityDimension != null ? qualityDimension.hashCode() : 0);
        result = 31 * result + (tableComparisonName != null ? tableComparisonName.hashCode() : 0);
        result = 31 * result + (timeGradient != null ? timeGradient.hashCode() : 0);
        return result;
    }
}
