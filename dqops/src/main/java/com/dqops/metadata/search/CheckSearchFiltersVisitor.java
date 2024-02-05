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

import com.dqops.checks.*;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionList;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapper;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.defaultchecks.DefaultObservabilityCheckWrapper;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.google.common.base.Strings;

import java.util.Set;

/**
 * Visitor for {@link CheckSearchFilters} that finds the correct nodes.
 */
public class CheckSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    private final CheckSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public CheckSearchFiltersVisitor(CheckSearchFilters filters) {
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
        String connectionNameFilter = this.filters.getConnection();
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
        String connectionNameFilter = this.filters.getConnection();

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setConnectionLabels(connectionWrapper.getSpec().getLabels());

        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(connectionWrapper.getName(), connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a collection of tables inside a connection.
     *
     * @param tableList Table list.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableList tableList, SearchParameterObject parameter) {
        String schemaTableName = this.filters.getFullTableName();
        if (Strings.isNullOrEmpty(schemaTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);
        if (physicalTableName.isSearchPattern()) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        TableWrapper tableWrapper = tableList.getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(tableWrapper);
    }

    /**
     * Accepts a table wrapper (lazy loader).
     *
     * @param tableWrapper Table wrapper.
     * @param parameter    Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableWrapper tableWrapper, SearchParameterObject parameter) {
        String schemaTableName = this.filters.getFullTableName();

        if (Strings.isNullOrEmpty(schemaTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);
        if (physicalTableName.isSearchPattern()) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        if (tableWrapper.getPhysicalTableName().matchPattern(physicalTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec Table specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setTableLabels(tableSpec.getLabels());
        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = parameter.getDataStreamSearcherObject();
        dataGroupingConfigurationSearcherObject.setTableDataGroupingConfigurations(tableSpec.getGroupings());
        dataGroupingConfigurationSearcherObject.setDefaultDataGrouping(tableSpec.getDefaultGroupingName());

        if (tableSpec.isDisabled() && (enabledFilter == null || enabledFilter)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column collection (map).
     *
     * @param columnSpecMap Column collection.
     * @param parameter     Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpecMap columnSpecMap, SearchParameterObject parameter) {
        // TODO: The HierarchyTree structure doesn't allow for similar filtering when filters.checkTarget == column.
        //       Both table and column-level checks will be included.
        //       We need to think it through and implement changes.
        if (this.filters.getCheckTarget() == CheckTarget.table) {
            return TreeNodeTraversalResult.SKIP_CHILDREN; // column checks don't concern us
        }

        String columnNameFilter = this.filters.getColumn();
        if (Strings.isNullOrEmpty(columnNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(columnNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact column name given, let's find it
        ColumnSpec columnSpec = columnSpecMap.get(columnNameFilter);
        if (columnSpec == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(columnSpec);
    }

    /**
     * Accepts a column specification.
     *
     * @param columnSpec Column specification.
     * @param parameter  Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setColumnLabels(columnSpec.getLabels());

        if (columnSpec.isDisabled() && (enabledFilter == null || enabledFilter)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (this.filters.getColumnDataType() != null
                && !this.filters.getColumnDataType().equals(columnSpec.getTypeSnapshot().getColumnType())) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        Boolean columnIsNullable = columnSpec.getTypeSnapshot() == null ? null : columnSpec.getTypeSnapshot().getNullable();
        if (this.filters.getColumnNullable() != null
                && (columnIsNullable == null || this.filters.getColumnNullable() ^ columnIsNullable)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (columnSpec.isDisabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        String columnNameFilter = this.filters.getColumn();
        if (Strings.isNullOrEmpty(columnNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        String columnName = columnSpec.getHierarchyId().getLast().toString();
        if (StringPatternComparer.matchSearchPattern(columnName, columnNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts any check specification.
     *
     * @param abstractCheckSpec Data quality check specification (any).
     * @param parameter     Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckSpec<?,?,?,?> abstractCheckSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = parameter.getDataStreamSearcherObject();
        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();

        AbstractSensorParametersSpec sensorParameters = abstractCheckSpec.getParameters();
        boolean checkEnabled = !abstractCheckSpec.isDisabled();
        if ((enabledFilter != null && (checkEnabled ^ enabledFilter))
                || (enabledFilter == null && !checkEnabled)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        DataGroupingConfigurationSpec selectedGroupingConfiguration =
                abstractCheckSpec.getDataGrouping() != null && dataGroupingConfigurationSearcherObject.getTableDataGroupingConfigurations() != null ?
                        dataGroupingConfigurationSearcherObject.getTableDataGroupingConfigurations().get(abstractCheckSpec.getDataGrouping()) : null;
        LabelSetSpec overriddenLabels = new LabelSetSpec();

        if (labelsSearcherObject.getColumnLabels() != null) {
            overriddenLabels.addAll(labelsSearcherObject.getColumnLabels());
        }

        if (labelsSearcherObject.getTableLabels() != null) {
            overriddenLabels.addAll(labelsSearcherObject.getTableLabels());
        }

        if (labelsSearcherObject.getConnectionLabels() != null) {
            overriddenLabels.addAll(labelsSearcherObject.getConnectionLabels());
        }

        if (!DataStreamsTagsSearchMatcher.matchAllCheckDataStreamsMapping(this.filters, selectedGroupingConfiguration)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }
        if (!LabelsSearchMatcher.matchCheckLabels(this.filters, overriddenLabels)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }


        String checkNameFilter = this.filters.getCheckName();
        if (!Strings.isNullOrEmpty(checkNameFilter)) {
            String checkName = abstractCheckSpec.getCheckName();
            if (!StringPatternComparer.matchSearchPattern(checkName, checkNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        String sensorNameFilter = this.filters.getSensorName();
        if (!Strings.isNullOrEmpty(sensorNameFilter)) {
            if (sensorParameters == null) {
                return TreeNodeTraversalResult.SKIP_CHILDREN; // sensor is not configured (has no parameters, we don't know what to run)
            }

            String sensorDefinitionName;

            if (abstractCheckSpec instanceof CustomCheckSpec) {
                CustomCheckSpec customCheckSpec = (CustomCheckSpec) abstractCheckSpec;
                sensorDefinitionName = customCheckSpec.getSensorName(); // we can filter by a sensor name only for custom checks that have an explicitly given a sensor name, user defined custom checks cannot be filtered this way
            }
            else {
                sensorDefinitionName = sensorParameters.getSensorDefinitionName();
            }

            if (!StringPatternComparer.matchSearchPattern(sensorDefinitionName, sensorNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        Set<HierarchyId> checkHierarchyIds = this.filters.getCheckHierarchyIds();
        if (checkHierarchyIds != null) {
            if (!checkHierarchyIds.contains(abstractCheckSpec.getHierarchyId())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        parameter.getNodes().add(abstractCheckSpec);

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to search any deeper, we have found what we were looking for
    }

    /**
     * Accepts a container of categories of data quality checks.
     *
     * @param checksContainerSpec Container of data quality checks that has nested categories (and categories contain checks).
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRootChecksContainerSpec checksContainerSpec, SearchParameterObject parameter) {
        CheckType checkTypeFilter = this.filters.getCheckType();
        if (checkTypeFilter != null) {
            if (checksContainerSpec.getCheckType() != checkTypeFilter) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        CheckTimeScale checkTimeScaleFilter = this.filters.getTimeScale();
        if (checkTimeScaleFilter != null) {
            if (checksContainerSpec.getCheckTimeScale() != checkTimeScaleFilter) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        CheckTarget checkTarget = Strings.isNullOrEmpty(this.filters.getColumn()) ? null : CheckTarget.column;
        if (checkTarget != null && checkTarget != checksContainerSpec.getCheckTarget()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        return super.accept(checksContainerSpec, parameter);
    }

    /**
     * Accepts a container of data quality checks for a single category.
     *
     * @param abstractCheckCategorySpec Container of data quality checks for a single category.
     * @param parameter                 Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckCategorySpec abstractCheckCategorySpec, SearchParameterObject parameter) {
        if (abstractCheckCategorySpec instanceof AbstractComparisonCheckCategorySpec) {
            AbstractComparisonCheckCategorySpec comparisonCheckCategorySpec = (AbstractComparisonCheckCategorySpec)abstractCheckCategorySpec;
            String dataComparisonNameFilter = this.filters.getTableComparisonName();
            if (!Strings.isNullOrEmpty(dataComparisonNameFilter)) {
                String comparisonName = comparisonCheckCategorySpec.getComparisonName();
                if (!StringPatternComparer.matchSearchPattern(comparisonName, dataComparisonNameFilter)) {
                    return TreeNodeTraversalResult.SKIP_CHILDREN;
                }
            }
        } else {
            String checkCategoryFilter = this.filters.getCheckCategory();
            if (!Strings.isNullOrEmpty(checkCategoryFilter)) {
                String categoryName = abstractCheckCategorySpec.getHierarchyId().getLast().toString();
                if (!StringPatternComparer.matchSearchPattern(categoryName, checkCategoryFilter)) {
                    return TreeNodeTraversalResult.SKIP_CHILDREN;
                }
            }
        }

        return super.accept(abstractCheckCategorySpec, parameter);
    }

    /**
     * Accepts a dictionary of custom checks. The keys must be names of configured custom checks.
     *
     * @param customCheckSpecMap Dictionary of custom checks.
     * @param parameter          Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CustomCheckSpecMap customCheckSpecMap, SearchParameterObject parameter) {
        String checkCategoryFilter = this.filters.getCheckCategory();
        if (!Strings.isNullOrEmpty(checkCategoryFilter)) {
            if (!StringPatternComparer.matchSearchPattern("custom", checkCategoryFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }
        return super.accept(customCheckSpecMap, parameter);
    }

    /**
     * Accepts a map of comparison checks for a named comparison.
     *
     * @param abstractComparisonCheckCategorySpecMap Comparison map with checks.
     * @param parameter                              Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractComparisonCheckCategorySpecMap<?> abstractComparisonCheckCategorySpecMap, SearchParameterObject parameter) {
        String checkCategoryFilter = this.filters.getCheckCategory();
        if (!Strings.isNullOrEmpty(checkCategoryFilter)) {
            String comparisonsObjectFieldName = abstractComparisonCheckCategorySpecMap.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(comparisonsObjectFieldName, checkCategoryFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        String dataComparisonName = this.filters.getTableComparisonName();
        if (!Strings.isNullOrEmpty(dataComparisonName)) {
            if (StringPatternComparer.isSearchPattern(dataComparisonName)) {
                return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
            }

            // exact comparison name given, let's find it
            AbstractComparisonCheckCategorySpec comparisonCheckCategorySpec = abstractComparisonCheckCategorySpecMap.get(dataComparisonName);
            if (comparisonCheckCategorySpec == null) {
                return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
            }

            return TreeNodeTraversalResult.traverseSelectedChildNodes(comparisonCheckCategorySpec);
        }
        return super.accept(abstractComparisonCheckCategorySpecMap, parameter);
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
