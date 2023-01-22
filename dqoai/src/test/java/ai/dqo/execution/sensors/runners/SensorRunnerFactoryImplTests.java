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

import ai.dqo.BaseTest;
import ai.dqo.execution.sqltemplates.JinjaSqlTemplateSensorRunner;
import ai.dqo.metadata.definitions.sensors.ProviderSensorRunnerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensorRunnerFactoryImplTests extends BaseTest {
    private SensorRunnerFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = SensorRunnerFactoryObjectMother.create();
    }

    @Test
    void getSensorRunner_whenSqlTemplateRunnerRequested_thenReturnsInstance() {
        AbstractSensorRunner sensorRunner = this.sut.getSensorRunner(ProviderSensorRunnerType.sql_template, null);
        Assertions.assertNotNull(sensorRunner);
        Assertions.assertInstanceOf(JinjaSqlTemplateSensorRunner.class, sensorRunner);
    }

    @Test
    void getSensorRunner_whenCustomClassRequested_thenCreatesRequestedRunner() {
        AbstractSensorRunner sensorRunner = this.sut.getSensorRunner(ProviderSensorRunnerType.custom_class, JinjaSqlTemplateSensorRunner.CLASS_NAME);
        Assertions.assertNotNull(sensorRunner);
        Assertions.assertInstanceOf(JinjaSqlTemplateSensorRunner.class, sensorRunner);
    }
}
