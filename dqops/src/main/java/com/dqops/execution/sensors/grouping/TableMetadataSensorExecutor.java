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
package com.dqops.execution.sensors.grouping;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.SourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sensors.progress.ExecutingSqlOnConnectionEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.metadata.sources.*;
import com.dqops.utils.logging.UserErrorLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Sensor executor that captures (introspects) the table metadata, listing all columns and their types.
 * It is used by all schema drift detection sensors (column exist, etc.) and called only once.
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableMetadataSensorExecutor extends AbstractGroupedSensorExecutor {
    private ConnectionProviderRegistry connectionProviderRegistry;
    private final UserErrorLogger userErrorLogger;

    /**
     * Dependency injection constructor that receives all dependencies.
     * @param connectionProviderRegistry Connection provider registry, used to retrieve a connector instance for the target data source.
     * @param userErrorLogger Check execution loggger.
     */
    @Autowired
    public TableMetadataSensorExecutor(ConnectionProviderRegistry connectionProviderRegistry, UserErrorLogger userErrorLogger) {
        super(null);
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.userErrorLogger = userErrorLogger;
    }

    /**
     * Executes a sensor and returns the sensor result.
     *
     * @param executionContext     Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param preparedSensorsGroup Prepared sensor group. Contains one or multiple similar prepared sensor results that will use the same query to capture all metrics at once.
     * @param progressListener     Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, may cancel a running query.
     * @return Sensor result.
     */
    @Override
    public GroupedSensorExecutionResult executeGroupedSensor(ExecutionContext executionContext,
                                                             PreparedSensorsGroup preparedSensorsGroup,
                                                             SensorExecutionProgressListener progressListener,
                                                             boolean dummySensorExecution,
                                                             JobCancellationToken jobCancellationToken) {
        SensorPrepareResult firstSensorPrepareResult = preparedSensorsGroup.getPreparedSensors().get(0);
        SensorExecutionRunParameters sensorRunParameters = firstSensorPrepareResult.getSensorRunParameters();
        Instant startedAt = Instant.now();

        PhysicalTableName physicalTableName = sensorRunParameters.getTable().getPhysicalTableName();
        try {
            if (!dummySensorExecution) {
                ConnectionSpec connectionSpec = sensorRunParameters.getConnection();
                progressListener.onExecutingSqlOnConnection(new ExecutingSqlOnConnectionEvent(sensorRunParameters,
                        firstSensorPrepareResult.getSensorDefinition(), connectionSpec, null));

                ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
                SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(executionContext.getUserHomeContext().getUserHome());
                try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true, secretValueLookupContext)) {
                    jobCancellationToken.throwIfCancelled();
                    String schemaName = physicalTableName.getSchemaName();
                    String tableName = physicalTableName.getTableName();

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
                        return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, (TableSpec)null);
                    }

                    TableSpec introspectedTableSpec = retrievedTableSpecList.get(0);
                    return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, introspectedTableSpec);
                }
            }

            TableSpec fakeTableSpec = new TableSpec();
            fakeTableSpec.setPhysicalTableName(physicalTableName);
            fakeTableSpec.getColumns().put("col1", new ColumnSpec() {{
                setTypeSnapshot(new ColumnTypeSnapshotSpec("INT"));
            }});
            return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, fakeTableSpec);
        }
        catch (Throwable exception) {
            this.userErrorLogger.logSensor("Metadata sensor failed to read the metadata of the table: " +
                        physicalTableName.toTableSearchFilter() + ", error: " + exception.getMessage(), exception);
            return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, exception);
        }
    }
}
