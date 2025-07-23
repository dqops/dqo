/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.policies.column;

import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.policies.table.TargetTablePatternFilter;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.sources.TableSpec;
import com.google.common.base.Strings;

/**
 * Target column filter for default checks that uses parsed patterns.
 */
public class TargetColumnPatternFilter extends TargetTablePatternFilter {
    private SearchPattern[] columnPattern;
    private SearchPattern[] dataTypePattern;
    private DataTypeCategory dataTypeCategory;

    /**
     * Creates a column pattern search object given a target column pattern specification. Creates search patterns for filled fields.
     * @param columnPatternSpec Target column search pattern.
     */
    public TargetColumnPatternFilter(TargetColumnPatternSpec columnPatternSpec) {
        super(columnPatternSpec);

        if (!Strings.isNullOrEmpty(columnPatternSpec.getColumn())) {
            this.columnPattern = SearchPattern.createForCommaSeparatedPatterns(false, columnPatternSpec.getColumn());
        }

        if (!Strings.isNullOrEmpty(columnPatternSpec.getDataType())) {
            this.dataTypePattern = SearchPattern.createForCommaSeparatedPatterns(false, columnPatternSpec.getDataType());
        }

        this.dataTypeCategory = columnPatternSpec.getDataTypeCategory();
    }

    /**
     * Matches a connection, table and column to a search pattern.
     * @param connectionSpec Connection spec.
     * @param tableSpec Table spec.
     * @param columnSpec Column spec.
     * @param dataTypeCategory Data type category of the column.
     * @return True when the filter passes (the table/column matches a filter), false when not.
     */
    public boolean match(ConnectionSpec connectionSpec, TableSpec tableSpec, ColumnSpec columnSpec, DataTypeCategory dataTypeCategory) {
        if (this.columnPattern != null) {
            if (!SearchPattern.matchAny(this.columnPattern, columnSpec.getColumnName())) {
                return false;
            }
        }

        if (this.dataTypePattern != null) {
            if (columnSpec.getTypeSnapshot() == null || !SearchPattern.matchAny(this.dataTypePattern, columnSpec.getTypeSnapshot().getColumnType())) {
                return false;
            }
        }

        if (this.dataTypeCategory != null) {
            if (this.dataTypeCategory != dataTypeCategory) {
                return false;
            }
        }

        if (super.match(connectionSpec, tableSpec, true)) {
            return true;
        }

        if (this.labelPattern != null && super.match(connectionSpec, tableSpec, false)) {
            LabelSetSpec columnSpecLabels = columnSpec.getLabels();
            if (columnSpecLabels != null && !columnSpecLabels.isEmpty()) {
                for (String label : columnSpecLabels) {
                    if (SearchPattern.matchAny(this.labelPattern, label)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
