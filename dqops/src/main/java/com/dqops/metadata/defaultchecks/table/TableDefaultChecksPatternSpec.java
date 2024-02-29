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

package com.dqops.metadata.defaultchecks.table;

import com.dqops.checks.*;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TablePartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.*;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The default configuration of table-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on tables.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableDefaultChecksPatternSpec extends AbstractSpec implements InvalidYamlStatusHolder {
    public static final ChildHierarchyNodeFieldMapImpl<TableDefaultChecksPatternSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("target", o -> o.target);
            put("profiling_checks", o -> o.profilingChecks);
            put("monitoring_checks", o -> o.monitoringChecks);
            put("partitioned_checks", o -> o.partitionedChecks);
        }
    };

    @JsonPropertyDescription("The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.")
    private int priority;

    @JsonPropertyDescription("The target table filter that are filtering the table and connection on which the default checks are applied.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TargetTablePatternSpec target = new TargetTablePatternSpec();

    @JsonPropertyDescription("Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableProfilingCheckCategoriesSpec profilingChecks = new TableProfilingCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMonitoringCheckCategoriesSpec monitoringChecks = new TableMonitoringCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TablePartitionedCheckCategoriesSpec partitionedChecks = new TablePartitionedCheckCategoriesSpec();


    @JsonIgnore
    private String yamlParsingError;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }

    /**
     * Returns the apply priority.
     * @return Application priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of applying this pattern.
     * @param priority The priority of applying this pattern.
     */
    public void setPriority(int priority) {
        this.setDirtyIf(this.priority != priority);
        this.priority = priority;
    }

    /**
     * Returns the configuration of the target table using search patterns.
     * @return The filters for the target tables.
     */
    public TargetTablePatternSpec getTarget() {
        return target;
    }

    /**
     * Returns the filters for the target table.
     * @param target Target table filter.
     */
    public void setTarget(TargetTablePatternSpec target) {
        setDirtyIf(!Objects.equals(this.target, target));
        this.target = target;
        propagateHierarchyIdToField(target, "target");
    }

    /**
     * Returns configuration of enabled table level profiling data quality checks.
     * @return Table level profiling data quality checks.
     */
    public TableProfilingCheckCategoriesSpec getProfilingChecks() {
        return profilingChecks;
    }

    /**
     * Sets a new configuration of table level profiling data quality checks.
     * @param profilingChecks New profiling checks configuration.
     */
    public void setProfilingChecks(TableProfilingCheckCategoriesSpec profilingChecks) {
        setDirtyIf(!Objects.equals(this.profilingChecks, profilingChecks));
        this.profilingChecks = profilingChecks;
        propagateHierarchyIdToField(profilingChecks, "profiling_checks");
    }

    /**
     * Returns configuration of enabled table level monitoring.
     * @return Table level monitoring.
     */
    public TableMonitoringCheckCategoriesSpec getMonitoringChecks() {
        return monitoringChecks;
    }

    /**
     * Sets a new configuration of table level data quality monitoring checks.
     * @param monitoringChecks New monitoring checks configuration.
     */
    public void setMonitoringChecks(TableMonitoringCheckCategoriesSpec monitoringChecks) {
        setDirtyIf(!Objects.equals(this.monitoringChecks, monitoringChecks));
        this.monitoringChecks = monitoringChecks;
        propagateHierarchyIdToField(monitoringChecks, "monitoring_checks");
    }

    /**
     * Returns configuration of enabled table level date/time partitioned checks.
     * @return Table level date/time partitioned checks.
     */
    public TablePartitionedCheckCategoriesSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of table level date/time partitioned data quality checks.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(TablePartitionedCheckCategoriesSpec partitionedChecks) {
        setDirtyIf(!Objects.equals(this.partitionedChecks, partitionedChecks));
        this.partitionedChecks = partitionedChecks;
        propagateHierarchyIdToField(partitionedChecks, "partitioned_checks");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Retrieves the configuration pattern name from the hierarchy.
     * @return Configuration pattern name or null for a standalone default checks spec object.
     */
    @JsonIgnore
    public String getPatternName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }

    /**
     * Applies the default checks on a target table.
     * @param targetTable Target table.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnTable(TableSpec targetTable, ProviderDialectSettings dialectSettings) {
        if (this.profilingChecks != null && !this.profilingChecks.isDefault()) {
            AbstractRootChecksContainerSpec tableProfilingContainer = targetTable.getTableCheckRootContainer(CheckType.profiling, null, true);
            this.profilingChecks.copyChecksToContainer(tableProfilingContainer, targetTable, null, dialectSettings);
        }

        if (this.monitoringChecks != null) {
            AbstractRootChecksContainerSpec defaultChecksDaily = this.monitoringChecks.getDaily();
            if (defaultChecksDaily != null && !defaultChecksDaily.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetTable.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
                defaultChecksDaily.copyChecksToContainer(targetContainer, targetTable, null, dialectSettings);
            }

            AbstractRootChecksContainerSpec defaultChecksMonthly = this.monitoringChecks.getMonthly();
            if (defaultChecksMonthly != null && !defaultChecksMonthly.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetTable.getTableCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, true);
                defaultChecksMonthly.copyChecksToContainer(targetContainer, targetTable, null, dialectSettings);
            }
        }

        if (this.partitionedChecks != null && targetTable.getTimestampColumns() != null &&
                !Strings.isNullOrEmpty(targetTable.getTimestampColumns().getPartitionByColumn())) {
            AbstractRootChecksContainerSpec defaultChecksDaily = this.partitionedChecks.getDaily();
            if (defaultChecksDaily != null && !defaultChecksDaily.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetTable.getTableCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, true);
                defaultChecksDaily.copyChecksToContainer(targetContainer, targetTable,null, dialectSettings);
            }

            AbstractRootChecksContainerSpec defaultChecksMonthly = this.partitionedChecks.getMonthly();
            if (defaultChecksMonthly != null && !defaultChecksMonthly.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetTable.getTableCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, true);
                defaultChecksMonthly.copyChecksToContainer(targetContainer, targetTable, null, dialectSettings);
            }
        }
    }

    /**
     * Retrieves a non-null root check container for the requested category.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the default checks pattern specification.
     * @param checkType Check type.
     * @param checkTimeScale Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the table specification.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getTableCheckRootContainer(CheckType checkType,
                                                                      CheckTimeScale checkTimeScale,
                                                                      boolean attachCheckContainer) {
        return getTableCheckRootContainer(checkType, checkTimeScale, attachCheckContainer, true);
    }

    /**
     * Retrieves a non-null root check container for the requested category. Returns null when the check container is not present.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the table specification.
     * @param checkType Check type.
     * @param checkTimeScale Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the table specification.
     * @param createEmptyContainerWhenNull Creates a new check container instance when it is null.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getTableCheckRootContainer(CheckType checkType,
                                                                      CheckTimeScale checkTimeScale,
                                                                      boolean attachCheckContainer,
                                                                      boolean createEmptyContainerWhenNull) {

        switch (checkType) {
            case profiling: {
                if (this.profilingChecks != null) {
                    return this.profilingChecks;
                }

                if (!createEmptyContainerWhenNull) {
                    return null;
                }

                TableProfilingCheckCategoriesSpec tableProfilingCheckCategoriesSpec = new TableProfilingCheckCategoriesSpec();
                tableProfilingCheckCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "profiling_checks"));
                if (attachCheckContainer) {
                    this.profilingChecks = tableProfilingCheckCategoriesSpec;
                }
                return tableProfilingCheckCategoriesSpec;
            }

            case monitoring: {
                TableMonitoringCheckCategoriesSpec monitoringSpec = this.monitoringChecks;
                if (monitoringSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    monitoringSpec = new TableMonitoringCheckCategoriesSpec();
                    monitoringSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "monitoring_checks"));
                    if (attachCheckContainer) {
                        this.monitoringChecks = monitoringSpec;
                    }
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (monitoringSpec.getDaily() != null) {
                            return monitoringSpec.getDaily();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableDailyMonitoringCheckCategoriesSpec dailyMonitoringCategoriesSpec = new TableDailyMonitoringCheckCategoriesSpec();
                        dailyMonitoringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(monitoringSpec.getHierarchyId(), "daily"));
                        if (attachCheckContainer) {
                            monitoringSpec.setDaily(dailyMonitoringCategoriesSpec);
                        }
                        return dailyMonitoringCategoriesSpec;
                    }
                    case monthly: {
                        if (monitoringSpec.getMonthly() != null) {
                            return monitoringSpec.getMonthly();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableMonthlyMonitoringCheckCategoriesSpec monthlyMonitoringCategoriesSpec = new TableMonthlyMonitoringCheckCategoriesSpec();
                        monthlyMonitoringCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(monitoringSpec.getHierarchyId(), "monthly"));
                        if (attachCheckContainer) {
                            monitoringSpec.setMonthly(monthlyMonitoringCategoriesSpec);
                        }
                        return monthlyMonitoringCategoriesSpec;
                    }
                    default:
                        throw new IllegalArgumentException("Check time scale " + checkTimeScale + " is not supported");
                }
            }

            case partitioned: {
                TablePartitionedCheckCategoriesSpec partitionedChecksSpec = this.partitionedChecks;
                if (partitionedChecksSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    partitionedChecksSpec = new TablePartitionedCheckCategoriesSpec();
                    partitionedChecksSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "partitioned_checks"));
                    if (attachCheckContainer) {
                        this.partitionedChecks = partitionedChecksSpec;
                    }
                }

                switch (checkTimeScale) {
                    case daily: {
                        if (partitionedChecksSpec.getDaily() != null) {
                            return partitionedChecksSpec.getDaily();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCategoriesSpec = new TableDailyPartitionedCheckCategoriesSpec();
                        dailyPartitionedCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(partitionedChecksSpec.getHierarchyId(), "daily"));
                        if (attachCheckContainer) {
                            partitionedChecksSpec.setDaily(dailyPartitionedCategoriesSpec);
                        }
                        return dailyPartitionedCategoriesSpec;
                    }
                    case monthly: {
                        if (partitionedChecksSpec.getMonthly() != null) {
                            return partitionedChecksSpec.getMonthly();
                        }

                        if (!createEmptyContainerWhenNull) {
                            return null;
                        }

                        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCategoriesSpec = new TableMonthlyPartitionedCheckCategoriesSpec();
                        monthlyPartitionedCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(partitionedChecksSpec.getHierarchyId(), "monthly"));
                        if (attachCheckContainer) {
                            partitionedChecksSpec.setMonthly(monthlyPartitionedCategoriesSpec);
                        }
                        return monthlyPartitionedCategoriesSpec;
                    }
                    default:
                        throw new IllegalArgumentException("Check time scale " + checkTimeScale + " is not supported");
                }
            }

            default: {
                throw new IllegalArgumentException("Unsupported check type");
            }
        }
    }

    /**
     * Sets the given container of checks at a proper level of the check hierarchy.
     * The object can be a profiling check container, one of monitoring check containers or one of partitioned check containers.
     * @param checkRootContainer Root check container to store.
     */
    @JsonIgnore
    public void setTableCheckRootContainer(AbstractRootChecksContainerSpec checkRootContainer) {
        if (checkRootContainer == null) {
            throw new NullPointerException("Root check container cannot be null");
        }

        if (checkRootContainer instanceof TableProfilingCheckCategoriesSpec) {
            this.setProfilingChecks((TableProfilingCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableDailyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new TableMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setDaily((TableDailyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableMonthlyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new TableMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setMonthly((TableMonthlyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableDailyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new TablePartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setDaily((TableDailyPartitionedCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof TableMonthlyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new TablePartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setMonthly((TableMonthlyPartitionedCheckCategoriesSpec)checkRootContainer);
        } else {
            throw new IllegalArgumentException("Unsupported check root container type " + checkRootContainer.getClass().getCanonicalName());
        }
    }
}
