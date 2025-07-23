/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.sqltemplates.grouping;

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
