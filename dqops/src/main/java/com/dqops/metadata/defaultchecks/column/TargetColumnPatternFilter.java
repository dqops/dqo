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

import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.defaultchecks.table.TargetTablePatternFilter;
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
    private SearchPattern columnPattern;
    private SearchPattern dataTypePattern;
    private DataTypeCategory dataTypeCategory;

    /**
     * Creates a column pattern search object given a target column pattern specification. Creates search patterns for filled fields.
     * @param columnPatternSpec Target column search pattern.
     */
    public TargetColumnPatternFilter(TargetColumnPatternSpec columnPatternSpec) {
        super(columnPatternSpec);

        if (!Strings.isNullOrEmpty(columnPatternSpec.getColumn())) {
            this.columnPattern = SearchPattern.create(false, columnPatternSpec.getColumn());
        }

        if (!Strings.isNullOrEmpty(columnPatternSpec.getDataType())) {
            this.dataTypePattern = SearchPattern.create(false, columnPatternSpec.getDataType());
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
            if (!this.columnPattern.match(columnSpec.getColumnName())) {
                return false;
            }
        }

        if (this.dataTypePattern != null) {
            if (columnSpec.getTypeSnapshot() == null || !this.dataTypePattern.match(columnSpec.getTypeSnapshot().getColumnType())) {
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
                    if (this.labelPattern.match(label)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
