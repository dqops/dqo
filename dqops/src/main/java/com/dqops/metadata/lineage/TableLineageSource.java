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

package com.dqops.metadata.lineage;

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.string.StringCompareUtility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Key object that identifies a source table by using the connection name, schema name and table name to identify.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TableLineageSource implements Cloneable, Comparable<TableLineageSource> {
    @JsonPropertyDescription("Connection name")
    private String connection;

    @JsonPropertyDescription("Schema name")
    private String schema;

    @JsonPropertyDescription("Table name")
    private String table;

    /**
     * Default constructor.
     */
    public TableLineageSource() {
    }

    /**
     * Creates a data lineage source object from the connection name, schema name, and table name.
     * @param connection Connection name.
     * @param schema Schema name.
     * @param table Table name.
     */
    public TableLineageSource(String connection, String schema, String table) {
        this.connection = connection;
        this.schema = schema;
        this.table = table;
    }

    /**
     * Returns a connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the connection name.
     * @param connection Connection name.
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Sets the schema name.
     * @return Schema name.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the schema name.
     * @param schema Schema name.
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Returns the table name.
     * @return Table name.
     */
    public String getTable() {
        return table;
    }

    /**
     * Set the table name.
     * @param table Table name.
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Returns a string representation of the object. The name is "schema.table", without file system encoding and without quoting the table name.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (Strings.isNullOrEmpty(this.connection)) {
            sb.append("");
        }
        else {
            sb.append(this.connection);
        }

        sb.append('.');

        if (Strings.isNullOrEmpty(this.schema)) {
            sb.append("");
        }
        else {
            sb.append(this.schema);
        }

        sb.append('.');

        if (Strings.isNullOrEmpty(this.table)) {
            sb.append("");
        }
        else {
            sb.append(this.table);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableLineageSource that = (TableLineageSource) o;
        return Objects.equals(this.connection, that.connection) && Objects.equals(this.schema, that.schema) && Objects.equals(this.table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.connection, this.schema, this.table);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public TableLineageSource clone() {
        try {
            TableLineageSource cloned = (TableLineageSource) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull TableLineageSource o) {
        int compareConnection = StringCompareUtility.compareNullableString(this.connection, o.connection);
        if (compareConnection != 0) {
            return compareConnection;
        }

        int compareSchema = StringCompareUtility.compareNullableString(this.schema, o.schema);
        if (compareSchema != 0) {
            return compareSchema;
        }

        return StringCompareUtility.compareNullableString(this.table, o.table);
    }

    /**
     * Creates a search filter for the table. A search filter is just a "schema.table".
     * @return Table search filter.
     */
    public String toTableSearchFilter() {
        return this.schema + "." + this.table;
    }

    public static class TableLineageSourceSampleFactory implements SampleValueFactory<TableLineageSource> {
        @Override
        public TableLineageSource createSample() {
            return new TableLineageSource("source_connection", "schema_name", "table_name");
        }
    }
}
