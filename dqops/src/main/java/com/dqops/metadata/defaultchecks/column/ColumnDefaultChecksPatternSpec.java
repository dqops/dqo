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

package com.dqops.metadata.defaultchecks.column;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The default configuration of column-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDefaultChecksPatternSpec extends AbstractSpec implements InvalidYamlStatusHolder {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDefaultChecksPatternSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("profiling_checks", o -> o.profilingChecks);
            put("monitoring_checks", o -> o.monitoringChecks);
            put("partitioned_checks", o -> o.partitionedChecks);
        }
    };

    @JsonPropertyDescription("Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnProfilingCheckCategoriesSpec profilingChecks = new ColumnProfilingCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnMonitoringCheckCategoriesSpec monitoringChecks = new ColumnMonitoringCheckCategoriesSpec();

    @JsonPropertyDescription("Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPartitionedCheckCategoriesSpec partitionedChecks = new ColumnPartitionedCheckCategoriesSpec();


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
     * Returns configuration of enabled table level profiling data quality checks.
     * @return Table level profiling data quality checks.
     */
    public ColumnProfilingCheckCategoriesSpec getProfilingChecks() {
        return profilingChecks;
    }

    /**
     * Sets a new configuration of table level profiling data quality checks.
     * @param profilingChecks New profiling checks configuration.
     */
    public void setProfilingChecks(ColumnProfilingCheckCategoriesSpec profilingChecks) {
        setDirtyIf(!Objects.equals(this.profilingChecks, profilingChecks));
        this.profilingChecks = profilingChecks;
        propagateHierarchyIdToField(profilingChecks, "profiling_checks");
    }

    /**
     * Returns configuration of enabled table level monitoring.
     * @return Table level monitoring.
     */
    public ColumnMonitoringCheckCategoriesSpec getMonitoringChecks() {
        return monitoringChecks;
    }

    /**
     * Sets a new configuration of table level data quality monitoring checks.
     * @param monitoringChecks New monitoring checks configuration.
     */
    public void setMonitoringChecks(ColumnMonitoringCheckCategoriesSpec monitoringChecks) {
        setDirtyIf(!Objects.equals(this.monitoringChecks, monitoringChecks));
        this.monitoringChecks = monitoringChecks;
        propagateHierarchyIdToField(monitoringChecks, "monitoring_checks");
    }

    /**
     * Returns configuration of enabled table level date/time partitioned checks.
     * @return Table level date/time partitioned checks.
     */
    public ColumnPartitionedCheckCategoriesSpec getPartitionedChecks() {
        return partitionedChecks;
    }

    /**
     * Sets a new configuration of table level date/time partitioned data quality checks.
     * @param partitionedChecks New configuration of date/time partitioned checks.
     */
    public void setPartitionedChecks(ColumnPartitionedCheckCategoriesSpec partitionedChecks) {
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
     * Applies the default checks on a target column.
     * @param targetColumn Target column.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnColumn(ColumnSpec targetColumn, ProviderDialectSettings dialectSettings) {
        if (this.profilingChecks != null && !this.profilingChecks.isDefault()) {
            AbstractRootChecksContainerSpec tableProfilingContainer = targetColumn.getColumnCheckRootContainer(CheckType.profiling, null, true);
            this.profilingChecks.copyChecksToContainer(tableProfilingContainer, null, dialectSettings);
        }

        if (this.monitoringChecks != null) {
            AbstractRootChecksContainerSpec defaultChecksDaily = this.monitoringChecks.getDaily();
            if (defaultChecksDaily != null && !defaultChecksDaily.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetColumn.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.daily, true);
                defaultChecksDaily.copyChecksToContainer(targetContainer, null, dialectSettings);
            }

            AbstractRootChecksContainerSpec defaultChecksMonthly = this.monitoringChecks.getMonthly();
            if (defaultChecksMonthly != null && !defaultChecksMonthly.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetColumn.getColumnCheckRootContainer(CheckType.monitoring, CheckTimeScale.monthly, true);
                defaultChecksMonthly.copyChecksToContainer(targetContainer, null, dialectSettings);
            }
        }

        if (this.partitionedChecks != null) {
            AbstractRootChecksContainerSpec defaultChecksDaily = this.partitionedChecks.getDaily();
            if (defaultChecksDaily != null && !defaultChecksDaily.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetColumn.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.daily, true);
                defaultChecksDaily.copyChecksToContainer(targetContainer, null, dialectSettings);
            }

            AbstractRootChecksContainerSpec defaultChecksMonthly = this.partitionedChecks.getMonthly();
            if (defaultChecksMonthly != null && !defaultChecksMonthly.isDefault()) {
                AbstractRootChecksContainerSpec targetContainer = targetColumn.getColumnCheckRootContainer(CheckType.partitioned, CheckTimeScale.monthly, true);
                defaultChecksMonthly.copyChecksToContainer(targetContainer, null, dialectSettings);
            }
        }
    }

    /**
     * Retrieves a non-null root check container for the requested category.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the column default checks specification.
     *
     * @param checkType            Check type.
     * @param checkTimeScale       Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the column.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getColumnCheckRootContainer(CheckType checkType,
                                                                       CheckTimeScale checkTimeScale,
                                                                       boolean attachCheckContainer) {
        return getColumnCheckRootContainer(checkType, checkTimeScale, attachCheckContainer, true);
    }

    /**
     * Retrieves a non-null root check container for the requested category.  Returns null when the check container is not present.
     * Creates a new check root container object if there was no such object configured and referenced
     * from the column default checks specification.
     *
     * @param checkType            Check type.
     * @param checkTimeScale       Time scale. Null value is accepted for profiling checks, for other time scale aware checks, the proper time scale is required.
     * @param attachCheckContainer When the check container doesn't exist, should the newly created check container be attached to the column.
     * @param createEmptyContainerWhenNull Creates a new check container instance when it is null.
     * @return Newly created container root.
     */
    public AbstractRootChecksContainerSpec getColumnCheckRootContainer(CheckType checkType,
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

                ColumnProfilingCheckCategoriesSpec columnProfilingCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
                columnProfilingCheckCategoriesSpec.setHierarchyId(HierarchyId.makeChildOrNull(this.getHierarchyId(), "profiling_checks"));
                if (attachCheckContainer) {
                    this.profilingChecks = columnProfilingCheckCategoriesSpec;
                }
                return columnProfilingCheckCategoriesSpec;
            }

            case monitoring: {
                ColumnMonitoringCheckCategoriesSpec monitoringSpec = this.monitoringChecks;
                if (monitoringSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    monitoringSpec = new ColumnMonitoringCheckCategoriesSpec();
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

                        ColumnDailyMonitoringCheckCategoriesSpec dailyMonitoringCategoriesSpec = new ColumnDailyMonitoringCheckCategoriesSpec();
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

                        ColumnMonthlyMonitoringCheckCategoriesSpec monthlyMonitoringCategoriesSpec = new ColumnMonthlyMonitoringCheckCategoriesSpec();
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
                ColumnPartitionedCheckCategoriesSpec partitionedChecksSpec = this.partitionedChecks;
                if (partitionedChecksSpec == null) {
                    if (!createEmptyContainerWhenNull) {
                        return null;
                    }

                    partitionedChecksSpec = new ColumnPartitionedCheckCategoriesSpec();
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

                        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitionedCategoriesSpec = new ColumnDailyPartitionedCheckCategoriesSpec();
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

                        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCategoriesSpec = new ColumnMonthlyPartitionedCheckCategoriesSpec();
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
     * The object could be a profiling check container, one of monitoring containers or one of partitioned checks container.
     * @param checkRootContainer Root check container to store.
     */
    @JsonIgnore
    public void setColumnCheckRootContainer(AbstractRootChecksContainerSpec checkRootContainer) {
        if (checkRootContainer == null) {
            throw new NullPointerException("Root check container cannot be null");
        }

        if (checkRootContainer instanceof ColumnProfilingCheckCategoriesSpec) {
            this.setProfilingChecks((ColumnProfilingCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnDailyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setDaily((ColumnDailyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnMonthlyMonitoringCheckCategoriesSpec) {
            if (this.monitoringChecks == null) {
                this.setMonitoringChecks(new ColumnMonitoringCheckCategoriesSpec());
            }

            this.getMonitoringChecks().setMonthly((ColumnMonthlyMonitoringCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnDailyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setDaily((ColumnDailyPartitionedCheckCategoriesSpec)checkRootContainer);
        }
        else if (checkRootContainer instanceof ColumnMonthlyPartitionedCheckCategoriesSpec) {
            if (this.partitionedChecks == null) {
                this.setPartitionedChecks(new ColumnPartitionedCheckCategoriesSpec());
            }

            this.getPartitionedChecks().setMonthly((ColumnMonthlyPartitionedCheckCategoriesSpec)checkRootContainer);
        } else {
            throw new IllegalArgumentException("Unsupported check root container type " + checkRootContainer.getClass().getCanonicalName());
        }
    }
}
