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
package ai.dqo.connectors.jdbc;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.io.jdbc.SqlResultSetReader;

/**
 * Initializes the format of non-standard JDBC data types. Performs one-time initialization of data type support. */
public class JdbcTypeColumnMapping {

    private static boolean isInitializedJdbc;
    private static final Object lock = new Object();

    /**
     * Ensures that data type format support for JDB is initialized exactly once.
     * This method must be called in read and write operations of non-standard data types for JDBC.
     */
    public static void ensureInitializedJdbc() {
        synchronized (lock) {
            if (isInitializedJdbc) {
                return;
            }

            SqlResultSetReader.mapJdbcTypeToColumnType(-155, ColumnType.INSTANT);
            SqlResultSetReader.mapJdbcTypeToColumnType(-101, ColumnType.INSTANT);

            isInitializedJdbc = true;
        }
    }
}
