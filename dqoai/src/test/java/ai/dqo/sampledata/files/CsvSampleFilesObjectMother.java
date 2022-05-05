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

import org.junit.jupiter.api.Assertions;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides access to cached instances of sample data.
 */
public class CsvSampleFilesObjectMother {
    private static final Map<String, SampleTableFromCsv> tables = new HashMap<>();

    /**
     * Loads a CSV file with sample data from the dqo/sampledata folder. Applies requested column types according to the
     * column names. The header row should have column names in the format: {column_name}:{requested_type}. Supported types
     * are the same as types in ColumnType class (case insensitive), for example: INTEGER, DOUBLE, LOCAL_DATE, LOCAL_DATE_TIME.
     * @param fileName CSV file name in the sampledata folder.
     * @return Loaded file.
     */
    public static Table loadTableCsv(String fileName) {
        try {
            String sampleDataFolder = System.getenv("SAMPLE_DATA_FOLDER");
            Path sampleFilePath = Path.of(sampleDataFolder).resolve(fileName);
            File sampleFile = sampleFilePath.toFile();
            Assertions.assertTrue(Files.exists(sampleFilePath));

            ArrayList<ColumnType> columnTypesToDetect = new ArrayList<>() {{
				add(ColumnType.INTEGER);
				add(ColumnType.DOUBLE);
				add(ColumnType.LONG);
				add(ColumnType.LOCAL_DATE);
				add(ColumnType.LOCAL_DATE_TIME);
				add(ColumnType.LOCAL_TIME);
				add(ColumnType.INSTANT);
				add(ColumnType.STRING);
            }};
            CsvReadOptions csvReadOptions = CsvReadOptions.builder(sampleFile)
                    .header(true)
                    .columnTypesToDetect(columnTypesToDetect)
                    .separator(',')
                    .dateFormat(DateTimeFormatter.ISO_DATE)
                    .dateTimeFormat(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .build();
            Table loadedTable = Table.read().csv(csvReadOptions);
            String tableName = fileName.substring(0, fileName.indexOf(".csv"));

            Table convertedTable = Table.create(tableName);
            for (Column<?> loadedColumn : loadedTable.columnArray()) {
                String annotatedColumName = loadedColumn.name();
                String[] nameTypeParts = annotatedColumName.split(":");
                Assertions.assertEquals(2, nameTypeParts.length);
                String columnName = nameTypeParts[0];
                String typeName = nameTypeParts[1];
                ColumnType columnType = ColumnType.valueOf(typeName);

                if (loadedColumn.type().equals(columnType)) {
                    Column<?> copiedColumn = loadedColumn.copy();
                    copiedColumn.setName(columnName);
                    convertedTable.addColumns(copiedColumn);
                    continue;
                }

                Column<?> convertedColumn = columnType.create(columnName);
                for (int i = 0; i < loadedTable.rowCount(); i++) {
                    String stringValue = loadedColumn.getString(i);
                    convertedColumn.appendCell(stringValue); // conversion
                }
                convertedTable.addColumns(convertedColumn);
            }

            return convertedTable;
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to read sample file " + fileName, ex);
        }
    }

    /**
     * Loads or returns a cached sample table loaded from a csv file in the dqo/sampledata folder.
     * @param fileName CSV file name inside the dqo/sampledata folder.
     * @return Sample data with a proposed physical table name based on the table hash.
     */
    public static SampleTableFromCsv getSampleTable(String fileName) {
        if (tables.containsKey(fileName)) {
            return tables.get(fileName);
        }

        Table loadedTable = loadTableCsv(fileName);
        long tableHash = TableHashingHelper.hashTable(loadedTable);
        String hashedTableName = loadedTable.name() + "_" + tableHash;
        SampleTableFromCsv sampleTable = new SampleTableFromCsv(loadedTable, hashedTableName, tableHash);
		tables.put(fileName, sampleTable);
        return sampleTable;
    }
}
