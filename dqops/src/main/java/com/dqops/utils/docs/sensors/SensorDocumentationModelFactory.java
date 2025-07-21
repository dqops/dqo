/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.sensors;

import com.dqops.sensors.AbstractSensorParametersSpec;

/**
 * Sensor documentation model factory that creates a sensor documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public interface SensorDocumentationModelFactory {
    /**
     * Create a sensor documentation model for a given sensor parameter class instance.
     *
     * @param sensorParametersSpec Sensor parameter instance.
     * @return Sensor documentation model.
     */
    SensorDocumentationModel createSensorDocumentation(AbstractSensorParametersSpec sensorParametersSpec);
}
