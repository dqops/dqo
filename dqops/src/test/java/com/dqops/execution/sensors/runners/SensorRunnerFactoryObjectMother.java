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

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for SensorRunnerFactoryImpl.
 */
public class SensorRunnerFactoryObjectMother {
    /**
     * Creates the default sensor runner factory.
     * @return Default sensor runner factory.
     */
    public static SensorRunnerFactoryImpl create() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SensorRunnerFactory sensorRunnerFactory = beanFactory.getBean(SensorRunnerFactory.class);
        return (SensorRunnerFactoryImpl)sensorRunnerFactory;
    }
}
