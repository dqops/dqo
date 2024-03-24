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
