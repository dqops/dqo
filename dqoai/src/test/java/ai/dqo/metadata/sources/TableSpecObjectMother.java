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
package ai.dqo.metadata.sources;

/**
 * Object mother for TableSpec.
 */
public class TableSpecObjectMother {
    /**
     * Creates a table schema for a given schema.table name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @return Table spec.
     */
    public static TableSpec create(String schemaName, String tableName) {
        return new TableSpec() {{
			setPhysicalTableName(new PhysicalTableName() {{
				setSchemaName(schemaName);
				setTableName(tableName);
            }});
        }};
    }

    /**
     * Adds a column to a table.
     * @param tableSpec Target table spec to add the column.
     * @param columnName Column name.
     * @param columnSpec Column spec.
     * @return Column spec that was passed (for chaining).
     */
    public static ColumnSpec addColumn(TableSpec tableSpec, String columnName, ColumnSpec columnSpec) {
        tableSpec.getColumns().put(columnName, columnSpec);
        return columnSpec;
    }
}
