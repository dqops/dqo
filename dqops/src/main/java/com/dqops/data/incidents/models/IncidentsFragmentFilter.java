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
    // schema name is not present on the patition level, it can only be filtered inside parquet data
    private String schemaName;
    // table name is not present on the patition level, it can only be filtered inside parquet data
    private String tableName;
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
        if (!Strings.isNullOrEmpty(schemaName)) {
            result.put(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME, schemaName);
        }
        if (!Strings.isNullOrEmpty(tableName)) {
            result.put(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME, tableName);
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
        if (!Objects.equals(schemaName, that.schemaName)) return false;
        if (!Objects.equals(tableName, that.tableName)) return false;
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
        result = 31 * result + (schemaName != null ? schemaName.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        return result;
    }
}
