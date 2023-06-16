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
package ai.dqo.sensors.table.schema;

import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.SourceConnection;
import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.execution.sensors.progress.ExecutingSqlOnConnectionEvent;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Sensor runner that introspects the metadata on a table and counts the number of columns.
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableColumnCountSensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = TableColumnCountSensorRunner.class.getName();
    private ConnectionProviderRegistry connectionProviderRegistry;

    /**
     * Dependency injection constructor that receives all dependencies.
     * @param connectionProviderRegistry Connection provider registry, used to retrieve a connector instance for the target data source.
     * @param defaultTimeZoneProvider The default time zone provider.
     */
    @Autowired
    public TableColumnCountSensorRunner(ConnectionProviderRegistry connectionProviderRegistry,
                                        DefaultTimeZoneProvider defaultTimeZoneProvider) {
        super(defaultTimeZoneProvider);
        this.connectionProviderRegistry = connectionProviderRegistry;
    }

    /**
     * Prepares a sensor for execution. SQL templated sensors will render the SQL template, filled with the table and column names.
     *
     * @param executionContext    Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorPrepareResult Sensor prepare result with additional sensor run parameters. The prepareSensor method should fill additional values in this object that will be used when the sensor is executed.
     * @param progressListener    Progress listener that receives events when the sensor is executed.
     */
    @Override
    public void prepareSensor(ExecutionContext executionContext,
                              SensorPrepareResult sensorPrepareResult,
                              SensorExecutionProgressListener progressListener) {
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
                try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true)) {
                    jobCancellationToken.throwIfCancelled();
                    String schemaName = sensorRunParameters.getTable().getPhysicalTableName().getSchemaName();
                    String tableName = sensorRunParameters.getTable().getPhysicalTableName().getTableName();
                    List<TableSpec> retrievedTableSpecList = sourceConnection.retrieveTableMetadata(schemaName, new ArrayList<>() {{
                        add(tableName);
                    }});

                    if (retrievedTableSpecList.size() == 0) {
                        // table not found
                        Table table = createResultTableWithResult((Double) null);
                        return new SensorExecutionResult(sensorRunParameters, table);
                    }

                    TableSpec introspectedTableSpec = retrievedTableSpecList.get(0);
                    int columnCount = introspectedTableSpec.getColumns().size();

                    Table table = createResultTableWithResult(columnCount);
                    return new SensorExecutionResult(sensorRunParameters, table);
                }
            }

            Table dummyResultTable = createDummyResultTable(sensorRunParameters);
            return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
        }
        catch (Throwable exception) {
            log.debug("Sensor failed to execute a query :" + renderedSensorSql, exception);
            return new SensorExecutionResult(sensorRunParameters, exception);
        }
    }
}
