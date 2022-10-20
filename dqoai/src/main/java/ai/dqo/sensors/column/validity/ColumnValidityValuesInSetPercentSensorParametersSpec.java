/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.sensors.column.validity;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Column level sensor that calculates the percent of values in a column matching the values in a provided list
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnValidityValuesInSetPercentSensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnValidityValuesInSetPercentSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Desired data type. Sensor will try to match the column records and cast the data using this type.")
    private BuiltInListFormats valuesType = BuiltInListFormats.STRING;

    @JsonPropertyDescription("Provided list of values to match the data.")
    private List<String> valuesList;

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
        return "column/validity/values_in_set_percent";
    }

    /**
     * Returns a desired type to match the data from given values.
     * @return values type.
     */
    public BuiltInListFormats getValuesType() {
        return valuesType;
    }

    /**
     * Sets a desired type to match the data from given values.
     * @param valuesType .
     */
    public void setValuesType(BuiltInListFormats valuesType) {
        this.setDirtyIf(this.valuesType != valuesType);
        this.valuesType = valuesType;
    }

    /**
     * Returns given values from user.
     * @return values.
     */
    public List<String> getValuesList() {
        return valuesList;
    }

    /**
     * Sets a List given from user.
     * @param valuesList values given from user.
     */
    public void setValuesList(List<String> valuesList) {
        this.setDirtyIf(!Objects.equals(this.valuesList, valuesList));
        this.valuesList = valuesList;
    }

    /**
     * This method should be overriden in derived classes and should check if there are any simple fields (String, integer, double, etc)
     * that are not HierarchyNodes (they are analyzed by the hierarchy tree engine).
     * This method should return true if there is at least one field that must be serialized to YAML.
     * It may return false only if:
     * - the parameter specification class has no custom fields (parameters are not configurable)
     * - there are some fields, but they are all nulls, so not a single field would be serialized.
     * The purpose of this method is to avoid serialization of the parameters as just "parameters: " yaml, without nested
     * fields because such a YAML is just invalid.
     *
     * @return True when the parameters spec must be serialized to YAML because it has some non-null simple fields,
     * false when serialization of the parameters may lead to writing an empty "parameters: " entry in YAML.
     */
    @Override
    public boolean hasNonNullSimpleFields() {
        return this.valuesType != null ||
                this.valuesList != null;
    }
}
