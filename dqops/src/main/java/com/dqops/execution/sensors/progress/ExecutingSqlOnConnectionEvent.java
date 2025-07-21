/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.progress;

import com.dqops.execution.checks.progress.CheckExecutionProgressEvent;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Progress event raised before a sensor SQL is executed on a connection.
 */
public class ExecutingSqlOnConnectionEvent extends CheckExecutionProgressEvent {
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorDefinitionFindResult sensorDefinitions;
    private final ConnectionSpec connectionSpec;
    private final String renderedSql;

    /**
     * Creates an event object.
     *
     * @param sensorRunParameters Sensor run parameters.
     * @param sensorDefinitions   Sensor definition to identify the right sensor.
     * @param connectionSpec      Connection specification.
     * @param renderedSql         Rendered sensor sql that will be executed.
     */
    public ExecutingSqlOnConnectionEvent(SensorExecutionRunParameters sensorRunParameters,
										 SensorDefinitionFindResult sensorDefinitions,
										 ConnectionSpec connectionSpec,
										 String renderedSql) {
        this.sensorRunParameters = sensorRunParameters;
        this.sensorDefinitions = sensorDefinitions;
        this.connectionSpec = connectionSpec;
        this.renderedSql = renderedSql;
    }

    /**
     * Sensor run parameters.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Sensor definition to identify the right sensor.
     *
     * @return Sensor definition to identify the right sensor.
     */
    public SensorDefinitionFindResult getSensorDefinitions() {
        return sensorDefinitions;
    }

    /**
     * Connection specification.
     *
     * @return Connection specification.
     */
    public ConnectionSpec getConnectionSpec() {
        return connectionSpec;
    }

    /**
     * Rendered sensor sql that will be executed.
     *
     * @return Rendered sensor sql that will be executed.
     */
    public String getRenderedSql() {
        return renderedSql;
    }
}
