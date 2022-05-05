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

/**
 * Sample table holder. Stores a sample data for a single table, with a hash of the data.
 */
public class SampleTableFromCsv {
    private final Table table;
    private final String hashedTableName;
    private final long hash;

    /**
     * Creates a sample table holder.
     * @param table Table content.
     * @param hashedTableName Table name with a hash suffix, this is the name of the csv file (without the .csv file extension), followed by a hashcode of the table to make the tables unique.
     * @param hash Hash of the table content.
     */
    public SampleTableFromCsv(Table table, String hashedTableName, long hash) {
        this.table = table;
        this.hashedTableName = hashedTableName;
        this.hash = hash;
    }

    /**
     * Sample table content.
     * @return Sample table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Table name with a hash suffix. This is the name used to create the table in the target database for integration tests.
     * @return Hashed name.
     */
    public String getHashedTableName() {
        return hashedTableName;
    }

    /**
     * Table hash calculated from the content of the table.
     * @return Table hash.
     */
    public long getHash() {
        return hash;
    }
}
