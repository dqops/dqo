/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.policies.table;

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
