/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.apikey;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of limits used in an API Key.
 */
public enum DqoCloudLimit {
    /**
     * Limit on the number of connections.
     */
    @JsonProperty("cl")
    CONNECTIONS_LIMIT,

    /**
     * Users limit.
     */
    @JsonProperty("ul")
    USERS_LIMIT,

    /**
     * Months limit - limit of the number of past months that are stored.
     */
    @JsonProperty("ml")
    MONTHS_LIMIT,

    /**
     * Tables per connections limit.
     */
    @JsonProperty("tcl")
    CONNECTION_TABLES_LIMIT,

    /**
     * Total tables limit.
     */
    @JsonProperty("tl")
    TABLES_LIMIT,

    /**
     * Parallel jobs limit.
     */
    @JsonProperty("jl")
    JOBS_LIMIT,

    /**
     * Data domains limit.
     */
    @JsonProperty("dl")
    DATA_DOMAINS_LIMIT;
}
