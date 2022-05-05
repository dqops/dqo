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
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Column date type percent check that safe casts string type columns as date or float (UNIX time) and calculates the percent of not NULL values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnValidityDateTypePercentSensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnValidityDateTypePercentSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Desired date format. Sensor will try to parse the column records and cast the data using this format.")
    private BuiltInDateFormats namedDateFormat = BuiltInDateFormats.ISO8601;

    @JsonPropertyDescription("Custom date format")
    private String customDateFormat;

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
        return "column/validity/date_type_percent";
    }

    /**
     * Returns a desired format to parse the date.
     * @return Date format.
     */
    public BuiltInDateFormats getNamedDateFormat() {
        return namedDateFormat;
    }

    /**
     * Sets a desired format to parse the date.
     * @param namedDateFormat Date format.
     */
    public void setNamedDateFormat(BuiltInDateFormats namedDateFormat) {
        this.setDirtyIf(this.namedDateFormat != namedDateFormat);
        this.namedDateFormat = namedDateFormat;
    }

    /**
     * Returns custom date format.
     * @return Custom date format.
     */
    public String getCustomDateFormat() {
        return customDateFormat;
    }

    /**
     * Sets custom date format.
     * @param customDateFormat Custom date format.
     */
    public void setCustomDateFormat(String customDateFormat) {
        this.setDirtyIf(!Objects.equals(this.customDateFormat, customDateFormat));
        this.customDateFormat = customDateFormat;
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
        return this.namedDateFormat != null ||
                !Strings.isNullOrEmpty(this.customDateFormat);
    }
}
