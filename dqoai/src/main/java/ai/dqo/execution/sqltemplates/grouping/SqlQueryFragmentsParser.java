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

package ai.dqo.execution.sqltemplates.grouping;

/**
 * SQL query component parser that analyzes queries and finds similar fragments.
 */
public interface SqlQueryFragmentsParser {
    /**
     * Parses a query into SQL query components.
     *
     * @param sql                     SQL query to divide.
     * @param actualValueColumnName   The name of the actual_value column name (if it is different than the default actual_value).
     * @param expectedValueColumnName The name of the expected_value column name (if it is different than the default expected_value).
     * @return Query parsed into query fragment components.
     */
    FragmentedSqlQuery parseQueryToComponents(String sql, String actualValueColumnName, String expectedValueColumnName);
}
