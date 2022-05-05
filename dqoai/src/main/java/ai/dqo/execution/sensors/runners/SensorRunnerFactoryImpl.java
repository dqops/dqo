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
package ai.dqo.execution.sensors.runners;

import ai.dqo.execution.sqltemplates.JinjaSqlTemplateSensorRunner;
import ai.dqo.metadata.definitions.sensors.ProviderSensorRunnerType;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sensor runner factory.
 */
@Component
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
            case custom_class:
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
