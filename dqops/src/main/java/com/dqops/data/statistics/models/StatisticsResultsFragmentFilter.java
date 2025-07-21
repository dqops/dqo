/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.statistics.models;

import com.dqops.data.models.ParquetDataFragmentFilter;
import com.dqops.data.statistics.factory.StatisticsColumnNames;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.*;

/**
 * Filter parameters for acquiring a fragment of statistics results.
 */
@Data
public class StatisticsResultsFragmentFilter extends ParquetDataFragmentFilter {
    private List<String> columnNames;
    private String collectorCategory;
    private String collectorName;
    private String collectorTarget;
    private String dataStreamName;
    private String sensorName;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    @Override
    public Map<String, String> getColumnConditions() {
        Map<String, String> result = new LinkedHashMap<>();
        if (!Strings.isNullOrEmpty(collectorCategory)) {
            result.put(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME, collectorCategory);
        }
        if (!Strings.isNullOrEmpty(collectorName)) {
            result.put(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME, collectorName);
        }
        if (!Strings.isNullOrEmpty(collectorTarget)) {
            result.put(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME, collectorTarget);
        }
        if (!Strings.isNullOrEmpty(dataStreamName)) {
            result.put(StatisticsColumnNames.DATA_GROUP_NAME_COLUMN_NAME, dataStreamName);
        }
        if (!Strings.isNullOrEmpty(sensorName)) {
            result.put(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME, sensorName);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StatisticsResultsFragmentFilter that = (StatisticsResultsFragmentFilter) o;

        if (!Objects.equals(collectorCategory, that.collectorCategory))
            return false;
        if (!Objects.equals(collectorName, that.collectorName)) return false;
        if (!Objects.equals(collectorTarget, that.collectorTarget)) return false;
        if (!Objects.equals(columnNames, that.columnNames)) return false;
        if (!Objects.equals(dataStreamName, that.dataStreamName))
            return false;
        return Objects.equals(sensorName, that.sensorName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (collectorCategory != null ? collectorCategory.hashCode() : 0);
        result = 31 * result + (collectorName != null ? collectorName.hashCode() : 0);
        result = 31 * result + (collectorTarget != null ? collectorTarget.hashCode() : 0);
        result = 31 * result + (columnNames != null ? columnNames.hashCode() : 0);
        result = 31 * result + (dataStreamName != null ? dataStreamName.hashCode() : 0);
        result = 31 * result + (sensorName != null ? sensorName.hashCode() : 0);
        return result;
    }
}
