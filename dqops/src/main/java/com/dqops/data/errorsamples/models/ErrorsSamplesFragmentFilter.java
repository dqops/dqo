/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.models;

import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.models.ParquetDataFragmentFilter;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Filter parameters for acquiring a fragment of error samples.
 */
@Data
public class ErrorsSamplesFragmentFilter extends ParquetDataFragmentFilter {
    private List<String> columnNames;
    private String checkCategory;
    private String checkName;
    private String checkType;
    private String sensorName;
    private String qualityDimension;
    private String tableComparisonName;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    @Override
    public Map<String, String> getColumnConditions() {
        Map<String, String> result = new LinkedHashMap<>();
        if (!Strings.isNullOrEmpty(checkCategory)) {
            result.put(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME, checkCategory);
        }
        if (!Strings.isNullOrEmpty(checkName)) {
            result.put(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME, checkName);
        }
        if (!Strings.isNullOrEmpty(checkType)) {
            result.put(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME, checkType);
        }
        if (!Strings.isNullOrEmpty(sensorName)) {
            result.put(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME, sensorName);
        }
        if (!Strings.isNullOrEmpty(qualityDimension)) {
            result.put(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME, qualityDimension);
        }
        if (!Strings.isNullOrEmpty(tableComparisonName)) {
            result.put(ErrorSamplesColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME, tableComparisonName);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ErrorsSamplesFragmentFilter that = (ErrorsSamplesFragmentFilter) o;

        if (!Objects.equals(checkCategory, that.checkCategory))
            return false;
        if (!Objects.equals(checkName, that.checkName)) return false;
        if (!Objects.equals(checkType, that.checkType)) return false;
        if (!Objects.equals(columnNames, that.columnNames)) return false;
        if (!Objects.equals(sensorName, that.sensorName)) return false;
        if (!Objects.equals(qualityDimension, that.qualityDimension)) return false;
        return Objects.equals(tableComparisonName, that.tableComparisonName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checkCategory != null ? checkCategory.hashCode() : 0);
        result = 31 * result + (checkName != null ? checkName.hashCode() : 0);
        result = 31 * result + (checkType != null ? checkType.hashCode() : 0);
        result = 31 * result + (columnNames != null ? columnNames.hashCode() : 0);
        result = 31 * result + (sensorName != null ? sensorName.hashCode() : 0);
        result = 31 * result + (qualityDimension != null ? qualityDimension.hashCode() : 0);
        result = 31 * result + (tableComparisonName != null ? tableComparisonName.hashCode() : 0);
        return result;
    }
}
