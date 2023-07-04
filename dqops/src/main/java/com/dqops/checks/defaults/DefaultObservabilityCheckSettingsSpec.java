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
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
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
public class DefaultObservabilityCheckSettingsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultObservabilityCheckSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("profiling", o -> o.profiling);
            put("recurring_daily", o -> o.recurringDaily);
            put("recurring_monthly", o -> o.recurringMonthly);
        }
    };

    @JsonPropertyDescription("Default configuration of advanced profiling checks that are enabled on tables and columns that are imported into DQO.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultProfilingObservabilityCheckSettingsSpec profiling = new DefaultProfilingObservabilityCheckSettingsSpec();

    @JsonPropertyDescription("Default configuration of daily recurring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQO.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultDailyRecurringObservabilityCheckSettingsSpec recurringDaily = new DefaultDailyRecurringObservabilityCheckSettingsSpec();

    @JsonPropertyDescription("Default configuration of daily recurring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQO.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DefaultMonthlyRecurringObservabilityCheckSettingsSpec recurringMonthly = new DefaultMonthlyRecurringObservabilityCheckSettingsSpec();

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
     * Returns the configuration of default recurring daily checks.
     * @return Default configuration of recurring daily checks.
     */
    public DefaultDailyRecurringObservabilityCheckSettingsSpec getRecurringDaily() {
        return recurringDaily;
    }

    /**
     * Sets the configuration of default recurring daily checks.
     * @param recurringDaily Default recurring daily checks.
     */
    public void setRecurringDaily(DefaultDailyRecurringObservabilityCheckSettingsSpec recurringDaily) {
        setDirtyIf(!Objects.equals(this.recurringDaily, recurringDaily));
        this.recurringDaily = recurringDaily;
        propagateHierarchyIdToField(recurringDaily, "recurring_daily");
    }

    /**
     * Returns the configuration of default recurring monthly checks.
     * @return Default configuration of recurring monthly checks.
     */
    public DefaultMonthlyRecurringObservabilityCheckSettingsSpec getRecurringMonthly() {
        return recurringMonthly;
    }

    /**
     * Sets the configuration of default recurring monthly checks.
     * @param recurringMonthly Default recurring monthly checks.
     */
    public void setRecurringMonthly(DefaultMonthlyRecurringObservabilityCheckSettingsSpec recurringMonthly) {
        setDirtyIf(!Objects.equals(this.recurringMonthly, recurringMonthly));
        this.recurringMonthly = recurringMonthly;
        propagateHierarchyIdToField(recurringMonthly, "recurring_monthly");
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
        if (this.recurringDaily != null) {
            this.recurringDaily.applyOnTable(targetTable, dialectSettings);
        }
        if (this.recurringMonthly != null) {
            this.recurringMonthly.applyOnTable(targetTable, dialectSettings);
        }
    }
}
