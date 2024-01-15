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
package com.dqops.sensors.column.sampling;

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSamplingColumnSamplesSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSamplingColumnSamplesSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The limit of results that are returned. The default value is 10 sample values with the highest count (the most popular).")
    @RequiredField
    private Integer limit = 10;

    /**
     * Returns the limit of rows that are returned in the sample.
     * @return The limit of rows in the sample.
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the limit of rows to return in the sample.
     * @param limit The limit of samples to return.
     */
    public void setLimit(Integer limit) {
        this.setDirtyIf(!Objects.equals(this.limit, limit));
        this.limit = limit;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        return "column/sampling/column_samples";
    }
}
