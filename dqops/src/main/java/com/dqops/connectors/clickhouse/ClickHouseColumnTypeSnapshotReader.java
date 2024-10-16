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
