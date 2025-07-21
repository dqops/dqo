/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks.defaults;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicyWrapper;
import com.dqops.metadata.policies.column.TargetColumnPatternFilter;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;
import com.dqops.metadata.policies.table.TableQualityPolicyWrapper;
import com.dqops.metadata.policies.table.TargetTablePatternFilter;
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

        List<TableQualityPolicyWrapper> tableQualityPolicyWrappers = new ArrayList<>(userHome.getTableQualityPolicies().toList());
        tableQualityPolicyWrappers.removeIf(wrapper -> wrapper.getSpec().isDisabled());
        tableQualityPolicyWrappers.sort(Comparator.comparing(wrapper -> wrapper.getSpec().getPriority()));
        
        for (TableQualityPolicyWrapper tableQualityPolicyWrapper : tableQualityPolicyWrappers) {
            TableQualityPolicySpec defaultChecksPattern = tableQualityPolicyWrapper.getSpec();
            TargetTablePatternFilter patternFilter = defaultChecksPattern.getTarget().toPatternFilter();
            if (!patternFilter.match(connectionSpec, targetTableSpec, true)) {
                continue;
            }

            defaultChecksPattern.applyOnTable(targetTableSpec, providerDialectSettings, tableQualityPolicyWrapper.getLastModified());
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

        List<ColumnQualityPolicyWrapper> columnPatternWrappers = new ArrayList<>(
                userHome.getColumnQualityPolicies().toList());
        columnPatternWrappers.removeIf(wrapper -> wrapper.getSpec().isDisabled());
        columnPatternWrappers.sort(Comparator.comparing(wrapper -> wrapper.getSpec().getPriority()));

        for (ColumnQualityPolicyWrapper columnQualityPolicyWrapper : columnPatternWrappers) {
            ColumnQualityPolicySpec defaultChecksPattern = columnQualityPolicyWrapper.getSpec();
            TargetColumnPatternFilter patternFilter = defaultChecksPattern.getTarget().toPatternFilter();
            DataTypeCategory dataTypeCategory = providerDialectSettings.detectColumnType(targetColumnSpec.getTypeSnapshot());
            if (!patternFilter.match(connectionSpec, targetTableSpec, targetColumnSpec, dataTypeCategory)) {
                continue;
            }

            defaultChecksPattern.applyOnColumn(targetTableSpec, targetColumnSpec, providerDialectSettings, columnQualityPolicyWrapper.getLastModified());
        }
    }
}
