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
package ai.dqo.execution.sqltemplates.rendering;

import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.SourceConnection;
import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorPrepareResult;
import ai.dqo.execution.sensors.grouping.AbstractGroupedSensorExecutor;
import ai.dqo.execution.sensors.grouping.GroupedSensorExecutionResult;
import ai.dqo.execution.sensors.grouping.PreparedSensorsGroup;
import ai.dqo.execution.sensors.progress.ExecutingSqlOnConnectionEvent;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.time.Instant;

/**
 * Sensor executor that executes SQL queries on a data source that supports SQL.
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JinjaSqlTemplateSensorExecutor extends AbstractGroupedSensorExecutor {
    private final ConnectionProviderRegistry connectionProviderRegistry;

    /**
     * Creates a sql template runner.
     * @param connectionProviderRegistry Connection provider registry.
     * @param defaultTimeZoneProvider Default time zone provider. Returns the default server time zone.
     */
    @Autowired
    public JinjaSqlTemplateSensorExecutor(ConnectionProviderRegistry connectionProviderRegistry,
                                          DefaultTimeZoneProvider defaultTimeZoneProvider) {
        super(defaultTimeZoneProvider);
        this.connectionProviderRegistry = connectionProviderRegistry;
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
        String renderedSensorSql = preparedSensorsGroup.getMergedSql();
        Instant startedAt = Instant.now();

        try {
            if (!dummySensorExecution) {
                ConnectionSpec connectionSpec = sensorRunParameters.getConnection();
                progressListener.onExecutingSqlOnConnection(new ExecutingSqlOnConnectionEvent(sensorRunParameters,
                        firstSensorPrepareResult.getSensorDefinition(), connectionSpec, renderedSensorSql));

                jobCancellationToken.throwIfCancelled();
                ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
                try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true)) {
                    jobCancellationToken.throwIfCancelled();
                    Table sensorResultRows = sourceConnection.executeQuery(renderedSensorSql, jobCancellationToken);
                    return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, sensorResultRows);
                }
            }

            Table dummyResultTable = createResultTableWithResult(preparedSensorsGroup);
            return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, dummyResultTable);
        }
        catch (Throwable exception) {
            log.debug("Sensor failed to execute a query :" + renderedSensorSql, exception);
            return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, exception);
        }
    }
}
