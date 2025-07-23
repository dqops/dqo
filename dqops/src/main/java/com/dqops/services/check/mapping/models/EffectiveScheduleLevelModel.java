/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of possible levels at which a schedule can be configured.
 */
public enum EffectiveScheduleLevelModel {
    /**
     * An enumeration for connection level (base level).
     */
    @JsonProperty("connection")
    connection,

    /**
     * An enumeration for table level override.
     */
    @JsonProperty("table_override")
    table_override,

    /**
     * An enumeration for check level override.
     */
    @JsonProperty("check_override")
    check_override,
}