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

import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.TableSpec;
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
 * The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 * for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DefaultObservabilityChecksSpec extends AbstractSpec implements InvalidYamlStatusHolder {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultObservabilityChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("profiling", o -> o.profiling);
            put("monitoring_daily", o -> o.monitoringDaily);
            put("monitoring_monthly", o -> o.monitoringMonthly);
        }
    };

    @JsonPropertyDescription("Default configuration of profiling checks that are enabled on tables and columns that are imported into DQOps.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultProfilingObservabilityCheckSettingsSpec profiling = new DefaultProfilingObservabilityCheckSettingsSpec();

    @JsonPropertyDescription("Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQOps.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultDailyMonitoringObservabilityCheckSettingsSpec monitoringDaily = new DefaultDailyMonitoringObservabilityCheckSettingsSpec();

    @JsonPropertyDescription("Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQOps.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultMonthlyMonitoringObservabilityCheckSettingsSpec monitoringMonthly = new DefaultMonthlyMonitoringObservabilityCheckSettingsSpec();

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
     * Returns the configuration of default profiling checks.
     * @return Default configuration of profiling checks.
     */
    public DefaultProfilingObservabilityCheckSettingsSpec getProfiling() {
        return profiling;
    }

    /**
     * Sets the configuration of default profiling checks.
     * @param profiling Default profiling checks.
     */
    public void setProfiling(DefaultProfilingObservabilityCheckSettingsSpec profiling) {
        setDirtyIf(!Objects.equals(this.profiling, profiling));
        this.profiling = profiling;
        propagateHierarchyIdToField(profiling, "profiling");
    }

    /**
     * Returns the configuration of default monitoring daily checks.
     * @return Default configuration of monitoring daily checks.
     */
    public DefaultDailyMonitoringObservabilityCheckSettingsSpec getMonitoringDaily() {
        return monitoringDaily;
    }

    /**
     * Sets the configuration of default monitoring daily checks.
     * @param monitoringDaily Default monitoring daily checks.
     */
    public void setMonitoringDaily(DefaultDailyMonitoringObservabilityCheckSettingsSpec monitoringDaily) {
        setDirtyIf(!Objects.equals(this.monitoringDaily, monitoringDaily));
        this.monitoringDaily = monitoringDaily;
        propagateHierarchyIdToField(monitoringDaily, "monitoring_daily");
    }

    /**
     * Returns the configuration of default monitoring monthly checks.
     * @return Default configuration of monitoring monthly checks.
     */
    public DefaultMonthlyMonitoringObservabilityCheckSettingsSpec getMonitoringMonthly() {
        return monitoringMonthly;
    }

    /**
     * Sets the configuration of default monitoring monthly checks.
     * @param monitoringMonthly Default monitoring monthly checks.
     */
    public void setMonitoringMonthly(DefaultMonthlyMonitoringObservabilityCheckSettingsSpec monitoringMonthly) {
        setDirtyIf(!Objects.equals(this.monitoringMonthly, monitoringMonthly));
        this.monitoringMonthly = monitoringMonthly;
        propagateHierarchyIdToField(monitoringMonthly, "monitoring_monthly");
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
     * Applies the checks on a target table.
     * @param targetTable Target table.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnTable(TableSpec targetTable, ProviderDialectSettings dialectSettings) {
        if (this.profiling != null) {
            this.profiling.applyOnTable(targetTable, dialectSettings);
        }
        if (this.monitoringDaily != null) {
            this.monitoringDaily.applyOnTable(targetTable, dialectSettings);
        }
        if (this.monitoringMonthly != null) {
            this.monitoringMonthly.applyOnTable(targetTable, dialectSettings);
        }
    }

    /**
     * Applies the checks on a target column.
     * @param targetColumn Target column.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnColumn(ColumnSpec targetColumn, ProviderDialectSettings dialectSettings) {
        if (this.profiling != null && this.profiling.getColumn() != null) {
            this.profiling.getColumn().applyOnColumn(targetColumn, dialectSettings);
        }
        if (this.monitoringDaily != null && this.monitoringDaily.getColumn() != null) {
            this.monitoringDaily.getColumn().applyOnColumn(targetColumn, dialectSettings);
        }
        if (this.monitoringMonthly != null && this.monitoringMonthly.getColumn() != null) {
            this.monitoringMonthly.getColumn().applyOnColumn(targetColumn, dialectSettings);
        }
    }
}
