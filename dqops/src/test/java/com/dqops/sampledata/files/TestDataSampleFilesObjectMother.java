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

import com.dqops.sampledata.files.csv.HeaderEntry;
import com.google.common.base.Strings;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import org.junit.jupiter.api.Assertions;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.io.json.JsonReadOptions;
import tech.tablesaw.io.json.JsonReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides access to cached instances of sample data.
 */
public class TestDataSampleFilesObjectMother {
    private static final Map<String, SampleTableFromTestDataFile> tables = new HashMap<>();

    /**
     * The supported column types that are the same as the types in ColumnType class
     */
    private static ArrayList<ColumnType> columnTypesToDetect = new ArrayList<>() {{
        add(ColumnType.INTEGER);
        add(ColumnType.DOUBLE);
        add(ColumnType.LONG);
        add(ColumnType.LOCAL_DATE);
        add(ColumnType.LOCAL_DATE_TIME);
        add(ColumnType.LOCAL_TIME);
        add(ColumnType.INSTANT);
        add(ColumnType.STRING);
    }};

    /**
     * Loads a CSV file with sample data from the dqo/sampledata folder. Applies requested column types according to the
     * column names. The header row should have column names in the format: {column_name}:{requested_type}. Supported types
     * are the same as types in ColumnType class (case insensitive), for example: INTEGER, DOUBLE, LOCAL_DATE, LOCAL_DATE_TIME.
     * @param fileName CSV file name in the sampledata folder.
     * @return Loaded file.
     */
    public static TestDataSampleFileContent loadTableCsv(String fileName) {
        try {
            File sampleFile = SampleDataFilesProvider.getFile(fileName);

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

            return loadTable(fileName, loadedTable, tableName);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to read sample file " + fileName, ex);
        }
    }

