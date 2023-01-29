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

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.CheckType;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.profiling.AbstractStatisticsCollectorSpec;

/**
 * Factory for {@link SensorExecutionRunParameters} objects. Expands all parameters in the form ${ENV_VAR} or ${sm://secret-name}
 */
public interface SensorExecutionRunParametersFactory {
    /**
     * Creates a sensor parameters object. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param check Check specification.
     * @param checkType Check type (adhoc, checkpoint, partitioned).
     * @param timeSeriesConfigurationSpec Time series configuration extracted from the group of checks (ad-hoc, checkpoints, partitioned).
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    SensorExecutionRunParameters createSensorParameters(ConnectionSpec connection,
                                                        TableSpec table,
                                                        ColumnSpec column,
                                                        AbstractCheckSpec<?,?,?,?> check,
                                                        CheckType checkType,
                                                        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec,
                                                        ProviderDialectSettings dialectSettings);

    /**
     * Creates a sensor parameters object for a statistics collector. The sensor parameter object contains cloned, truncated and expanded (parameter expansion)
     * specifications for the target connection, table, column, check.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Optional column specification for column sensors.
     * @param statisticsCollectorSpec Statistics collector specification.
     * @param dialectSettings Dialect settings.
     * @return Sensor execution run parameters.
     */
    SensorExecutionRunParameters createSensorParameters(ConnectionSpec connection,
                                                        TableSpec table,
                                                        ColumnSpec column,
                                                        AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec,
                                                        StatisticsDataScope statisticsDataScope,
                                                        ProviderDialectSettings dialectSettings);
}
