/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
