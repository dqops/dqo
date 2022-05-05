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
package ai.dqo.data;

import com.google.common.base.Strings;
import net.tlabs.tablesaw.parquet.TablesawParquet;
import tech.tablesaw.api.ColumnType;

/**
 * Tablesaw parquet format initialization. Performs one time parquet support initialization.
 */
public class ParquetSupport {
    private static boolean initialized;
    private static final Object lock = new Object();

    /**
     * Ensures that the Parquet format support for Tablesaw is initialized exactly once.
     * This method must be called in Parquet read and write operations.
     */
    public static void ensureInitialized() {
        synchronized (lock) {
            if (initialized) {
                return;
            }

            // because of some race condition in the Tablesaw, we need to use the columns for the first time exactly in the same order as they are declared in the ColumnType interface,
            // otherwise constants are created twice because of a circular dependency in the static initializers
            Object[] columnTypes = {
                    ColumnType.SHORT,
                    ColumnType.INTEGER,
                    ColumnType.LONG,
                    ColumnType.FLOAT,
                    ColumnType.BOOLEAN,
                    ColumnType.STRING,
                    ColumnType.DOUBLE,
                    ColumnType.LOCAL_DATE,
                    ColumnType.LOCAL_TIME,
                    ColumnType.LOCAL_DATE_TIME,
                    ColumnType.INSTANT,
                    ColumnType.TEXT,
                    ColumnType.SKIP};

            TablesawParquet.register();

            String currentHadoopHome = System.getenv("HADOOP_HOME");
            String currentHadoopHomeDir = System.getProperty("hadoop.home.dir");

            if (Strings.isNullOrEmpty(currentHadoopHome) || Strings.isNullOrEmpty(currentHadoopHomeDir)) {
                String dqoHome = System.getenv("DQO_HOME");
                System.setProperty("hadoop.home.dir", dqoHome);
            }

            initialized = true;
        }
    }
}
