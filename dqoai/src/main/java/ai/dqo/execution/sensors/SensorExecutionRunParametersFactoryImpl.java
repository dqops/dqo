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
package ai.dqo.execution.sensors;

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for {@link SensorExecutionRunParameters} objects. Expands all parameters in the form ${ENV_VAR} or ${sm://secret-name}
 */
@Component
public class SensorExecutionRunParametersFactoryImpl implements SensorExecutionRunParametersFactory {
    private final SecretValueProvider secretValueProvider;

    /**
     * Default injection constructor.
     * @param secretValueProvider Secret value provider to expand environment variables and secrets.
     */
    @Autowired
    public SensorExecutionRunParametersFactoryImpl(SecretValueProvider secretValueProvider) {
        this.secretValueProvider = secretValueProvider;
    }

    /**
     * Creates a sensor parameters object. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param check Check specification.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    @Override
    public SensorExecutionRunParameters createSensorParameters(ConnectionSpec connection,
															   TableSpec table,
															   ColumnSpec column,
															   AbstractCheckDeprecatedSpec check,
															   ProviderDialectSettings dialectSettings) {
        ConnectionSpec expandedConnection = connection.expandAndTrim(this.secretValueProvider);
        TableSpec expandedTable = table.expandAndTrim(this.secretValueProvider);
        ColumnSpec expandedColumn = column != null ? column.expandAndTrim(this.secretValueProvider) : null;
        HierarchyId checkHierarchyId = check.getHierarchyId();
        AbstractSensorParametersSpec sensorParameters = check.getSensorParameters().expandAndTrim(this.secretValueProvider);
        AbstractCheckDeprecatedSpec expandedCheck = check.expandAndTrim(this.secretValueProvider);

        TimeSeriesConfigurationSpec timeSeries = expandedCheck.getTimeSeriesOverride();
        if (timeSeries == null && expandedColumn != null) {
            timeSeries = expandedColumn.getTimeSeriesOverride();
        }
        if (timeSeries == null) {
            timeSeries = expandedTable.getTimeSeries();
        }
        if(timeSeries == null) {
            timeSeries = expandedConnection.getDefaultTimeSeries();
        }

        DimensionsConfigurationSpec dimensions = expandedCheck.getDimensionsOverride();
        if (dimensions == null && column != null) {
            dimensions = expandedColumn.getDimensionsOverride(); // TODO: support combining an affective dimension configuration
        }
        if (dimensions == null) {
            dimensions = expandedTable.getDimensions();
        }
        if (dimensions == null) {
            dimensions = expandedConnection.getDefaultDimensions();
        }

        return new SensorExecutionRunParameters(expandedConnection, expandedTable, expandedColumn,
                checkHierarchyId, timeSeries, dimensions, sensorParameters, dialectSettings);
    }


    /**
     * Creates a sensor parameters object. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param check Check specification.
     * @param timeSeriesConfigurationSpec Time series configuration extracted from the group of checks (ad-hoc, checkpoints, partitioned).
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    @Override
    public SensorExecutionRunParameters createSensorParameters(ConnectionSpec connection,
                                                               TableSpec table,
                                                               ColumnSpec column,
                                                               AbstractCheckSpec check,
                                                               TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
                                                               ProviderDialectSettings dialectSettings) {
        ConnectionSpec expandedConnection = connection.expandAndTrim(this.secretValueProvider);
        TableSpec expandedTable = table.expandAndTrim(this.secretValueProvider);
        ColumnSpec expandedColumn = column != null ? column.expandAndTrim(this.secretValueProvider) : null;
        HierarchyId checkHierarchyId = check.getHierarchyId();
        AbstractSensorParametersSpec sensorParameters = check.getParameters().expandAndTrim(this.secretValueProvider);

        TimeSeriesConfigurationSpec timeSeries = timeSeriesConfigurationSpec; // TODO: for very custom checks, we can extract the time series override from the check

        DimensionsConfigurationSpec dimensions = null;
        if (expandedColumn != null) {
            dimensions = expandedColumn.getDimensionsOverride(); // TODO: support combining an affective dimension configuration
        }
        if (dimensions == null) {
            dimensions = expandedTable.getDimensions();
        }
        if (dimensions == null) {
            dimensions = expandedConnection.getDefaultDimensions();
        }

        return new SensorExecutionRunParameters(expandedConnection, expandedTable, expandedColumn,
                checkHierarchyId, timeSeries, dimensions, sensorParameters, dialectSettings);
    }
}
