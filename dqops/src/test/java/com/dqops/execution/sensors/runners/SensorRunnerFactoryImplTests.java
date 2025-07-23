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

import com.dqops.BaseTest;
import com.dqops.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import com.dqops.metadata.definitions.sensors.ProviderSensorRunnerType;
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
        AbstractSensorRunner sensorRunner = this.sut.getSensorRunner(ProviderSensorRunnerType.java_class, JinjaSqlTemplateSensorRunner.CLASS_NAME);
        Assertions.assertNotNull(sensorRunner);
        Assertions.assertInstanceOf(JinjaSqlTemplateSensorRunner.class, sensorRunner);
    }
}
