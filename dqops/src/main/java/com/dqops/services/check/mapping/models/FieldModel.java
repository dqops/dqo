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
package com.dqops.services.check.mapping.models;

import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "FieldModel", description = "Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.")
public class FieldModel implements Cloneable {
    @JsonPropertyDescription("Field name that matches the field name (snake_case) used in the YAML specification.")
    private ParameterDefinitionSpec definition;

    @JsonPropertyDescription("Field value is optional and may be null, when false - the field is required and must be filled.")
    private boolean optional;

    @JsonPropertyDescription("Field value for a string field.")
    private String stringValue;

    @JsonPropertyDescription("Field value for a boolean field.")
    private Boolean booleanValue;

    @JsonPropertyDescription("Field value for an integer (32-bit) field.")
    private Integer integerValue;

    @JsonPropertyDescription("Field value for a long (64-bit) field.")
    private Long longValue;

    @JsonPropertyDescription("Field value for a double field.")
    private Double doubleValue;

    @JsonPropertyDescription("Field value for a date time field.")
    private LocalDateTime datetimeValue;

    @JsonPropertyDescription("Field value for a column name field.")
    private String columnNameValue;

    @JsonPropertyDescription("Field value for an enum (choice) field.")
    private String enumValue;

    @JsonPropertyDescription("Field value for an array (list) of strings.")
    private List<String> stringListValue;

    @JsonPropertyDescription("Field value for an array (list) of integers, using 64 bit integers.")
    private List<Long> integerListValue;

    @JsonPropertyDescription("Field value for an date.")
    private LocalDate dateValue;

    /**
     * Creates a selective deep/shallow clone of the object. Definition objects are not cloned, but all other editable objects are.
     * @return Cloned instance.
     */
    public FieldModel cloneForUpdate() {
        try {
            FieldModel cloned = (FieldModel) super.clone();
            if (cloned.definition != null) {
                cloned.definition = cloned.definition.deepClone();
            }
            if (cloned.stringListValue != null) {
                cloned.stringListValue = new ArrayList<>(cloned.stringListValue);
            }
            if (cloned.integerListValue != null) {
                cloned.integerListValue = new ArrayList<>(cloned.integerListValue);
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported: " + ex.toString(), ex);
        }
    }

    /**
     * Sets a sample value on the correct field value. Copies and converts sample values from the list of sample values.
     * The method is meant to be called when generating the YAML samples for documentation.
     */
    public void applySampleValues() {
        if (this.definition == null || this.definition.getSampleValues() == null || this.definition.getSampleValues().size() == 0) {
            return;
        }

        List<String> sampleValues = this.definition.getSampleValues();

        switch (this.definition.getDataType()) {
            case string_type:
                this.stringValue = sampleValues.get(0);
                return;

            case boolean_type:
                this.booleanValue = Boolean.parseBoolean(sampleValues.get(0));
                return;

            case integer_type:
                this.integerValue = Integer.valueOf(sampleValues.get(0));
                return;

            case long_type:
                this.longValue = Long.valueOf(sampleValues.get(0));
                return;

            case double_type:
                this.doubleValue = Double.valueOf(sampleValues.get(0));
                return;

            case datetime_type:
                this.datetimeValue =LocalDateTime.parse(sampleValues.get(0));
                return;

            case date_type:
                this.dateValue = LocalDate.parse(sampleValues.get(0));
                return;

            case column_name_type:
                this.columnNameValue = sampleValues.get(0);
                return;

            case enum_type:
                this.enumValue = sampleValues.get(0);
                return;

            case string_list_type:
                this.stringListValue = new ArrayList<>(sampleValues);
                return;

            case integer_list_type:
                this.integerListValue = sampleValues.stream().map(sample -> Long.valueOf(sample)).collect(Collectors.toList());
                return;
        }
    }

    /**
     * Returns the field value for the parameter type in the field definition.
     * @return Field value.
     */
    @JsonIgnore
    public Object getValue() {
        switch (this.definition.getDataType()) {
            case string_type:
                return this.stringValue;

            case boolean_type:
                return this.booleanValue;

            case integer_type:
                return this.integerValue;

            case long_type:
                return this.longValue;

            case double_type:
                return this.doubleValue;

            case datetime_type:
                return this.datetimeValue;

            case date_type:
                return this.dateValue;

            case column_name_type:
                return this.columnNameValue;

            case enum_type:
                return this.enumValue;

            case string_list_type:
                return this.stringListValue;

            case integer_list_type:
                return this.integerListValue;

            default:
                throw new DqoRuntimeException("Cannot retrieve a value for the type " + this.definition.getDataType());
        }
    }

    /**
     * Sets an object as a field value (in the model) for a matching data type.
     * @param value Value to store.
     */
    @JsonIgnore
    public void setValue(Object value) {
        switch (this.definition.getDataType()) {
            case string_type:
                this.stringValue = value == null || value instanceof String ? (String)value : value.toString();
                return;

            case boolean_type:
                this.booleanValue = value == null || value instanceof Boolean ? (Boolean)value : Boolean.parseBoolean(value.toString());
                return;

            case integer_type:
                this.integerValue = value == null || value instanceof Integer ? (Integer)value : Integer.valueOf(value.toString());
                return;

            case long_type:
                this.longValue = value == null || value instanceof Long ? (Long)value : Long.valueOf(value.toString());
                return;

            case double_type:
                this.doubleValue = value == null || value instanceof Double ? (Double)value : Double.valueOf(value.toString());
                return;

            case datetime_type:
                this.datetimeValue = value == null || value instanceof LocalDateTime ? (LocalDateTime)value : LocalDateTime.parse(value.toString());
                return;

            case date_type:
                this.dateValue = value == null || value instanceof LocalDate ? (LocalDate)value : LocalDate.parse(value.toString());
                return;

            case column_name_type:
                this.columnNameValue = value == null || value instanceof String ? (String)value : value.toString();
                return;

            case enum_type:
                this.enumValue = value == null || value instanceof String ? (String)value : value.toString();
                return;

            case string_list_type:
                this.stringListValue =  value == null || value instanceof ArrayList<?> ? (ArrayList)value : List.of(value.toString().split(","));
                return;

            case integer_list_type:
                this.integerListValue = value == null || value instanceof ArrayList<?> ? (ArrayList) value :
                        List.of(value.toString().split(",")).stream().map(val -> Long.valueOf(val)).collect(Collectors.toList());
                return;

            default:
                throw new DqoRuntimeException("Cannot retrieve a value for the type " + this.definition.getDataType());
        }
    }
}
