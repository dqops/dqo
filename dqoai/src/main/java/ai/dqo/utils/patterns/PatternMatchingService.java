/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.utils.patterns;

import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Service dedicated to resolving patterns in connection, schema, table, column and check names, such as using wildcards (*).
 */
public interface PatternMatchingService {
    /**
     * Verify if the connection name fits into the provided connection pattern.
     * @param connectionPattern Connection pattern.
     * @param connectionName    Connection name to verify,
     * @return <code>true</code> if connection name matches the pattern, false otherwise.
     */
    boolean matchConnectionName(String connectionPattern, String connectionName);

    /**
     * Verify if the <code>schema.table</code> name fits into the provided schema-table search pattern.
     * @param schemaTablePattern Schema-table pattern.
     * @param schemaTableName    Schema-table name to verify,
     * @return <code>true</code> if schema-table name matches the pattern, false otherwise.
     */
    boolean matchSchemaTableName(String schemaTablePattern, PhysicalTableName schemaTableName);

    /**
     * Verify if the column name fits into the provided column pattern.
     * @param columnPattern Column pattern.
     * @param columnName    Column name to verify,
     * @return <code>true</code> if column name matches the pattern, false otherwise.
     */
    boolean matchColumnName(String columnPattern, String columnName);

    /**
     * Verify if the check name fits into the provided check pattern.
     * @param checkPattern Check pattern.
     * @param checkName    Check name to verify,
     * @return <code>true</code> if check name matches the pattern, false otherwise.
     */
    boolean matchCheckName(String checkPattern, String checkName);
}
