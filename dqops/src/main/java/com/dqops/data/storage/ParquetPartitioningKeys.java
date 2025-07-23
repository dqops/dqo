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

/**
 * Constants with the partitioning column names used to store parquet files in a hive compliant partition folders.
 */
public class ParquetPartitioningKeys {
    /**
     * Connection name column.
     */
    public static final String CONNECTION = "c";

    /**
     * Target object name (schema.table for tables) name.
     */
    public static final String SCHEMA_TABLE = "t";

    /**
     * Month as a date of the first day of the month, for example: m=2022-03-10
     */
    public static final String MONTH = "m";
}
