/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.parquetfiles;

import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
import com.dqops.data.errors.factory.ErrorsColumnNames;
import com.dqops.data.errors.factory.ErrorsTableFactoryImpl;
import com.dqops.data.errorsamples.factory.ErrorSamplesColumnNames;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactoryImpl;
import com.dqops.data.incidents.factory.IncidentsColumnNames;
import com.dqops.data.incidents.factory.IncidentsTableFactoryImpl;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.statistics.factory.StatisticsColumnNames;
import com.dqops.data.statistics.factory.StatisticsResultsTableFactoryImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Parquet files documentation model factory that creates a parquet files documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public class ParquetFilesDocumentationModelFactoryImpl implements ParquetFilesDocumentationModelFactory {
    private static final CommentFormatter commentFormatter = new CommentFormatter();
    private static final Map<ColumnType, String> columnTypeToHiveTypeMapping = new LinkedHashMap<>() {{
        put(ColumnType.BOOLEAN, "BOOLEAN");
        put(ColumnType.STRING, "STRING");
        put(ColumnType.TEXT, "STRING");
        put(ColumnType.DOUBLE, "DOUBLE");
        put(ColumnType.FLOAT, "FLOAT");
        put(ColumnType.INSTANT, "TIMESTAMP");
        put(ColumnType.INTEGER, "INTEGER");
        put(ColumnType.LOCAL_DATE, "DATE");
        put(ColumnType.LOCAL_DATE_TIME, "TIMESTAMP");
        put(ColumnType.LOCAL_TIME, "INTERVAL");
        put(ColumnType.LONG, "BIGINT");
        put(ColumnType.SHORT, "SMALLINT");
    }};

    /**
     * Create a parquet file documentation models.
     *
     * @return Parquet file documentation models.
     */
    @Override
    public List<ParquetFileDocumentationModel> createDocumentationForParquetFiles() {
        List<ParquetFileDocumentationModel> parquetFileDocumentationModels = new ArrayList<>();
        Map<Table, Class<?>> parquetFilesClasses = new LinkedHashMap<>();

        CheckResultsTableFactoryImpl checkResultsTableFactory = new CheckResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
        Table checkResultsTable = checkResultsTableFactory.createEmptyCheckResultsTable("check_results");
        parquetFilesClasses.put(checkResultsTable, CheckResultsColumnNames.class);

        ErrorsTableFactoryImpl errorsTableFactory = new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
        Table errorsTable = errorsTableFactory.createEmptyErrorsTable("errors");
        parquetFilesClasses.put(errorsTable, ErrorsColumnNames.class);

        IncidentsTableFactoryImpl incidentsTableFactory = new IncidentsTableFactoryImpl();
        Table incidentsTable = incidentsTableFactory.createEmptyIncidentsTable("incidents");
        parquetFilesClasses.put(incidentsTable, IncidentsColumnNames.class);

        SensorReadoutsTableFactoryImpl sensorReadoutsTableFactory = new SensorReadoutsTableFactoryImpl();
        Table sensorReadoutsTable = sensorReadoutsTableFactory.createEmptySensorReadoutsTable("sensor_readouts");
        parquetFilesClasses.put(sensorReadoutsTable, SensorReadoutsColumnNames.class);

        StatisticsResultsTableFactoryImpl statisticsResultsTableFactory = new StatisticsResultsTableFactoryImpl();
        Table statisticsResultsTable = statisticsResultsTableFactory.createEmptyStatisticsTable("statistics");
        parquetFilesClasses.put(statisticsResultsTable, StatisticsColumnNames.class);

        ErrorSamplesTableFactoryImpl errorSamplesTableFactory = new ErrorSamplesTableFactoryImpl();
        Table errorSamplesTable = errorSamplesTableFactory.createEmptyErrorSamplesTable("error_samples");
        parquetFilesClasses.put(errorSamplesTable, ErrorSamplesColumnNames.class);

        for (Map.Entry<Table, Class<?>> parquetFilesClass : parquetFilesClasses.entrySet()) {
            parquetFileDocumentationModels.add(generateParquetFileDocumentationModel(parquetFilesClass.getKey(), parquetFilesClass.getValue()));
        }
        return parquetFileDocumentationModels;
    }

    /**
     * Completes description for each column name.
     *
     * @param table            Table generate in previous step. Contain columns names.
     * @param classWithDetails Class contains columns descriptions.
     * @return Parquet file documentation model.
     */
    private ParquetFileDocumentationModel generateParquetFileDocumentationModel(Table table, Class<?> classWithDetails) {
        ParquetFileDocumentationModel parquetFileDocumentationModel = new ParquetFileDocumentationModel();
        List<ParquetColumnDetailsDocumentationModel> parquetColumnDetailsDocumentationModels = new ArrayList<>();

        parquetFileDocumentationModel.setParquetFileFullName(table.name());
        Map<String, String> columnsDescriptions = getFieldsDescriptions(parquetFileDocumentationModel, classWithDetails);

        List<String> columnsNames = table.columnNames();
        for (String columnName : columnsNames) {
            ParquetColumnDetailsDocumentationModel parquetColumnDetailsDocumentationModel = new ParquetColumnDetailsDocumentationModel();
            parquetColumnDetailsDocumentationModel.setColumnName(columnName);
            ColumnType columnType = table.column(columnName).type();
            String hiveColumnTypeName = columnTypeToHiveTypeMapping.get(columnType);
            parquetColumnDetailsDocumentationModel.setColumnType(hiveColumnTypeName);
            String columnDescription = columnsDescriptions.get(columnName);
            if (columnDescription != null) {
                parquetColumnDetailsDocumentationModel.setColumnDescription(columnDescription);
                parquetColumnDetailsDocumentationModels.add(parquetColumnDetailsDocumentationModel);
                continue;
            }
            parquetColumnDetailsDocumentationModels.add(parquetColumnDetailsDocumentationModel);
        }
        parquetFileDocumentationModel.setParquetColumnDetailsDocumentationModels(parquetColumnDetailsDocumentationModels);

        return parquetFileDocumentationModel;
    }

    /**
     * Parses columns names with their descriptions.
     *
     * @param parquetFileDocumentationModel Model needs class description complete.
     * @param classWithDetails              Class contains columns descriptions.
     * @return Map where key is column name and value is column description.
     */
    private Map<String, String> getFieldsDescriptions(ParquetFileDocumentationModel parquetFileDocumentationModel, Class<?> classWithDetails) {
        Map<String, String> columnsDescriptions = new LinkedHashMap<>();
        Map<String, String> columnsNameDictionary = new LinkedHashMap<>();

        ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(classWithDetails);
        if (classJavadoc != null) {
            if (classJavadoc.getComment() != null) {
                String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                parquetFileDocumentationModel.setParquetFileDescription(formattedClassComment);
                parquetFileDocumentationModel.setParquetFileShortDescription(getShortDescription(formattedClassComment));
            }
            while (classWithDetails != null) {
                for (Field field : classWithDetails.getDeclaredFields()) {
                    try {
                        String variableValue = field.get(null).toString();
                        columnsNameDictionary.put(field.getName(), variableValue);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                for (FieldJavadoc fieldJavadoc : classJavadoc.getFields()) {
                    columnsDescriptions.put(columnsNameDictionary.get(fieldJavadoc.getName()), fieldJavadoc.getComment().toString());
                }
                classWithDetails = classWithDetails.getSuperclass();
                if (classWithDetails != null) {
                    classJavadoc = RuntimeJavadoc.getJavadoc(classWithDetails);
                }
            }
        }
        return columnsDescriptions;
    }

    private String getShortDescription(String description) {
        String[] splitDescription = description.split("\\.");
        return splitDescription[0] + ".";
    }
}

