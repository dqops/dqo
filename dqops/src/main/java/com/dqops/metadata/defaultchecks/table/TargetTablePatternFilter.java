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

import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.LabelSetSpec;
import com.dqops.metadata.sources.TableSpec;
import com.google.common.base.Strings;

/**
 * Target table filter for default checks that uses parsed patterns.
 */
public class TargetTablePatternFilter {
    private SearchPattern connectionPattern;
    private SearchPattern schemaPattern;
    private SearchPattern tablePattern;
    protected SearchPattern labelPattern;

    /**
     * Creates a table pattern search object given a target table pattern specification. Creates search patterns for filled fields.
     * @param tablePatternSpec Target table search pattern.
     */
    public TargetTablePatternFilter(TargetTablePatternSpec tablePatternSpec) {
        if (!Strings.isNullOrEmpty(tablePatternSpec.getConnection())) {
            this.connectionPattern = SearchPattern.create(false, tablePatternSpec.getConnection());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getSchema())) {
            this.schemaPattern = SearchPattern.create(false, tablePatternSpec.getSchema());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getTable())) {
            this.tablePattern = SearchPattern.create(false, tablePatternSpec.getTable());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getLabel())) {
            this.labelPattern = SearchPattern.create(false, tablePatternSpec.getLabel());
        }
    }

    /**
     * Matches a connection and table to a search pattern.
     * @param connectionSpec Connection spec.
     * @param tableSpec Table spec.
     * @param matchLabels Also match labels.
     * @return True when the filter passes (the table matches a filter), false when not.
     */
    public boolean match(ConnectionSpec connectionSpec, TableSpec tableSpec, boolean matchLabels) {
        if (this.connectionPattern != null) {
            if (!this.connectionPattern.match(connectionSpec.getConnectionName())) {
                return false;
            }
        }

        if (this.schemaPattern != null) {
            if (!this.schemaPattern.match(tableSpec.getPhysicalTableName().getSchemaName())) {
                return false;
            }
        }

        if (this.tablePattern != null) {
            if (!this.tablePattern.match(tableSpec.getPhysicalTableName().getTableName())) {
                return false;
            }
        }

        if (matchLabels && this.labelPattern != null) {
            LabelSetSpec connectionLabels = connectionSpec.getLabels();
            if (connectionLabels != null && !connectionLabels.isEmpty()) {
                for (String label : connectionLabels) {
                    if (this.labelPattern.match(label)) {
                        return true;
                    }
                }
            }

            LabelSetSpec tableLabels = tableSpec.getLabels();
            if (tableLabels != null && !tableLabels.isEmpty()) {
                for (String label : tableLabels) {
                    if (this.labelPattern.match(label)) {
                        return true;
                    }
                }
            }

            return false;
        }

        return true;
    }
}
