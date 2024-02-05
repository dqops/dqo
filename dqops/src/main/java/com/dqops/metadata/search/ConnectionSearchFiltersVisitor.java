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
package com.dqops.metadata.search;

import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionList;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapper;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.defaultchecks.DefaultObservabilityCheckWrapper;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

/**
 * Visitor for {@link ConnectionSearchFilters} that finds the correct nodes.
 */
public class ConnectionSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    private final ConnectionSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Connection search filters.
     */
    public ConnectionSearchFiltersVisitor(ConnectionSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a list of connections.
     *
     * @param connectionList List of connections.
     * @param parameter      Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionList connectionList, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact connection name given, let's find it
        ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionNameFilter, true);
        if (connectionWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }
        return TreeNodeTraversalResult.traverseSelectedChildNodes(connectionWrapper);
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        if (this.filters.getLabels() != null && this.filters.getLabels().length > 0) {
            parameter.getLabelsSearcherObject().setConnectionLabels(connectionWrapper.getSpec().getLabels());
            if (!LabelsSearchMatcher.matchConnectionLabels(this.filters, connectionWrapper.getSpec().getLabels())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            parameter.getNodes().add(connectionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(connectionWrapper.getName(), connectionNameFilter)) {
            parameter.getNodes().add(connectionWrapper.getSpec());
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionSpec Connection wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSpec connectionSpec, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnectionName();
        parameter.getLabelsSearcherObject().setConnectionLabels(connectionSpec.getLabels());
        if (!LabelsSearchMatcher.matchConnectionLabels(this.filters, connectionSpec.getLabels())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            parameter.getNodes().add(connectionSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(connectionSpec.getConnectionName(), connectionNameFilter)) {
            parameter.getNodes().add(connectionSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (connectionNameFilter.equals(connectionSpec.getConnectionName())) {
            parameter.getNodes().add(connectionSpec);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     *
     * @param defaultObservabilityChecksSpec Default configuration of observability checks.
     * @param parameter                             Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultObservabilityChecksSpec defaultObservabilityChecksSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;  // we don't want to look for checks here, because there are checks.. but only templates, they cannot be run
    }

    /**
     * Accepts a list of sensor definitions for different providers.
     *
     * @param providerSensorDefinitionList List of sensor definitions per provider.
     * @param parameter                    Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionList providerSensorDefinitionList, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a custom rule definition wrapper list that stores a list of custom rules.
     *
     * @param ruleDefinitionList Custom rule list.
     * @param parameter          Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionList ruleDefinitionList, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a settings specific specification.
     *
     * @param localSettingsSpec Settings specific configuration.
     * @param parameter         Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(LocalSettingsSpec localSettingsSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of dashboards list.
     *
     * @param dashboardDefinitionWrapper List of dashboards list.
     * @param parameter                  Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardFolderListSpecWrapperImpl dashboardDefinitionWrapper, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of custom checks.
     *
     * @param checkDefinitionWrappers Custom check list.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CheckDefinitionListImpl checkDefinitionWrappers, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a dictionary of reference table comparisons.
     *
     * @param tableComparisonConfigurationSpecMap Dictionary of reference table comparisons.
     * @param parameter                           Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of a pair of column names that are used for joining and grouping.
     *
     * @param tableComparisonGroupingColumnsPairSpecs A list of a pairs of columns used for grouping and joining in table comparison checks.
     * @param parameter                               Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonGroupingColumnsPairsListSpec tableComparisonGroupingColumnsPairSpecs, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a shared credential list.
     *
     * @param sharedCredentialWrappers Shared credentials list.
     * @param parameter                Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SharedCredentialList sharedCredentialWrappers, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default monitoring schedule wrapper instance.
     *
     * @param monitoringSchedulesWrapper Default monitoring schedule wrapper instance.
     * @param parameter                  Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(MonitoringSchedulesWrapper monitoringSchedulesWrapper, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default incident webhook notification wrapper instance.
     *
     * @param defaultIncidentWebhookNotificationsWrapper Default incident webhook notification wrapper instance.
     * @param parameter                                  Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultIncidentWebhookNotificationsWrapper defaultIncidentWebhookNotificationsWrapper, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default observability check wrapper instance.
     *
     * @param defaultObservabilityCheckWrapper Default observability check wrapper instance.
     * @param parameter                        Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultObservabilityCheckWrapper defaultObservabilityCheckWrapper, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a data dictionary list.
     *
     * @param dictionaryWrappers Data dictionary list.
     * @param parameter          Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DictionaryListImpl dictionaryWrappers, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
