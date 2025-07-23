/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.definitions.sensors;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Implementation mode for a provider sensor.
 */
public enum ProviderSensorRunnerType {
    /**
     * Sensor is defined as an SQL template.
     */
    @JsonProperty("sql_template")
    sql_template,

    /**
     * Sensor is implemented as a Java class.
     */
    @JsonProperty("java_class")
    java_class
}
