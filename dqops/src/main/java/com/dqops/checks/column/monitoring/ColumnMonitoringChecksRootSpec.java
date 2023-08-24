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
package com.dqops.checks.column.monitoring;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.*;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of column level monitoring, divided by the time window (daily, monthly, etc.)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnMonitoringChecksRootSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnMonitoringChecksRootSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("daily", o -> o.daily);
            put("monthly", o -> o.monthly);
        }
    };

    @JsonPropertyDescription("Configuration of daily monitoring evaluated at a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDailyMonitoringCheckCategoriesSpec daily;

    @JsonPropertyDescription("Configuration of monthly monitoring evaluated at a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnMonthlyMonitoringCheckCategoriesSpec monthly;

    // TODO: add other time periods that make sense (hourly, weekly, etc.)

    /**
     * Returns daily monitoring.
     *
     * @return Daily monitoring.
     */
    public ColumnDailyMonitoringCheckCategoriesSpec getDaily() {
        return daily;
    }

    /**
     * Sets the daily monitoring checks container.
     *
     * @param daily New daily monitoring checks container.
     */
    public void setDaily(ColumnDailyMonitoringCheckCategoriesSpec daily) {
        this.setDirtyIf(!Objects.equals(this.daily, daily));
        this.daily = daily;
        this.propagateHierarchyIdToField(daily, "daily");
    }

    /**
     * Returns monthly monitoring checks.
     *
     * @return Monthly monitoring checks.
     */
    public ColumnMonthlyMonitoringCheckCategoriesSpec getMonthly() {
        return monthly;
    }

    /**
     * Sets the monthly monitoring checks container.
     *
     * @param monthly New monthly monitoring checks container.
     */
    public void setMonthly(ColumnMonthlyMonitoringCheckCategoriesSpec monthly) {
        this.setDirtyIf(!Objects.equals(this.monthly, monthly));
        this.monthly = monthly;
        this.propagateHierarchyIdToField(monthly, "monthly");
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
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Checks if there are any configured checks (not null) in any check category.
     *
     * @return True when there are some checks configured, false when all checks are nulls.
     */
    public boolean hasAnyConfiguredChecks() {
        for (ChildFieldEntry childFieldEntry : this.getChildMap().getChildEntries()) {
            HierarchyNode childNode = childFieldEntry.getGetChildFunc().apply(this);

            if (childNode instanceof AbstractRootChecksContainerSpec) {
                AbstractRootChecksContainerSpec checksRootSpec = (AbstractRootChecksContainerSpec) childNode;
                if (checksRootSpec.hasAnyConfiguredChecks()) {
                    return true;
                }
            }
        }

        return false;
    }
}
