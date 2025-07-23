/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.monitoring.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.uniqueness.TableDuplicateRecordCountCheckSpec;
import com.dqops.checks.table.checkspecs.uniqueness.TableDuplicateRecordPercentCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level monthly monitoring for uniqueness data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableUniquenessMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableUniquenessMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_duplicate_record_count", o -> o.monthlyDuplicateRecordCount);
            put("monthly_duplicate_record_percent", o -> o.monthlyDuplicateRecordPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordCountCheckSpec monthlyDuplicateRecordCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDuplicateRecordPercentCheckSpec monthlyDuplicateRecordPercent;

    /**
     * Returns a duplicate record count check.
     * @return Duplicate record count check.
     */
    public TableDuplicateRecordCountCheckSpec getMonthlyDuplicateRecordCount() {
        return monthlyDuplicateRecordCount;
    }

    /**
     * Sets a new definition of a duplicate record count check.
     * @param monthlyDuplicateRecordCount Duplicate record count check.
     */
    public void setMonthlyDuplicateRecordCount(TableDuplicateRecordCountCheckSpec monthlyDuplicateRecordCount) {
		this.setDirtyIf(!Objects.equals(this.monthlyDuplicateRecordCount, monthlyDuplicateRecordCount));
        this.monthlyDuplicateRecordCount = monthlyDuplicateRecordCount;
		this.propagateHierarchyIdToField(monthlyDuplicateRecordCount, "monthly_duplicate_record_count");
    }

    /**
     * Returns a duplicate record percent check.
     * @return Duplicate record percent check.
     */
    public TableDuplicateRecordPercentCheckSpec getMonthlyDuplicateRecordPercent() {
        return monthlyDuplicateRecordPercent;
    }

    /**
     * Sets a new definition of a duplicate record percent check.
     * @param monthlyDuplicateRecordPercent Duplicate record percent check.
     */
    public void setMonthlyDuplicateRecordPercent(TableDuplicateRecordPercentCheckSpec monthlyDuplicateRecordPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicateRecordPercent, monthlyDuplicateRecordPercent));
        this.monthlyDuplicateRecordPercent = monthlyDuplicateRecordPercent;
        this.propagateHierarchyIdToField(monthlyDuplicateRecordPercent, "monthly_duplicate_record_percent");
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
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public TableUniquenessMonthlyMonitoringChecksSpec deepClone() {
        return (TableUniquenessMonthlyMonitoringChecksSpec)super.deepClone();
    }

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.monitoring;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }

    public static class TableUniquenessMonthlyMonitoringChecksSpecSampleFactory implements SampleValueFactory<TableUniquenessMonthlyMonitoringChecksSpec> {
        @Override
        public TableUniquenessMonthlyMonitoringChecksSpec createSample() {
            return new TableUniquenessMonthlyMonitoringChecksSpec() {{
                setMonthlyDuplicateRecordCount(new TableDuplicateRecordCountCheckSpec.TableDuplicateRecordCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
