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

package com.dqops.checks.defaults;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternWrapper;
import com.dqops.metadata.defaultchecks.column.TargetColumnPatternFilter;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternList;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternWrapper;
import com.dqops.metadata.defaultchecks.table.TargetTablePatternFilter;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
     * Applies the default configuration of default checks on a table and its columns.
     *
     * @param connectionSpec  Target connection specification.
     * @param targetTableSpec Target table specification.
     * @param userHome        User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnTableAndColumns(ConnectionSpec connectionSpec,
                                                    TableSpec targetTableSpec,
                                                    UserHome userHome) {
        applyDefaultChecksOnTableOnly(connectionSpec, targetTableSpec, userHome);

        for (ColumnSpec targetColumnSpec : targetTableSpec.getColumns().values()) {
            applyDefaultChecksOnColumn(connectionSpec, targetTableSpec, targetColumnSpec, userHome);
        }
    }

    /**
     * Applies the default configuration of default checks on a table only, not on columns.
     *
     * @param connectionSpec  Connection specification.
     * @param targetTableSpec Target table specification.
     * @param userHome        User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnTableOnly(ConnectionSpec connectionSpec,
                                              TableSpec targetTableSpec,
                                              UserHome userHome) {
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings providerDialectSettings = connectionProvider.getDialectSettings(connectionSpec);

        List<TableDefaultChecksPatternWrapper> tableDefaultChecksPatternWrappers = new ArrayList<>(userHome.getTableDefaultChecksPatterns().toList());
        tableDefaultChecksPatternWrappers.sort(Comparator.comparing(wrapper -> wrapper.getSpec().getPriority()));
        
        for (TableDefaultChecksPatternWrapper tableDefaultChecksPatternWrapper : tableDefaultChecksPatternWrappers) {
            TableDefaultChecksPatternSpec defaultChecksPattern = tableDefaultChecksPatternWrapper.getSpec();
            TargetTablePatternFilter patternFilter = defaultChecksPattern.getTarget().toPatternFilter();
            if (!patternFilter.match(connectionSpec, targetTableSpec, true)) {
                continue;
            }

            defaultChecksPattern.applyOnTable(targetTableSpec, providerDialectSettings);
        }
    }

    /**
     * Applies the default configuration of default checks on a column.
     *
     * @param connectionSpec   Connection specification.
     * @param targetTableSpec  Target table specification.
     * @param targetColumnSpec Target column specification.
     * @param userHome         User home, to read the configuration.
     */
    @Override
    public void applyDefaultChecksOnColumn(ConnectionSpec connectionSpec,
                                           TableSpec targetTableSpec,
                                           ColumnSpec targetColumnSpec,
                                           UserHome userHome) {
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings providerDialectSettings = connectionProvider.getDialectSettings(connectionSpec);

        List<ColumnDefaultChecksPatternWrapper> columnPatternWrappers = new ArrayList<>(userHome.getColumnDefaultChecksPatterns().toList());
        columnPatternWrappers.sort(Comparator.comparing(wrapper -> wrapper.getSpec().getPriority()));

        for (ColumnDefaultChecksPatternWrapper columnDefaultChecksPatternWrapper : columnPatternWrappers) {
            ColumnDefaultChecksPatternSpec defaultChecksPattern = columnDefaultChecksPatternWrapper.getSpec();
            TargetColumnPatternFilter patternFilter = defaultChecksPattern.getTarget().toPatternFilter();
            DataTypeCategory dataTypeCategory = providerDialectSettings.detectColumnType(targetColumnSpec.getTypeSnapshot());
            if (!patternFilter.match(connectionSpec, targetTableSpec, targetColumnSpec, dataTypeCategory)) {
                continue;
            }

            defaultChecksPattern.applyOnColumn(targetColumnSpec, providerDialectSettings);
        }
    }
}
