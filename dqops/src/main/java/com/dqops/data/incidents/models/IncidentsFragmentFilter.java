/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.models;

import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.models.ParquetDataFragmentFilter;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Filter parameters for acquiring a fragment of sensor readouts.
 */
@Data
public class IncidentsFragmentFilter extends ParquetDataFragmentFilter {
    private List<String> columnNames;
    private String checkCategory;
    private String checkName;
    private String checkType;
    private String dataStreamName;
    private String qualityDimension;
    private String statusName;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    @Override
    public Map<String, String> getColumnConditions() {
        Map<String, String> result = new LinkedHashMap<>();
        if (!Strings.isNullOrEmpty(checkCategory)) {
            result.put(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME, checkCategory);
        }
        if (!Strings.isNullOrEmpty(checkName)) {
            result.put(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME, checkName);
        }
        if (!Strings.isNullOrEmpty(checkType)) {
            result.put(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME, checkType);
        }
        if (!Strings.isNullOrEmpty(dataStreamName)) {
            result.put(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, dataStreamName);
        }
        if (!Strings.isNullOrEmpty(qualityDimension)) {
            result.put(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME, qualityDimension);
        }
        if (!Strings.isNullOrEmpty(statusName)) {
            result.put(IncidentsColumnNames.STATUS_COLUMN_NAME, statusName);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        IncidentsFragmentFilter that = (IncidentsFragmentFilter) o;
        if (!Objects.equals(checkCategory, that.checkCategory)) return false;
        if (!Objects.equals(checkName, that.checkName)) return false;
        if (!Objects.equals(checkType, that.checkType)) return false;
        if (!Objects.equals(columnNames, that.columnNames)) return false;
        if (!Objects.equals(dataStreamName, that.dataStreamName)) return false;
        if (!Objects.equals(qualityDimension, that.qualityDimension)) return false;
        return Objects.equals(statusName, that.statusName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checkCategory != null ? checkCategory.hashCode() : 0);
        result = 31 * result + (checkName != null ? checkName.hashCode() : 0);
        result = 31 * result + (checkType != null ? checkType.hashCode() : 0);
        result = 31 * result + (columnNames != null ? columnNames.hashCode() : 0);
        result = 31 * result + (dataStreamName != null ? dataStreamName.hashCode() : 0);
        result = 31 * result + (qualityDimension != null ? qualityDimension.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        return result;
    }
}
