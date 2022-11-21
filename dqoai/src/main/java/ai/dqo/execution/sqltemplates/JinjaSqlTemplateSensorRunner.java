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
package ai.dqo.execution.sqltemplates;

import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.SourceConnection;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.ExecutingSqlOnConnectionEvent;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.runners.AbstractSensorRunner;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;

/**
 * Sensor runner that transforms an SQL template and executes a generated SQL on a connection.
 */
@Component
public class JinjaSqlTemplateSensorRunner extends AbstractSensorRunner {
    /**
     * Sensor runner class name.
     */
    public static final String CLASS_NAME = JinjaSqlTemplateSensorRunner.class.getName();
    private final JinjaTemplateRenderService jinjaTemplateRenderService;
    private final ConnectionProviderRegistry connectionProviderRegistry;

    /**
     * Creates a sql template runner.
     * @param jinjaTemplateRenderService Jinja template rendering service.
     * @param connectionProviderRegistry Connection provider registry.
     */
    @Autowired
    public JinjaSqlTemplateSensorRunner(JinjaTemplateRenderService jinjaTemplateRenderService,
										ConnectionProviderRegistry connectionProviderRegistry) {
        this.jinjaTemplateRenderService = jinjaTemplateRenderService;
        this.connectionProviderRegistry = connectionProviderRegistry;
    }

    /**
     * Executes a sensor and returns the sensor result.
     *
     * @param checkExecutionContext Check execution context with access to the dqo home and user home, if any metadata is needed.
     * @param sensorRunParameters   Sensor run parameters - connection, table, column, sensor parameters.
     * @param sensorDefinitions     Sensor definition (both the core sensor definition and the provider specific sensor definition).
     * @param progressListener      Progress listener that receives events when the sensor is executed.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Sensor result.
     */
    @Override
    public SensorExecutionResult executeSensor(CheckExecutionContext checkExecutionContext,
											   SensorExecutionRunParameters sensorRunParameters,
											   SensorDefinitionFindResult sensorDefinitions,
											   CheckExecutionProgressListener progressListener,
											   boolean dummySensorExecution) {
        JinjaTemplateRenderParameters templateRenderParameters = JinjaTemplateRenderParameters.createFromTrimmedObjects(
                sensorRunParameters, sensorDefinitions);
        String renderedSql = this.jinjaTemplateRenderService.renderTemplate(checkExecutionContext, sensorDefinitions,
                templateRenderParameters, progressListener);

        if (!dummySensorExecution) {
            ConnectionSpec connectionSpec = sensorRunParameters.getConnection();
            progressListener.onExecutingSqlOnConnection(new ExecutingSqlOnConnectionEvent(sensorRunParameters,
                    sensorDefinitions, connectionSpec, renderedSql));

            ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
            try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true)) {
                Table sensorResultRows = sourceConnection.executeQuery(renderedSql);
                return new SensorExecutionResult(sensorRunParameters, sensorResultRows);
            }
        }

        Table dummyResultTable = createDummyResultTable(sensorRunParameters);
        return new SensorExecutionResult(sensorRunParameters, dummyResultTable);
    }

    /**
     * Creates a one row dummy result table.
     * @param sensorRunParameters Sensor execution run parameters.
     * @return Dummy result table.
     */
    public Table createDummyResultTable(SensorExecutionRunParameters sensorRunParameters) {
        Table dummyResultTable = Table.create("dummy_results", DoubleColumn.create(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME));
        Row row = dummyResultTable.appendRow();
        row.setDouble(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME, 10.0);

        TimeSeriesConfigurationSpec effectiveTimeSeries = sensorRunParameters.getEffectiveTimeSeries();
        if (effectiveTimeSeries != null && effectiveTimeSeries.getMode() != null) {
            dummyResultTable.addColumns(DateTimeColumn.create(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME));
            row.setDateTime(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME, LocalDateTime.now());
        }

        // TODO: we could also add some fake dimensions to make the dummy run more realistic

        return dummyResultTable;
    }
}
