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
package com.dqops.metadata.sources;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Stores the column data type captured at the time of the table metadata import.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ColumnTypeSnapshotSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<ColumnTypeSnapshotSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    /**
     * Default constructor.
     */
    public ColumnTypeSnapshotSpec() {
    }

    /**
     * Creates a column type.
     * @param columnType Column type.
     */
    public ColumnTypeSnapshotSpec(String columnType) {
        this.columnType = columnType;
    }

    /**
     * Creates a column type.
     * @param columnType Column type.
     * @param nullable Is nullable.
     */
    public ColumnTypeSnapshotSpec(String columnType, Boolean nullable) {
        this.columnType = columnType;
        this.nullable = nullable;
    }

    /**
     * Creates a column type.
     * @param columnType Column type.
     * @param length Length.
     */
    public ColumnTypeSnapshotSpec(String columnType, Integer length) {
        this.columnType = columnType;
        this.length = length;
    }

    /**
     * Creates a column type.
     * @param columnType Column type.
     * @param nullable Is nullable.
     * @param length Maximum length.
     */
    public ColumnTypeSnapshotSpec(String columnType, Boolean nullable, Integer length) {
        this.columnType = columnType;
        this.nullable = nullable;
        this.length = length;
    }

    /**
     * Creates a column type.
     * @param columnType Column type.
     * @param nullable Is nullable.
     * @param precision Decimal numeric precision.
     * @param scale Decimal numeric scale.
     */
    public ColumnTypeSnapshotSpec(String columnType, Boolean nullable, Integer precision, Integer scale) {
        this.columnType = columnType;
        this.nullable = nullable;
        this.precision = precision;
        this.scale = scale;
    }

    @JsonPropertyDescription("Column data type using the monitored database type names.")
    private String columnType;

    @JsonPropertyDescription("Column is nullable.")
    private Boolean nullable;

    @JsonPropertyDescription("Maximum length of text and binary columns.")
    private Integer length;

    @JsonPropertyDescription("Precision of a numeric (decimal) data type.")
    private Integer precision;

    @JsonPropertyDescription("Scale of a numeric (decimal) data type.")
    private Integer scale;

    /**
     * Creates a column type snapshot given a database column type with a possible length and precision/scale.
     * Supported types could be "INT", "FLOAT" - are just parsed as a data type. "STRING(100), VARCHAR(100)" are parsed
     * as a data type STRING or VARCHAR with a 100 length. Finally types like NUMBER(18,2) are parsed as a "NUMBER"
     * with precision 18 and scale 2.
     * @param dataType Data type snapshot object with the data type information.
     * @return Data type snapshot.
     */
    public static ColumnTypeSnapshotSpec fromType(String dataType) {
        ColumnTypeSnapshotSpec result = new ColumnTypeSnapshotSpec();
        if (Strings.isNullOrEmpty(dataType)) {
            return result;
        }

        int indexOfOpen = dataType.indexOf('(');
        int indexOfClose = dataType.indexOf(')');
        if (indexOfOpen < 0 || indexOfClose != dataType.length() - 1) {
            // just a type name like "INT", we can use it as is
            result.setColumnType(dataType.toUpperCase(Locale.ROOT));
        } else {
            result.setColumnType(dataType.substring(0, indexOfOpen));
            String numbersSection = dataType.substring(indexOfOpen + 1, indexOfClose);
            String[] numberComponents = numbersSection.split(",");
            if (numberComponents.length == 1) {
                try {
                    result.setLength(Integer.parseInt(numbersSection.trim()));
                }
                catch (NumberFormatException ex) {
                    // ignore, probably "MAX" in SQL Server
                    result.setColumnType(dataType);
                }
            }
            else {
                result.setPrecision(Integer.parseInt(numberComponents[0].trim()));
                result.setScale(Integer.parseInt(numberComponents[1].trim()));
            }
        }

        return result;
    }

    /**
     * Column type name.
     * @return Column type name.
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * Sets the column type.
     * @param columnType Column type.
     */
    public void setColumnType(String columnType) {
		this.setDirtyIf(!Objects.equals(this.columnType, columnType));
        this.columnType = columnType;
    }

    /**
     * Returns the nullable flag of the column.
     * @return Column is nullable.
     */
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * Sets the nullable flag of a column.
     * @param nullable Column is nullable.
     */
    public void setNullable(Boolean nullable) {
		this.setDirtyIf(!Objects.equals(this.nullable, nullable));
        this.nullable = nullable;
    }

    /**
     * Returns the length of a column.
     * @return Column length.
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Sets the column length.
     * @param length Column length.
     */
    public void setLength(Integer length) {
		this.setDirtyIf(!Objects.equals(this.length, length));
        this.length = length;
    }

    /**
     * Returns a precision of decimal columns.
     * @return Column precision.
     */
    public Integer getPrecision() {
        return precision;
    }

    /**
     * Sets the column precision.
     * @param precision Column precision.
     */
    public void setPrecision(Integer precision) {
        this.setDirtyIf(!Objects.equals(this.precision, precision));
        this.precision = precision;
    }

    /**
     * Returns a scale of a column (used for decimal columns and some high resolution datetime or time columns).
     * @return Column scale.
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * Sets the scale of a column.
     * @param scale Column scale.
     */
    public void setScale(Integer scale) {
		this.setDirtyIf(!Objects.equals(this.scale, scale));
        this.scale = scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnTypeSnapshotSpec that = (ColumnTypeSnapshotSpec) o;
        return Objects.equals(columnType, that.columnType) && Objects.equals(nullable, that.nullable) && Objects.equals(length, that.length) && Objects.equals(scale, that.scale) && Objects.equals(precision, that.precision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnType, nullable, length, scale, precision);
    }

    /**
     * Calculate a 64-bit hash of the data type, including all values.
     * @return 64-bit hash.
     */
    public long hashCode64() {
        List<HashCode> hashCodes = new ArrayList<>() {{
            add(getColumnType() != null ? Hashing.farmHashFingerprint64().hashString(getColumnType(), StandardCharsets.UTF_8) : HashCode.fromLong(-1L));
            add(getLength() != null ? HashCode.fromLong(getLength()) : HashCode.fromLong(-1L));
            add(getScale() != null ? HashCode.fromLong(getScale()) : HashCode.fromLong(-1L));
            add(getPrecision() != null ? HashCode.fromLong(getPrecision()) : HashCode.fromLong(-1L));
            add(getNullable() != null ? HashCode.fromLong(getNullable() ? 1L : 0L) : HashCode.fromLong(-1L));
        }};

        return Math.abs(Hashing.combineOrdered(hashCodes).asLong()); // we return only positive hashes which limits the hash space to 2^63, but positive hashes are easier for users
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public ColumnTypeSnapshotSpec deepClone() {
        ColumnTypeSnapshotSpec cloned = (ColumnTypeSnapshotSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a clone of the object that will be passed to the sensor runner. Configurable variables are expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Cloned and expanded copy of the object.
     */
    public ColumnTypeSnapshotSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        ColumnTypeSnapshotSpec cloned = this.deepClone();
        if (cloned.columnType != null) {
            cloned.columnType = cloned.columnType.toUpperCase(Locale.ROOT);
        }
        cloned.columnType = secretValueProvider.expandValue(cloned.columnType, lookupContext);
        return cloned;
    }

    @Override
    public ColumnTypeSnapshotSpec clone() {
        try {
            ColumnTypeSnapshotSpec clone = (ColumnTypeSnapshotSpec) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class ColumnTypeSnapshotSpecSampleFactory implements SampleValueFactory<ColumnTypeSnapshotSpec> {
        @Override
        public ColumnTypeSnapshotSpec createSample() {
            return new ColumnTypeSnapshotSpec("string", false, 256);
        }
    }
}