    /**
     * Loads a JSON file with sample data from the dqo/sampledata folder. Applies requested column types according to the
     * column names. The header row should have column names in the format: {column_name}:{requested_type}. Supported types
     * are the same as types in ColumnType class (case insensitive), for example: INTEGER, DOUBLE, LOCAL_DATE, LOCAL_DATE_TIME.
     * @param fileName CSV file name in the sampledata folder.
     * @return Loaded file.
     */
    public static TestDataSampleFileContent loadTableJson(String fileName) {
        try {
            File sampleFile = SampleDataFilesProvider.getFile(fileName);

            JsonReadOptions jsonReadOptions = JsonReadOptions.builder(sampleFile)
                    .dateFormat(DateTimeFormatter.ISO_DATE)
                    .dateTimeFormat(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .build();

            Table loadedTable = new JsonReader().read(jsonReadOptions);
            String tableName = fileName.substring(0, fileName.indexOf(".json"));

            return loadTable(fileName, loadedTable, tableName, true);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to read sample file " + fileName, ex);
        }
    }

    /**
     * Loads a Parquet file with sample data from the dqo/sampledata folder. Applies requested column types according to the
     * column names. The header row should have column names in the format: {column_name}:{requested_type}. Supported types
     * are the same as types in ColumnType class (case insensitive), for example: INTEGER, DOUBLE, LOCAL_DATE, LOCAL_DATE_TIME.
     * @param fileName Parquet file name in the sampledata folder.
     * @return Loaded file.
     */
    public static TestDataSampleFileContent loadTableParquet(String fileName) {
        try {
            File sampleFile = SampleDataFilesProvider.getFile(fileName);

            TablesawParquetReadOptions parquetReadOptions = TablesawParquetReadOptions.builder(sampleFile)
                    .build();

            Table loadedTable = new TablesawParquetReader().read(parquetReadOptions);
            String tableName = fileName.substring(0, fileName.indexOf(".parquet"));

            return loadTable(fileName, loadedTable, tableName, true);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to read sample file " + fileName, ex);
        }
    }

    public static TestDataSampleFileContent loadTable(String fileName, Table loadedTable, String tableName) {
        return loadTable(fileName, loadedTable, tableName, false);
    }

    public static TestDataSampleFileContent loadTable(String fileName, Table loadedTable, String tableName, boolean preserveColumnNameWithType) {
        try {
            HashMap<String, String> columnPhysicalDataTypes = new HashMap<>();
            Table convertedTable = Table.create(tableName);
            for (Column<?> loadedColumn : loadedTable.columnArray()) {
                String annotatedColumName = loadedColumn.name();
                String[] nameTypeParts = annotatedColumName.split(":");
                Assertions.assertTrue(nameTypeParts.length == 2 || nameTypeParts.length == 3);
                String columnName = preserveColumnNameWithType ? annotatedColumName : nameTypeParts[0];
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

            return new TestDataSampleFileContent(convertedTable, columnPhysicalDataTypes);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to read sample file " + fileName, ex);
        }
    }

    /**
     * Loads CSV files with sample data from the dqo/sampledata/{path} folder. Applies requested column types according to the
     * column names. The header row from header.csv file should have column names in the format: {column_name}:{requested_type}. Supported types
     * are the same as types in ColumnType class (case insensitive), for example: INTEGER, DOUBLE, LOCAL_DATE, LOCAL_DATE_TIME.
     * @param folderName CSV file name in the sampledata folder.
     * @return Loaded multiple csv.
     */
    public static TestDataSampleFileContent loadTableFromFolderWithCsvFiles(String folderName) {
        try {
            List<File> sampleFiles = SampleDataFilesProvider.getCsvFiles(folderName);
            HashMap<String, String> columnPhysicalDataTypes = new HashMap<>();

            Table convertedTable = Table.create(folderName);

            File headerFile = sampleFiles.stream()
                    .filter(file -> file.getName().equals("header.csv"))
                    .findFirst()
                    .get();

            List<HeaderEntry> columnNamesWithTypes = getColumnNamesWithTypesFromHeaderFile(headerFile);

            sampleFiles.stream()
                    .filter(file -> !file.getName().equals("header.csv"))
                    .collect(Collectors.toList())
                    .forEach(file -> {

                        CsvReadOptions csvReadOptions = CsvReadOptions.builder(file)
                                .header(false)
                                .columnTypesToDetect(columnTypesToDetect)
                                .separator(',')
                                .dateFormat(DateTimeFormatter.ISO_DATE)
                                .dateTimeFormat(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                .missingValueIndicator("")
                                .build();

                        Table loadedTable = Table.read().csv(csvReadOptions);

                        int i = 0;
                        for (Column<?> loadedColumn : loadedTable.columnArray()) {

                            loadedColumn.setName(columnNamesWithTypes.get(i).getColumnName());

                            Column<?> convertedColumn = getConvertedColumn(
                                    loadedColumn,
                                    columnNamesWithTypes.get(i).getColumnType(),
                                    loadedTable,
                                    convertedTable,
                                    columnNamesWithTypes.get(i).getColumnName());
                            if(convertedColumn != null){
                                if(!convertedTable.containsColumn(columnNamesWithTypes.get(i).getColumnName())){
                                    convertedTable.addColumns(convertedColumn);
                                } else {
                                    // todo: add data
                                }
                            }
                            i++;
                        }
                    });

            return new TestDataSampleFileContent(convertedTable, columnPhysicalDataTypes);
        }
        catch (Exception ex) {
            throw new RuntimeException("Failed to read sample files from the path: " + folderName, ex);
        }
    }

    private static List<HeaderEntry> getColumnNamesWithTypesFromHeaderFile(File headerFile){

        String rawHeader = null;
        try {
            rawHeader = Files.readAllLines(headerFile.toPath()).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<HeaderEntry> columnNamesWithTypes = new ArrayList<>();

        Arrays.stream(rawHeader.split(",")).forEachOrdered(headerColumn -> {

            String[] nameTypeParts = headerColumn.split(":");
            Assertions.assertTrue(nameTypeParts.length == 2);
            String columnName = nameTypeParts[0];
            String typeName = nameTypeParts[1];
            ColumnType columnType = ColumnType.valueOf(typeName);

            HeaderEntry headerEntry = new HeaderEntry(columnName, columnType);
            columnNamesWithTypes.add(headerEntry);
        });

        return columnNamesWithTypes;
    }

    private static Column<?> getConvertedColumn(Column<?> loadedColumn,
                                                ColumnType columnType,
                                                Table loadedTable,
                                                Table convertedTable,
                                                String columnName){

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
            if(!convertedTable.columnNames().contains(copiedColumn.name())) {
                convertedTable.addColumns(copiedColumn);
            }
            return null;
        }

        Column<?> convertedColumn = columnType.create(columnName);
        for (int i = 0; i < loadedTable.rowCount(); i++) {
            String stringValue = loadedColumn.getString(i);
            convertedColumn.appendCell(stringValue); // conversion
        }

        return convertedColumn;
    }

    /**
     * Loads or returns a cached sample table loaded from a csv file in the dqo/sampledata folder.
     * @param fileName CSV file name inside the dqo/sampledata folder.
     * @return Sample data with a proposed physical table name based on the table hash.
     */
    public static SampleTableFromTestDataFile getSampleTableFromCsv(String fileName) {
        if (tables.containsKey(fileName)) {
            return tables.get(fileName);
        }
        TestDataSampleFileContent csvSampleFileContent = loadTableCsv(fileName);
        return getSampleTable(csvSampleFileContent, fileName);
    }

    /**
     * Loads or returns a cached sample table loaded from a csv file in the dqo/sampledata folder.
     * @param fileName CSV file name inside the dqo/sampledata folder.
     * @return Sample data with a proposed physical table name based on the table hash.
     */
    public static SampleTableFromTestDataFile getSampleTableFromJson(String fileName) {
        if (tables.containsKey(fileName)) {
            return tables.get(fileName);
        }
        TestDataSampleFileContent jsonSampleFileContent = loadTableJson(fileName);
        return getSampleTable(jsonSampleFileContent, fileName);
    }

    /**
     * Loads or returns a cached sample table loaded from a csv file in the dqo/sampledata folder.
     * @param fileName CSV file name inside the dqo/sampledata folder.
     * @return Sample data with a proposed physical table name based on the table hash.
     */
    public static SampleTableFromTestDataFile getSampleTableFromParquet(String fileName) {
        if (tables.containsKey(fileName)) {
            return tables.get(fileName);
        }
        TestDataSampleFileContent parquetSampleFileContent = loadTableParquet(fileName);
        return getSampleTable(parquetSampleFileContent, fileName);
    }

    /**
     * Loads or returns a cached sample table loaded from a csv file in the dqo/sampledata folder.
     * @param filesFolder folder of CSV file names inside the dqo/sampledata folder.
     * @return Sample data with a proposed physical table name based on the table hash.
     */
    public static SampleTableFromTestDataFile getSampleTableForFiles(String filesFolder) {
        if (tables.containsKey(filesFolder)) {
            return tables.get(filesFolder);
        }
        TestDataSampleFileContent csvSampleFileContent = loadTableFromFolderWithCsvFiles(filesFolder);
        return getSampleTable(csvSampleFileContent, filesFolder);
    }

    /**
     * Loads a sample table to the cache of tables and returns the sample table.
     * @param csvSampleFileContent Csv sample file content.
     * @param csvDataName Csv data name that will be used as a key in a table cache.
     * @return Sample table.
     */
    private static SampleTableFromTestDataFile getSampleTable(TestDataSampleFileContent csvSampleFileContent, String csvDataName){
        Table loadedTable = csvSampleFileContent.getTable();
        long tableHash = TableHashingHelper.hashTable(loadedTable);
        String hashedTableName = loadedTable.name() + "_" + tableHash;
        SampleTableFromTestDataFile sampleTable = new SampleTableFromTestDataFile(loadedTable, hashedTableName, tableHash, csvSampleFileContent.getColumnPhysicalDataTypes());
        tables.put(csvDataName, sampleTable);
        return sampleTable;
    }

}
