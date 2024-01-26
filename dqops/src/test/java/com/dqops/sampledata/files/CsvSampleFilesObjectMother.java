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
package com.dqops.sampledata.files;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Assertions;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
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
    public static CsvSampleFileContent loadTableCsv(String fileName) {
        try {
            File sampleFile = getFile(fileName);
            HashMap<String, String> columnPhysicalDataTypes = new HashMap<>();

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
                    .missingValueIndicator("")
                    .build();
            Table loadedTable = Table.read().csv(csvReadOptions);
            String tableName = fileName.substring(0, fileName.indexOf(".csv"));

            Table convertedTable = Table.create(tableName);
            for (Column<?> loadedColumn : loadedTable.columnArray()) {
                String annotatedColumName = loadedColumn.name();
                String[] nameTypeParts = annotatedColumName.split(":");
                Assertions.assertTrue(nameTypeParts.length == 2 || nameTypeParts.length == 3);
                String columnName = nameTypeParts[0];
                String typeName = nameTypeParts[1];
                String physicalDataType = nameTypeParts.length > 2 ? nameTypeParts[2] : null;
                ColumnType columnType = ColumnType.valueOf(typeName);

                if (!Strings.isNullOrEmpty(physicalDataType)) {
                    columnPhysicalDataTypes.put(columnName, physicalDataType);
                }

                if (loadedColumn instanceof StringColumn) {
                    StringColumn loadedStringColumn = (StringColumn) loadedColumn;
                    loadedColumn = loadedStringColumn.set(loadedStringColumn.isEqualTo(""), (String)null);
                }

                if (loadedColumn instanceof TextColumn) {
                    TextColumn loadedTextColumn = (TextColumn) loadedColumn;
                    loadedColumn = loadedTextColumn.set(loadedTextColumn.isEqualTo(""), (String)null);
                }

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

            return new CsvSampleFileContent(convertedTable, columnPhysicalDataTypes);
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

        CsvSampleFileContent csvSampleFileContent = loadTableCsv(fileName);
        Table loadedTable = csvSampleFileContent.getTable();
        long tableHash = TableHashingHelper.hashTable(loadedTable);
        String hashedTableName = loadedTable.name() + "_" + tableHash;
        SampleTableFromCsv sampleTable = new SampleTableFromCsv(loadedTable, hashedTableName, tableHash, csvSampleFileContent.getColumnPhysicalDataTypes());
		tables.put(fileName, sampleTable);
        return sampleTable;
    }

    /**
     * Returns a file object of a csv file in the dqo/sampledata folder.
     * @param fileName CSV file name inside the dqo/sampledata folder.
     * @return File object of a csv file.
     */
    public static File getFile(String fileName) {
        String sampleDataFolder = System.getenv("SAMPLE_DATA_FOLDER");
        Path sampleFilePath = Path.of(sampleDataFolder).resolve(fileName);
        File sampleFile = sampleFilePath.toFile();
        Assertions.assertTrue(Files.exists(sampleFilePath));
        return sampleFile;
    }

}
