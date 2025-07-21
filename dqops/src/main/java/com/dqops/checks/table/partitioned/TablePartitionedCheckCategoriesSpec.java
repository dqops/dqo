/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.partitioned;

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
 * Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TablePartitionedCheckCategoriesSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TablePartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("daily", o -> o.daily);
            put("monthly", o -> o.monthly);
        }
    };

    @JsonPropertyDescription("Configuration of day partitioned data quality checks evaluated at a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDailyPartitionedCheckCategoriesSpec daily;

    @JsonPropertyDescription("Configuration of monthly partitioned data quality checks evaluated at a table level..")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMonthlyPartitionedCheckCategoriesSpec monthly;

    // TODO: add other time periods that make sense (hourly, weekly, etc.)

    /**
     * Returns daily partitioned checks.
     * @return Daily partitioned checks.
     */
    public TableDailyPartitionedCheckCategoriesSpec getDaily() {
        return daily;
    }

    /**
     * Sets the daily partitioned checks container.
     * @param daily New daily partitioned checks container.
     */
    public void setDaily(TableDailyPartitionedCheckCategoriesSpec daily) {
		this.setDirtyIf(!Objects.equals(this.daily, daily));
        this.daily = daily;
		this.propagateHierarchyIdToField(daily, "daily");
    }

    /**
     * Returns monthly partitioned checks.
     * @return Monthly partitioned checks.
     */
    public TableMonthlyPartitionedCheckCategoriesSpec getMonthly() {
        return monthly;
    }

    /**
     * Sets the monthly partitioned checks container.
     * @param monthly New monthly partitioned checks container.
     */
    public void setMonthly(TableMonthlyPartitionedCheckCategoriesSpec monthly) {
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
