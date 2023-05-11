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
package ai.dqo.sampledata.files;

import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

/**
 * Tablesaw table hashing helper. Creates a hashcode for a whole table.
 */
public class TableHashingHelper {
    /**
     * Creates a hash value for the content of the table.
     * @param table Table to hash.
     * @return Hashed table.
     */
    public static long hashTable(Table table) {
        long hash = 0;

        int rowCount = table.rowCount();
        for (Column<?> column : table.columnArray()) {
            if (column.name() != null) {
                hash = hash * 29 + column.name().hashCode();
            }

            for( int i = 0; i < rowCount; i++) {
                Object cellValue = column.get(i);
                int cellHash = cellValue != null ? cellValue.hashCode() : -999;
                hash = hash * 29 + cellHash;
            }
        }

        return Math.abs(hash);
    }
}
