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
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.sources.TableSpec;
import com.google.common.base.Strings;

/**
 * Target table filter for default checks that uses parsed patterns.
 */
public class TargetTablePatternFilter {
    private SearchPattern[] connectionPattern;
    private SearchPattern[] schemaPattern;
    private SearchPattern[] tablePattern;
    private SearchPattern[] stagePattern;
    private Integer tablePriority;
    protected SearchPattern[] labelPattern;

    /**
     * Creates a table pattern search object given a target table pattern specification. Creates search patterns for filled fields.
     * @param tablePatternSpec Target table search pattern.
     */
    public TargetTablePatternFilter(TargetTablePatternSpec tablePatternSpec) {
        if (!Strings.isNullOrEmpty(tablePatternSpec.getConnection())) {
            this.connectionPattern = SearchPattern.createForCommaSeparatedPatterns(false, tablePatternSpec.getConnection());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getSchema())) {
            this.schemaPattern = SearchPattern.createForCommaSeparatedPatterns(false, tablePatternSpec.getSchema());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getTable())) {
            this.tablePattern = SearchPattern.createForCommaSeparatedPatterns(false, tablePatternSpec.getTable());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getStage())) {
            this.stagePattern = SearchPattern.createForCommaSeparatedPatterns(false, tablePatternSpec.getStage());
        }

        if (!Strings.isNullOrEmpty(tablePatternSpec.getLabel())) {
            this.labelPattern = SearchPattern.createForCommaSeparatedPatterns(false, tablePatternSpec.getLabel());
        }

        this.tablePriority = tablePatternSpec.getTablePriority();
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
            if (!SearchPattern.matchAny(this.connectionPattern, connectionSpec.getConnectionName())) {
                return false;
            }
        }

        if (this.schemaPattern != null) {
            if (!SearchPattern.matchAny(this.schemaPattern, tableSpec.getPhysicalTableName().getSchemaName())) {
                return false;
            }
        }

        if (this.tablePattern != null) {
            if (!SearchPattern.matchAny(this.tablePattern, tableSpec.getPhysicalTableName().getTableName())) {
                return false;
            }
        }

        if (this.stagePattern != null) {
            if (!SearchPattern.matchAny(this.stagePattern, tableSpec.getStage())) {
                return false;
            }
        }

        if (this.tablePriority != null) {
            if (tableSpec.getPriority() == null || tableSpec.getPriority() > tablePriority) {
                return false;
            }
        }

        if (matchLabels && this.labelPattern != null) {
            LabelSetSpec connectionLabels = connectionSpec.getLabels();
            if (connectionLabels != null && !connectionLabels.isEmpty()) {
                for (String label : connectionLabels) {
                    if (SearchPattern.matchAny(this.labelPattern, label)) {
                        return true;
                    }
                }
            }

            LabelSetSpec tableLabels = tableSpec.getLabels();
            if (tableLabels != null && !tableLabels.isEmpty()) {
                for (String label : tableLabels) {
                    if (SearchPattern.matchAny(this.labelPattern, label)) {
                        return true;
                    }
                }
            }

            return false;
        }

        return true;
    }
}
