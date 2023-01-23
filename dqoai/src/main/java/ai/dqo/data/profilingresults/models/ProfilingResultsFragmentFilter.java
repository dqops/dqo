/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.data.profilingresults.models;

import ai.dqo.data.models.ParquetDataFragmentFilter;
import ai.dqo.data.normalization.CommonColumnNames;
import ai.dqo.data.profilingresults.factory.ProfilingResultsColumnNames;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Filter parameters for acquiring a fragment of profiling results.
 */
@Data
public class ProfilingResultsFragmentFilter extends ParquetDataFragmentFilter {
    private String profilerCategory;

    private String profilerName;

    private String profilerType;

    private String columnName;

    private String dataStreamName;
    private String sensorName;

    /**
     * Tell which additional columns should be read from the parquet, and what their value should be.
     * @return Mapping column name to expected value.
     */
    @Override
    public Map<String, String> getColumnConditions() {
        Map<String, String> result = new HashMap<>();
        if (!Strings.isNullOrEmpty(profilerCategory)) {
            result.put(ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME, profilerCategory);
        }
        if (!Strings.isNullOrEmpty(profilerName)) {
            result.put(ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME, profilerName);
        }
        if (!Strings.isNullOrEmpty(profilerType)) {
            result.put(ProfilingResultsColumnNames.PROFILER_TYPE_COLUMN_NAME, profilerType);
        }
        if (!Strings.isNullOrEmpty(columnName)) {
            result.put(ProfilingResultsColumnNames.COLUMN_NAME_COLUMN_NAME, columnName);
        }
        if (!Strings.isNullOrEmpty(dataStreamName)) {
            result.put(ProfilingResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME, dataStreamName);
        }
        if (!Strings.isNullOrEmpty(sensorName)) {
            result.put(ProfilingResultsColumnNames.SENSOR_NAME_COLUMN_NAME, sensorName);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProfilingResultsFragmentFilter that = (ProfilingResultsFragmentFilter) o;

        if (!Objects.equals(profilerCategory, that.profilerCategory))
            return false;
        if (!Objects.equals(profilerName, that.profilerName)) return false;
        if (!Objects.equals(profilerType, that.profilerType)) return false;
        if (!Objects.equals(columnName, that.columnName)) return false;
        if (!Objects.equals(dataStreamName, that.dataStreamName))
            return false;
        return Objects.equals(sensorName, that.sensorName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (profilerCategory != null ? profilerCategory.hashCode() : 0);
        result = 31 * result + (profilerName != null ? profilerName.hashCode() : 0);
        result = 31 * result + (profilerType != null ? profilerType.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (dataStreamName != null ? dataStreamName.hashCode() : 0);
        result = 31 * result + (sensorName != null ? sensorName.hashCode() : 0);
        return result;
    }
}
