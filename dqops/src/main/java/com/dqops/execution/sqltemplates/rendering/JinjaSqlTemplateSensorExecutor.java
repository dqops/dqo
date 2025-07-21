/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.SourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorPrepareResult;
import com.dqops.execution.sensors.grouping.AbstractGroupedSensorExecutor;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.grouping.PreparedSensorsGroup;
import com.dqops.execution.sensors.progress.ExecutingSqlOnConnectionEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.logging.UserErrorLogger;
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
    private final UserErrorLogger userErrorLogger;

    /**
     * Creates a sql template runner.
     * @param connectionProviderRegistry Connection provider registry.
     * @param defaultTimeZoneProvider Default time zone provider. Returns the default server time zone.
     * @param userErrorLogger Check execution logger.
     */
    @Autowired
    public JinjaSqlTemplateSensorExecutor(ConnectionProviderRegistry connectionProviderRegistry,
                                          DefaultTimeZoneProvider defaultTimeZoneProvider,
                                          UserErrorLogger userErrorLogger) {
        super(defaultTimeZoneProvider);
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
        String renderedSensorSql = preparedSensorsGroup.getMergedSql();
        Instant startedAt = Instant.now();

        try {
            if (!dummySensorExecution) {
                ConnectionSpec connectionSpec = sensorRunParameters.getConnection();
                progressListener.onExecutingSqlOnConnection(new ExecutingSqlOnConnectionEvent(sensorRunParameters,
                        firstSensorPrepareResult.getSensorDefinition(), connectionSpec, renderedSensorSql));

                jobCancellationToken.throwIfCancelled();
                ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
                SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(executionContext.getUserHomeContext().getUserHome());
                try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true, secretValueLookupContext)) {
                    jobCancellationToken.throwIfCancelled();
                    Table sensorResultRows = sourceConnection.executeQuery(renderedSensorSql, jobCancellationToken,
                            sensorRunParameters.getRowCountLimit(),
                            sensorRunParameters.isFailOnSensorReadoutLimitExceeded());
                    return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, sensorResultRows);
                }
            }

            Table dummyResultTable = createResultTableWithResult(preparedSensorsGroup);
            return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, dummyResultTable);
        }
        catch (Throwable exception) {
            this.userErrorLogger.logSensor(sensorRunParameters.toString() + " failed to execute a query: " + renderedSensorSql +
                        ", error: " + exception.getMessage(), exception);
            return new GroupedSensorExecutionResult(preparedSensorsGroup, startedAt, exception);
        }
    }
}
