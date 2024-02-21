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

package com.dqops.checks.defaults.services;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternWrapper;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternWrapper;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service that will apply the default configuration of the data observability (the default checks) on new tables and columns that are imported.
 */
@Component
public class DefaultObservabilityConfigurationServiceImpl implements DefaultObservabilityConfigurationService {
    private final ConnectionProviderRegistry connectionProviderRegistry;

    /**
     * Default injection container.
     * @param connectionProviderRegistry Connection provider registry.
     */
    @Autowired
    public DefaultObservabilityConfigurationServiceImpl(ConnectionProviderRegistry connectionProviderRegistry) {
        this.connectionProviderRegistry = connectionProviderRegistry;
    }

    /**
     * Applies the default configuration of default checks on a table.
     *
     * @param targetTableSpec         Target table specification.
     * @param providerDialectSettings Provider specific dialect settings, to detect the column type and if certain categories of checks could be applied.
     * @param userHome                User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnTableAndColumns(TableSpec targetTableSpec, ProviderDialectSettings providerDialectSettings, UserHome userHome) {
        for (TableDefaultChecksPatternWrapper tableDefaultChecksPatternWrapper : userHome.getTableDefaultChecksPatterns() ) {
            TableDefaultChecksPatternSpec defaultChecksPattern = tableDefaultChecksPatternWrapper.getSpec();
            defaultChecksPattern.applyOnTable(targetTableSpec, providerDialectSettings);
        }

        for (ColumnSpec targetColumnSpec : targetTableSpec.getColumns().values()) {
            for (ColumnDefaultChecksPatternWrapper columnDefaultChecksPatternWrapper : userHome.getColumnDefaultChecksPatterns() ) {
                ColumnDefaultChecksPatternSpec defaultChecksPattern = columnDefaultChecksPatternWrapper.getSpec();
                defaultChecksPattern.applyOnColumn(targetColumnSpec, providerDialectSettings);
            }
        }
    }

    /**
     * Applies the default configuration of default checks on a table.
     *
     * @param targetTableSpec Target table specification.
     * @param connectionSpec  Connection specification.
     * @param userHome        User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnTableAndColumns(TableSpec targetTableSpec, ConnectionSpec connectionSpec, UserHome userHome) {
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings providerDialectSettings = connectionProvider.getDialectSettings(connectionSpec);

        applyDefaultChecksOnTableAndColumns(targetTableSpec, providerDialectSettings, userHome);
    }

    /**
     * Applies the default configuration of default checks on a table only, not on columns.
     *
     * @param targetTableSpec Target table specification.
     * @param connectionSpec  Connection specification.
     * @param userHome        User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnTableOnly(TableSpec targetTableSpec, ConnectionSpec connectionSpec, UserHome userHome) {
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings providerDialectSettings = connectionProvider.getDialectSettings(connectionSpec);

        for (TableDefaultChecksPatternWrapper tableDefaultChecksPatternWrapper : userHome.getTableDefaultChecksPatterns() ) {
            TableDefaultChecksPatternSpec defaultChecksPattern = tableDefaultChecksPatternWrapper.getSpec();
            defaultChecksPattern.applyOnTable(targetTableSpec, providerDialectSettings);
        }
    }

    /**
     * Applies the default configuration of default checks on a column.
     *
     * @param targetColumnSpec Target column specification.
     * @param connectionSpec   Connection specification.
     * @param userHome         User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnColumn(ColumnSpec targetColumnSpec, ConnectionSpec connectionSpec, UserHome userHome) {
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings providerDialectSettings = connectionProvider.getDialectSettings(connectionSpec);

        for (ColumnDefaultChecksPatternWrapper columnDefaultChecksPatternWrapper : userHome.getColumnDefaultChecksPatterns() ) {
            ColumnDefaultChecksPatternSpec defaultChecksPattern = columnDefaultChecksPatternWrapper.getSpec();
            defaultChecksPattern.applyOnColumn(targetColumnSpec, providerDialectSettings);
        }
    }
}
