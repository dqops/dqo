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
package com.dqops.sensors.table.schema;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.SourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.grouping.TableMetadataSensorExecutor;
import com.dqops.execution.sensors.progress.ExecutingSqlOnConnectionEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.execution.sensors.runners.AbstractSensorRunner;
import com.dqops.execution.sensors.runners.GenericSensorResultsFactory;
import com.dqops.metadata.sources.*;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.logging.UserErrorLogger;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sensor runner that introspects the metadata on a table and calculates a hash of the column names and their data types, the hash does not depend on the order of columns in the monitored table.
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableColumnTypesHashSensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = TableColumnTypesHashSensorRunner.class.getName();
    private ConnectionProviderRegistry connectionProviderRegistry;
    private TableMetadataSensorExecutor tableMetadataSensorExecutor;
    private UserErrorLogger userErrorLogger;

    /**
     * Dependency injection constructor that receives all dependencies.
     * @param connectionProviderRegistry Connection provider registry, used to retrieve a connector instance for the target data source.
     * @param defaultTimeZoneProvider The default time zone provider.
     * @param tableMetadataSensorExecutor Table metadata shared executor.
     * @param userErrorLogger Check execution logger.
     */
    @Autowired
    public TableColumnTypesHashSensorRunner(ConnectionProviderRegistry connectionProviderRegistry,
                                            DefaultTimeZoneProvider defaultTimeZoneProvider,
                                            TableMetadataSensorExecutor tableMetadataSensorExecutor,
                                            UserErrorLogger userErrorLogger) {
        super(defaultTimeZoneProvider);
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.tableMetadataSensorExecutor = tableMetadataSensorExecutor;
        this.userErrorLogger = userErrorLogger;
    }

    /**
     * Prepares a sensor for execution. SQL templated sensors will render the SQL template, filled with the table and column names.
     *
     * @param executionContext    Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinition    Sensor definition that was found in the dqo home or the user home.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    @Override
    public SensorPrepareResult prepareSensor(ExecutionContext executionContext,
                                             SensorExecutionRunParameters sensorRunParameters,
                                             SensorDefinitionFindResult sensorDefinition,
                                             SensorExecutionProgressListener progressListener) {
        return new SensorPrepareResult(sensorRunParameters, sensorDefinition, this.tableMetadataSensorExecutor, this, false);
    }

    /**
     * Transforms the sensor result that was captured by the sensor executor. This method performs de-grouping of grouped sensors that were executed as multiple SQL queries merged into one big query.
     *
     * @param executionContext             Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param groupedSensorExecutionResult Sensor execution result with the data retrieved from the data source. It will be adapted to a sensor result for one sensor.
     * @param sensorPrepareResult          Original sensor prepare results for this sensor. Contains also the sensor run parameters.
     * @param progressListener             Progress listener that receives events when the sensor is executed.
     * @param jobCancellationToken         Job cancellation token, may cancel a running query.
     * @return Sensor result for one sensor.
     */
    @Override
    public SensorExecutionResult extractSensorResults(ExecutionContext executionContext,
                                                      GroupedSensorExecutionResult groupedSensorExecutionResult,
                                                      SensorPrepareResult sensorPrepareResult,
                                                      SensorExecutionProgressListener progressListener,
                                                      JobCancellationToken jobCancellationToken) {
        SensorExecutionRunParameters sensorRunParameters = sensorPrepareResult.getSensorRunParameters();
        PhysicalTableName physicalTableName = sensorRunParameters.getTable().getPhysicalTableName();

        if (!groupedSensorExecutionResult.isSuccess()) {
            return new SensorExecutionResult(sensorRunParameters, groupedSensorExecutionResult.getException());
        }

        try {
            TableSpec introspectedTableSpec = groupedSensorExecutionResult.getCapturedMetadataResult();

            if (introspectedTableSpec == null) {
                // table not found
                Table table = GenericSensorResultsFactory.createResultTableWithResult((Double) null,
                        this.defaultTimeZoneProvider.getDefaultTimeZoneId(), sensorPrepareResult.getSensorRunParameters().getTimePeriodGradient());
                return new SensorExecutionResult(sensorRunParameters, table);
            }

            List<HashCode> elementHashes = introspectedTableSpec.getColumns().keySet().stream()
                    .map(columnName -> Hashing.combineOrdered(Arrays.asList(
                            Hashing.farmHashFingerprint64().hashString(columnName, StandardCharsets.UTF_8),
                            introspectedTableSpec.getColumns().get(columnName).getTypeSnapshot() != null  ?
                                    HashCode.fromLong(introspectedTableSpec.getColumns().get(columnName).getTypeSnapshot().hashCode64()) : HashCode.fromLong(-1L))))
                    .collect(Collectors.toList());
            long fullHash = Math.abs(Hashing.combineUnordered(elementHashes).asLong());
            long hashFitInDoubleExponent = fullHash & ((1L << 52) - 1L); // because we are storing the results of data quality checks in a IEEE 754 double-precision floating-point value and we need exact match, we need to return only as many bits as the fraction part (52 bits) can fit in a Double value, without any unwanted truncations

            Table table = GenericSensorResultsFactory.createResultTableWithResult(hashFitInDoubleExponent,
                    this.defaultTimeZoneProvider.getDefaultTimeZoneId(), sensorPrepareResult.getSensorRunParameters().getTimePeriodGradient());
            return new SensorExecutionResult(sensorRunParameters, table);
        }
        catch (Throwable exception) {
            this.userErrorLogger.logSensor("Sensor failed to analyze the metadata of:" + physicalTableName.toTableSearchFilter(), exception);
            return new SensorExecutionResult(sensorRunParameters, exception);
        }
    }

    /**
     * Executes a sensor and returns the sensor result.
     *
     * @param executionContext     Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorPrepareResult  Sensor preparation result, contains the sensor definition, rendered SQL template, sensor run parameters (connection, table, etc.).
     * @param progressListener     Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, may cancel a running query.
     * @return Sensor result.
     */
    @Override
    public SensorExecutionResult executeSensor(ExecutionContext executionContext,
                                               SensorPrepareResult sensorPrepareResult,
                                               SensorExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               JobCancellationToken jobCancellationToken) {
        SensorExecutionRunParameters sensorRunParameters = sensorPrepareResult.getSensorRunParameters();
        String renderedSensorSql = sensorPrepareResult.getRenderedSensorSql();

        try {
            if (!dummySensorExecution) {
                ConnectionSpec connectionSpec = sensorRunParameters.getConnection();
                progressListener.onExecutingSqlOnConnection(new ExecutingSqlOnConnectionEvent(sensorRunParameters,
                        sensorPrepareResult.getSensorDefinition(), connectionSpec, renderedSensorSql));

                jobCancellationToken.throwIfCancelled();
                ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
                SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(executionContext.getUserHomeContext().getUserHome());
                try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true, secretValueLookupContext)) {
                    jobCancellationToken.throwIfCancelled();
                    String schemaName = sensorRunParameters.getTable().getPhysicalTableName().getSchemaName();
                    String tableName = sensorRunParameters.getTable().getPhysicalTableName().getTableName();

                    ConnectionList connections = executionContext.getUserHomeContext().getUserHome().getConnections();
                    ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionSpec.getConnectionName(), true);

                    List<TableSpec> retrievedTableSpecList = sourceConnection.retrieveTableMetadata(
                            schemaName,
                            null,
                            1,
                            new ArrayList<>() {{
                                add(tableName);
                            }},
                            connectionWrapper,
                            secretValueLookupContext
                    );

                    if (retrievedTableSpecList.size() == 0) {
                        // table not found
                        Table table = GenericSensorResultsFactory.createResultTableWithResult((Double) null,
                                this.defaultTimeZoneProvider.getDefaultTimeZoneId(), sensorPrepareResult.getSensorRunParameters().getTimePeriodGradient());
                        return new SensorExecutionResult(sensorRunParameters, table);
                    }

                    TableSpec introspectedTableSpec = retrievedTableSpecList.get(0);
                    List<HashCode> elementHashes = introspectedTableSpec.getColumns().keySet().stream()
                            .map(columnName -> Hashing.combineOrdered(Arrays.asList(
                                    Hashing.farmHashFingerprint64().hashString(columnName, StandardCharsets.UTF_8),
                                    introspectedTableSpec.getColumns().get(columnName).getTypeSnapshot() != null  ?
                                            HashCode.fromLong(introspectedTableSpec.getColumns().get(columnName).getTypeSnapshot().hashCode64()) : HashCode.fromLong(-1L))))
                            .collect(Collectors.toList());
                    long fullHash = Math.abs(Hashing.combineUnordered(elementHashes).asLong());
                    long hashFitInDoubleExponent = fullHash & ((1L << 52) - 1L); // because we are storing the results of data quality checks in a IEEE 754 double-precision floating-point value and we need exact match, we need to return only as many bits as the fraction part (52 bits) can fit in a Double value, without any unwanted truncations

                    Table table = GenericSensorResultsFactory.createResultTableWithResult(hashFitInDoubleExponent,
                            this.defaultTimeZoneProvider.getDefaultTimeZoneId(), sensorPrepareResult.getSensorRunParameters().getTimePeriodGradient());
                    return new SensorExecutionResult(sensorRunParameters, table);
                }
            }

            Table dummyResultTable = GenericSensorResultsFactory.createDummyResultTable(sensorRunParameters,
                    this.defaultTimeZoneProvider.getDefaultTimeZoneId());
            return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
        }
        catch (Throwable exception) {
            this.userErrorLogger.logSensor(sensorRunParameters.toString() + " failed to execute a query: " + renderedSensorSql +
                        ", error: " + exception.getMessage(), exception);
            return new SensorExecutionResult(sensorRunParameters, exception);
        }
    }
}
