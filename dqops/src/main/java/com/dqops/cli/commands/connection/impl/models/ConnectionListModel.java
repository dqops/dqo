/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.connection.impl.models;

import com.dqops.connectors.ProviderType;

/**
 * Connection list model.
 */
public class ConnectionListModel {
    private long id;
    private String name;
    private ProviderType dialect;
    private String databaseName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProviderType getDialect() {
        return dialect;
    }

    public void setDialect(ProviderType dialect) {
        this.dialect = dialect;
    }

    public String getDatabaseName() { return databaseName; }

    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }
}
