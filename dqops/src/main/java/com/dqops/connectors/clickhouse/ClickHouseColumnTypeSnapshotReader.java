/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.clickhouse;

import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.google.common.base.Strings;

import java.util.Locale;

/**
 * Reads the datatype from raw string.
 */
public class ClickHouseColumnTypeSnapshotReader {

    /**
     * Creates a column type snapshot given a database column type with a possible length and precision/scale.
     * Supported types could be "INT", "FLOAT" - are just parsed as a data type. "Nullable(Int32), Nullable(String)" are parsed
     * as a data type INT32 or STRING.
     * @param dataType Data type snapshot object with the data type information.
     * @return Data type snapshot.
     */
    public static ColumnTypeSnapshotSpec fromType(String dataType) {
        ColumnTypeSnapshotSpec result = new ColumnTypeSnapshotSpec();
        if (Strings.isNullOrEmpty(dataType)) {
            return result;
        }

        int indexOfOpen = dataType.indexOf('(');
        int indexOfClose = dataType.indexOf(')');
        if (indexOfOpen < 0 || indexOfClose != dataType.length() - 1) {
            // just a type name like "INT", we can use it as is
            result.setColumnType(dataType.toUpperCase(Locale.ROOT));
        } else {
            result.setColumnType(dataType.substring(indexOfOpen + 1, indexOfClose).toUpperCase(Locale.ROOT));
        }

        return result;
    }

}
