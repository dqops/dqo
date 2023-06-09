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
package ai.dqo.utils.docs.parquetfiles;

import ai.dqo.data.checkresults.factory.CheckResultsColumnNames;
import ai.dqo.data.checkresults.factory.CheckResultsTableFactoryImpl;
import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.errors.factory.ErrorsTableFactoryImpl;
import ai.dqo.data.incidents.factory.IncidentsColumnNames;
import ai.dqo.data.incidents.factory.IncidentsTableFactoryImpl;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import ai.dqo.data.statistics.factory.StatisticsColumnNames;
import ai.dqo.data.statistics.factory.StatisticsResultsTableFactoryImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.FieldJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import tech.tablesaw.api.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parquet files documentation model factory that creates a parquet files documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public class ParquetFilesDocumentationModelFactoryImpl implements ParquetFilesDocumentationModelFactory {
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    /**
     * Create a parquet file documentation models.
     *
     * @return Parquet file documentation models.
     */
    @Override
    public List<ParquetFileDocumentationModel> createDocumentationForParquetFiles() {
        List<ParquetFileDocumentationModel> parquetFileDocumentationModels = new ArrayList<>();
        Map<Table, Class<?>> parquetFilesClasses = new HashMap<>();

        CheckResultsTableFactoryImpl checkResultsTableFactory = new CheckResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
        Table checkResultsTable = checkResultsTableFactory.createEmptyCheckResultsTable("CheckResultsTable");
        parquetFilesClasses.put(checkResultsTable, CheckResultsColumnNames.class);

        ErrorsTableFactoryImpl errorsTableFactory = new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
        Table errorsTable = errorsTableFactory.createEmptyErrorsTable("ErrorsTable");
        parquetFilesClasses.put(errorsTable, ErrorsColumnNames.class);

        IncidentsTableFactoryImpl incidentsTableFactory = new IncidentsTableFactoryImpl();
        Table incidentsTable = incidentsTableFactory.createEmptyIncidentsTable("IncidentsTable");
        parquetFilesClasses.put(incidentsTable, IncidentsColumnNames.class);

        SensorReadoutsTableFactoryImpl sensorReadoutsTableFactory = new SensorReadoutsTableFactoryImpl();
        Table sensorReadoutsTable = sensorReadoutsTableFactory.createEmptySensorReadoutsTable("SensorReadoutsTable");
        parquetFilesClasses.put(sensorReadoutsTable, SensorReadoutsColumnNames.class);

        StatisticsResultsTableFactoryImpl statisticsResultsTableFactory = new StatisticsResultsTableFactoryImpl();
        Table statisticsResultsTable = statisticsResultsTableFactory.createEmptyStatisticsTable("StatisticsResultsTable");
        parquetFilesClasses.put(statisticsResultsTable, StatisticsColumnNames.class);

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
            parquetColumnDetailsDocumentationModel.setColumnType(table.column(columnName).type().toString());
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
        Map<String, String> columnsDescriptions = new HashMap<>();
        Map<String, String> columnsNameDictionary = new HashMap<>();

        ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(classWithDetails);
        if (classJavadoc != null) {
            if (classJavadoc.getComment() != null) {
                String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                parquetFileDocumentationModel.setParquetFileDescription(formattedClassComment);
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
}

