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

package com.dqops.metadata.similarity;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

/**
 * Table similarity score holder at a connection level that stores a score used to find the most similar tables.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class ConnectionSimilarityIndexSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<ConnectionSimilarityIndexSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Dictionary of scores for each table, identified by a schema and table.")
    private Map<String, Map<String, TableSimilarityContainer>> tables = new LinkedHashMap<>();

    /**
     * Returns a dictionary of table similar scores.
     * WARNING: This list is always mutable and should be cloned before updating.
     * @return Dictionary of tables.
     */
    public Map<String, Map<String, TableSimilarityContainer>> getTables() {
        return tables;
    }

    /**
     * Sets a new dictionary of table similarity scores.
     * @param tables New dictionary of table similarity scores.
     */
    public void setTables(Map<String, Map<String, TableSimilarityContainer>> tables) {
        this.setDirtyIf(!Objects.equals(this.tables, tables));
        this.tables = tables;
    }

    /**
     * Retrieves a similarity score for a given table.
     * WARNING: The returned object is mutable and should not be modified in a read-only user home context.
     * @param physicalTableName Physical table name.
     * @return Similarity score holder or null when the table is not indexed.
     */
    public TableSimilarityContainer getSimilarityScore(PhysicalTableName physicalTableName) {
        Map<String, TableSimilarityContainer> schemaTablesMap = this.tables.get(physicalTableName.getSchemaName());
        if (schemaTablesMap == null) {
            return null;
        }

        return schemaTablesMap.get(physicalTableName.getTableName());
    }

    /**
     * Extracts the keys of all tables that are indexed.
     * @return List of all table identifiers that are indexed.
     */
    @JsonIgnore
    public List<PhysicalTableName> getAllTables() {
        List<PhysicalTableName> allTables = new ArrayList<>();
        for (Map.Entry<String, Map<String, TableSimilarityContainer>> schemaEntry : this.tables.entrySet()) {
            String schemaName = schemaEntry.getKey();
            for (String tableName : schemaEntry.getValue().keySet()) {
                allTables.add(new PhysicalTableName(schemaName, tableName));
            }
        }

        return allTables;
    }

    /**
     * Retrieves a table similarity container for a given table.
     * @param tableKey Table key.
     * @return Table similarity container or null.
     */
    public TableSimilarityContainer get(PhysicalTableName tableKey) {
        Map<String, TableSimilarityContainer> schemaTablesMap = this.tables.get(tableKey.getSchemaName());
        if (schemaTablesMap == null) {
            return null;
        }

        return schemaTablesMap.get(tableKey.getTableName());
    }

    /**
     * Stores a table similarity container for a given table.
     * @param tableKey Table key (physical table name).
     * @param tableSimilarityContainer Similarity score container.
     */
    public void set(PhysicalTableName tableKey, TableSimilarityContainer tableSimilarityContainer) {
        if (tableSimilarityContainer == null) {
            remove(tableKey);
            return;
        }

        Map<String, TableSimilarityContainer> schemaTablesMap = this.tables.get(tableKey.getSchemaName());
        if (schemaTablesMap == null) {
            schemaTablesMap = new LinkedHashMap<>();
            this.tables.put(tableKey.getSchemaName(), schemaTablesMap);
        }

        schemaTablesMap.put(tableKey.getTableName(), tableSimilarityContainer);
    }

    /**
     * Removes a table entry.
     * @param indexedTableKey Table key.
     */
    public void remove(PhysicalTableName indexedTableKey) {
        Map<String, TableSimilarityContainer> schemaTablesMap = this.tables.get(indexedTableKey.getSchemaName());
        if (schemaTablesMap == null) {
            return;
        }

        schemaTablesMap.remove(indexedTableKey.getTableName());
        if (schemaTablesMap.isEmpty()) {
            this.tables.remove(indexedTableKey.getSchemaName());
        }
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
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns a deep clone of this object.
     * @return Deep cloned instance.
     */
    public ConnectionSimilarityIndexSpec deepClone() {
        ConnectionSimilarityIndexSpec cloned = (ConnectionSimilarityIndexSpec) super.deepClone();
        cloned.tables = new LinkedHashMap<>();

        for (Map.Entry<String, Map<String, TableSimilarityContainer>> schemaEntry : this.tables.entrySet()) {
            LinkedHashMap<String, TableSimilarityContainer> clonedTables = new LinkedHashMap<>();
            for (Map.Entry<String, TableSimilarityContainer> tableEntry : schemaEntry.getValue().entrySet()) {
                clonedTables.put(tableEntry.getKey(), tableEntry.getValue().clone());
            }

            cloned.tables.put(schemaEntry.getKey(), clonedTables);
        }

        return cloned;
    }
}
