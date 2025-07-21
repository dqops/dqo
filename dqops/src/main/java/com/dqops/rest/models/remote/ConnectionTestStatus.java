/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Tabular output format for printing the tabular results.
 */
public enum ConnectionTestStatus {
    /**
     * An enumeration for Success connection status.
     */
    @JsonProperty("SUCCESS")
    SUCCESS,

    /**
     * An enumeration for FAILURE connection status.
     */
    @JsonProperty("FAILURE")
    FAILURE,

    /**
     * An enumeration for CONNECTION_ALREADY_EXISTS connection status.
     */
    @JsonProperty("CONNECTION_ALREADY_EXISTS")
    CONNECTION_ALREADY_EXISTS,
}