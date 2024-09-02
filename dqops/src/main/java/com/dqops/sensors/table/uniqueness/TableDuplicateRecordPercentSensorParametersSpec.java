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
package com.dqops.sensors.table.uniqueness;

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Table sensor that executes a duplicate record percent query.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableDuplicateRecordPercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableDuplicateRecordPercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("A list of columns used for uniqueness record duplicate verification.")
    @SampleValues(values = { "id", "created_at" })
    @RequiredField
    private List<String> columns;

    /**
     * Returns given values from user.
     * @return values.
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * Sets a List given from user.
     * @param columns values given from user.
     */
    public void setColumns(List<String> columns) {
        this.setDirtyIf(!Objects.equals(this.columns, columns));
        this.columns = columns != null ? Collections.unmodifiableList(columns) : null;
    }

    /**
     * Sensor name used by this sensor parameters.
     */
    public static final String SENSOR_NAME = "table/uniqueness/duplicate_record_percent";

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
        return SENSOR_NAME;
    }

    /**
     * Returns the default value that is used when the sensor returned no rows.
     *
     * @return Default value to use when the sensor returned no rows.
     */
    @Override
    @JsonIgnore
    public Double getDefaultValue() {
        return 0.0;
    }

    public static class TableDuplicateRecordPercentSensorParametersSpecSampleFactory implements SampleValueFactory<TableDuplicateRecordPercentSensorParametersSpec> {
        @Override
        public TableDuplicateRecordPercentSensorParametersSpec createSample() {
            return new TableDuplicateRecordPercentSensorParametersSpec();
        }
    }
}
