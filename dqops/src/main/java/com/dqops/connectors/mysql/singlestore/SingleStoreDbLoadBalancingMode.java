/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.mysql.singlestore;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Failover and Load-Balancing Modes for Single Store DB.
 */
public enum SingleStoreDbLoadBalancingMode {

    /**
     * Mode not set.
     */
    @JsonProperty("none")
    none,

    /**
     * In this mode, the connector tries to connect with the hosts in the order in which they are declared in the connection string. Hence, the first available host is used for all the queries.
     */
    @JsonProperty("sequential")
    sequential,

    /**
     * The connector randomly selects a host from the connection string, for each connection, to perform load-balancing on all the queries. Hence, the queries are load-balanced by randomly distributing the connections across all the hosts.
     */
    @JsonProperty("loadbalance")
    loadbalance

}
