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

package com.dqops.connectors.duckdb.schema;

import java.util.List;

/**
 * Object that describes a single data type parsed from DuckDB data types. It is a single field, an array, or a schema (nested object).
 */
public class DuckDBField {
    private String fieldName;
    private String typeName;
    private Integer length;
    private Integer precision;
    private Integer scale;
    private boolean nullable = true;
    private boolean array;
    private boolean struct;
    private boolean map;
    private List<DuckDBField> nestedFields;

    public DuckDBField() {
    }

    public DuckDBField(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Returns the field name.
     * @return Field name.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name.
     * @param fieldName Field name.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Returns the data type name. It is just a "STRUCT" for a struct that contains nested fields.
     * @return Data type name.
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets the data type name.
     * @param typeName Data type name.
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Returns the field length (for varchar fields).
     * @return Field length.
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Set the field length.
     * @param length Field length.
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * Returns the precision of decimal numbers.
     * @return Precision of decimal numbers.
     */
    public Integer getPrecision() {
        return precision;
    }

    /**
     * Sets the precision of decimal numbers.
     * @param precision Precision of decimal numbers.
     */
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    /**
     * Returns the scale of decimal numbers.
     * @return Scale of decimal numbers.
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * Sets the scale of decimal numbers.
     * @param scale Scale of decimal numbers.
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * Returns true if the field is nullable. By default, all fields are nullable.
     * @return True when the field is nullable.
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * Sets the flag if the field is nullable.
     * @param nullable True when the field is nullable, false if not nullable.
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * Returns true if the field is an array of the data types.
     * @return The field is an array.
     */
    public boolean isArray() {
        return array;
    }

    /**
     * Sets a flag if the field is an array.
     * @param array True when it is an array.
     */
    public void setArray(boolean array) {
        this.array = array;
    }

    /**
     * Returns true if this field is a struct, which is a nested object with other nested fields.
     * @return True when this is a struct (nested) object.
     */
    public boolean isStruct() {
        return struct;
    }

    /**
     * Sets a flag to identify that this field is a struct. As a side effect, also changes the data type name to "STRUCT".
     * @param struct This field is a struct.
     */
    public void setStruct(boolean struct) {
        this.struct = struct;
        if (struct) {
            this.typeName = "STRUCT";
        }
    }

    /**
     * Returns true if this is a complex data type that is a MAP. A map has two nested data types, the type of the key and the type of the value.
     * @return True when it is a MAP type.
     */
    public boolean isMap() {
        return map;
    }

    /**
     * Sets a flag that turns this type to a map.
     * @param map Map type.
     */
    public void setMap(boolean map) {
        this.map = map;
        if (map) {
            this.typeName = "MAP";
        }
    }

    /**
     * Returns a list of nested fields.
     * @return List of nested fields.
     */
    public List<DuckDBField> getNestedFields() {
        return nestedFields;
    }

    /**
     * Replaces the list of nested fields in a structure.
     * @param nestedFields List of nested fields.
     */
    public void setNestedFields(List<DuckDBField> nestedFields) {
        assert this.struct || this.map;
        this.nestedFields = nestedFields;
    }
}
