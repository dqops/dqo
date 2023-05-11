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
package ai.dqo.connectors.bigquery;

import ai.dqo.connectors.ConnectionQueryException;
import com.google.cloud.bigquery.*;
import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Support class that is responsible for executing SQL queries on a given connection object.
 */
@Component
public class BigQuerySqlRunner {
    /**
     * Executes a query and returns a data frame with the results.
     * @param connection Connection object.
     * @param sql SQL string to execute.
     * @return Table object.
     */
    public Table executeQuery(BigQuerySourceConnection connection, String sql) {
        try {
            BigQueryInternalConnection bigQueryInternalConnection = connection.getBigQueryInternalConnection();

            String jobProjectId = bigQueryInternalConnection.getBillingProjectId();
            QueryJobConfiguration queryJobConfiguration = QueryJobConfiguration.newBuilder(sql).build();
            JobId.Builder jobBuilder = JobId.newBuilder();
            if (!Strings.isNullOrEmpty(jobProjectId)) {
                jobBuilder = jobBuilder.setProject(jobProjectId);
            }
            JobId jobId = jobBuilder.build();
            BigQuery bigQueryService = bigQueryInternalConnection.getBigQueryClient();

            TableResult tableResult = bigQueryService.query(queryJobConfiguration, jobId);
            Schema tableSchema = tableResult.getSchema();
            Table table = Table.create(sql); // the name of the table is the SQL that was executed, for simpler debugging
            List<Column<?>> columns = createColumnsFromBigQuerySchema(tableSchema);
            table.addColumns(columns.toArray(size -> new Column<?>[size]));

            for (FieldValueList bqRow : tableResult.iterateAll()) {
                Row row = table.appendRow();

                for (int colIndex = 0; colIndex < bqRow.size(); colIndex++) {
                    FieldValue fieldValue = bqRow.get(colIndex);
                    if (fieldValue.isNull()) {
                        continue; // no value
                    }

                    Field field = tableSchema.getFields().get(colIndex);
                    LegacySQLTypeName legacyFieldType = field.getType();
                    StandardSQLTypeName standardFieldType = legacyFieldType.getStandardType();

                    switch (standardFieldType) {
                        case BOOL:
                            row.setBoolean(colIndex, fieldValue.getBooleanValue());
                            break;
                        case INT64:
                            row.setLong(colIndex, fieldValue.getLongValue());
                            break;
                        case FLOAT64:
                            row.setDouble(colIndex, fieldValue.getDoubleValue());
                            break;
                        case NUMERIC:
                        case BIGNUMERIC:
                            row.setDouble(colIndex, fieldValue.getNumericValue().doubleValue());
                            break;
                        case STRING:
                            row.setString(colIndex, fieldValue.getStringValue());
                            break;
                        case BYTES:
                            row.setText(colIndex, "0x" + new String(Hex.encodeHex(fieldValue.getBytesValue())));
                            break;
                        case STRUCT:
                            row.setText(colIndex, fieldValue.getRecordValue().toString());
                            break;
                        case ARRAY:
                            row.setText(colIndex, fieldValue.getRepeatedValue().toString());
                            break;
                        case GEOGRAPHY:
                            row.setText(colIndex, fieldValue.getValue().toString());
                            break;
                        case TIMESTAMP:
                            row.setInstant(colIndex, Instant.ofEpochMilli(fieldValue.getTimestampValue() / 1000));
                            break;
                        case DATE:
                            row.setDate(colIndex, LocalDate.parse(fieldValue.getStringValue(), DateTimeFormatter.ISO_DATE));
                            break;
                        case TIME:
                            row.setTime(colIndex, LocalTime.ofInstant(Instant.ofEpochMilli(fieldValue.getTimestampValue() / 1000), ZoneOffset.UTC));
                            break;
                        case DATETIME:
                            row.setDateTime(colIndex, LocalDateTime.ofInstant(Instant.ofEpochMilli(fieldValue.getTimestampValue() / 1000), ZoneOffset.UTC));
                            break;
                        default:
                            throw new RuntimeException("Unknown column type: " + standardFieldType.name());
                    }
                }
            }

            return table;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(String.format("Failed to execute query: %s, error: %s", sql, ex.getMessage()), ex);
        }
    }

    /**
     * Executes an SQL statement that does not return results (DML or DDL).
     * @param connection Connection object.
     * @param sql SQL string to execute.
     * @return Number of rows affected.
     */
    public long executeStatement(BigQuerySourceConnection connection, String sql) {
        try {
            BigQueryInternalConnection bigQueryInternalConnection = connection.getBigQueryInternalConnection();

            String jobProjectId = bigQueryInternalConnection.getBillingProjectId();
            QueryJobConfiguration queryJobConfiguration = QueryJobConfiguration.newBuilder(sql).build();
            JobId.Builder jobBuilder = JobId.newBuilder();
            if (!Strings.isNullOrEmpty(jobProjectId)) {
                jobBuilder = jobBuilder.setProject(jobProjectId);
            }
            JobId jobId = jobBuilder.build();
            BigQuery bigQueryService = bigQueryInternalConnection.getBigQueryClient();

            TableResult tableResult = bigQueryService.query(queryJobConfiguration, jobId);
            return tableResult.getTotalRows();
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(String.format("Failed to execute query: %s, error: %s", sql, ex.getMessage()), ex);
        }
    }

    /**
     * Creates a list of columns for a bigquery result schema.
     * @param tableSchema Table schema.
     * @return List of columns.
     */
    public List<Column<?>> createColumnsFromBigQuerySchema(Schema tableSchema) {
        List<Column<?>> columns = new ArrayList<>();
        for (Field field : tableSchema.getFields()) {
            LegacySQLTypeName legacyFieldType = field.getType();
            StandardSQLTypeName standardFieldType = legacyFieldType.getStandardType();

            switch (standardFieldType) {
                case BOOL:
                    columns.add(tech.tablesaw.api.BooleanColumn.create(field.getName()));
                    break;
                case INT64:
                    columns.add(tech.tablesaw.api.LongColumn.create(field.getName()));
                    break;
                case FLOAT64:
                case NUMERIC:
                case BIGNUMERIC:
                    columns.add(tech.tablesaw.api.DoubleColumn.create(field.getName()));
                    break;
                case STRING:
                case BYTES:
                case STRUCT:
                case ARRAY:
                case GEOGRAPHY:
                    columns.add(tech.tablesaw.api.TextColumn.create(field.getName()));
                    break;
                case TIMESTAMP:
                    columns.add(tech.tablesaw.api.InstantColumn.create(field.getName()));
                    break;
                case DATE:
                    columns.add(tech.tablesaw.api.DateColumn.create(field.getName()));
                    break;
                case TIME:
                    columns.add(tech.tablesaw.api.TimeColumn.create(field.getName()));
                    break;
                case DATETIME:
                    columns.add(tech.tablesaw.api.DateTimeColumn.create(field.getName()));
                    break;
                default:
                    throw new RuntimeException("Unknown column type: " + standardFieldType.name());
            }
        }
        return columns;
    }
}
