/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.runners;

import com.dqops.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import com.dqops.metadata.definitions.sensors.ProviderSensorRunnerType;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Sensor runner factory.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SensorRunnerFactoryImpl implements SensorRunnerFactory {
    private final BeanFactory beanFactory;
    private final JinjaSqlTemplateSensorRunner sqlTemplateSensorRunner;

    /**
     * Creates a sensor runner factory.
     * @param beanFactory Bean factory.
     */
    @Autowired
    public SensorRunnerFactoryImpl(BeanFactory beanFactory, JinjaSqlTemplateSensorRunner sqlTemplateSensorRunner) {
        this.beanFactory = beanFactory;
        this.sqlTemplateSensorRunner = sqlTemplateSensorRunner;
    }

    /**
     * Gets an instance of a sensor runner given the class name.
     *
     * @param sensorType            Sensor type.
     * @param sensorRunnerClassName Sensor runner class name (optional, only for a custom java class).
     * @return Sensor runner class name.
     */
    @Override
    public AbstractSensorRunner getSensorRunner(ProviderSensorRunnerType sensorType, String sensorRunnerClassName) {
        switch (sensorType) {
            case sql_template:
                return this.sqlTemplateSensorRunner;
            case java_class:
                return createCustomSensorByJavaClass(sensorRunnerClassName);
            default:
                throw new IllegalArgumentException("Unsupported provider sensor type: " + sensorType);
        }
    }

    /**
     * Gets an instance of a sensor runner given the class name.
     * @param sensorRunnerClassName Sensor runner class name.
     * @return Sensor runner class name.
     */
    public AbstractSensorRunner createCustomSensorByJavaClass(String sensorRunnerClassName) {
        try {
            Class<?> runnerClassType = Class.forName(sensorRunnerClassName);
            AbstractSensorRunner abstractSensorRunner = (AbstractSensorRunner) beanFactory.getBean(runnerClassType);
            return abstractSensorRunner;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Runner " + sensorRunnerClassName + " not found");
        }
    }
}
