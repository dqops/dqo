/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage;

import com.google.common.base.Strings;
import net.tlabs.tablesaw.parquet.TablesawParquet;
import tech.tablesaw.api.ColumnType;

/**
 * Tablesaw parquet format initialization. Performs one time parquet support initialization.
 */
public class TablesawParquetSupportFix {
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
