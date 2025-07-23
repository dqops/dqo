/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.comparisons;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecList;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

/**
 * List of column pairs used for grouping and joining in the table comparison checks.
 */
public class TableComparisonGroupingColumnsPairsListSpec extends AbstractDirtyTrackingSpecList<TableComparisonGroupingColumnsPairSpec> {
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
     * Creates and returns a copy of this object.
     */
    @Override
    public TableComparisonGroupingColumnsPairsListSpec deepClone() {
        TableComparisonGroupingColumnsPairsListSpec cloned = new TableComparisonGroupingColumnsPairsListSpec();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (TableComparisonGroupingColumnsPairSpec columnsPairSpec : this) {
            cloned.add(columnsPairSpec.deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    /**
     * Creates a data grouping configuration for the compared table.
     * @return The data grouping configuration for the compared table.
     */
    public DataGroupingConfigurationSpec createDataGroupingConfigurationForComparedTable() {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        if (this.getHierarchyId() != null) {
            dataGroupingConfigurationSpec.setHierarchyId(this.getHierarchyId().getParentHierarchyId()); // if we use the parent hierarchy id, the name of the data grouping will be the same as the data comparison (because we take the name of the last hierarchy id node)
        }

        for (int i = 0; i < 9 && i < this.size(); i++) {
            TableComparisonGroupingColumnsPairSpec tableComparisonGroupingColumnsPairSpec = this.get(i);
            DataGroupingDimensionSpec groupingDimensionSpec = tableComparisonGroupingColumnsPairSpec.createGroupingDimensionForComparedTable();
            dataGroupingConfigurationSpec.setLevel(i + 1, groupingDimensionSpec);
        }

        return dataGroupingConfigurationSpec;
    }

    /**
     * Creates a data grouping configuration for the reference table.
     * @return The data grouping configuration for the reference table.
     */
    public DataGroupingConfigurationSpec createDataGroupingConfigurationForReferenceTable() {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec();
        if (this.getHierarchyId() != null) {
            dataGroupingConfigurationSpec.setHierarchyId(this.getHierarchyId().getParentHierarchyId()); // if we use the parent hierarchy id, the name of the data grouping will be the same as the data comparison (because we take the name of the last hierarchy id node)
        }

        for (int i = 0; i < 9 && i < this.size(); i++) {
            TableComparisonGroupingColumnsPairSpec tableComparisonGroupingColumnsPairSpec = this.get(i);
            DataGroupingDimensionSpec groupingDimensionSpec = tableComparisonGroupingColumnsPairSpec.createGroupingDimensionForReferenceTable();
            dataGroupingConfigurationSpec.setLevel(i + 1, groupingDimensionSpec);
        }

        return dataGroupingConfigurationSpec;
    }
}
