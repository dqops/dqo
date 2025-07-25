/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mapping.utils;

import com.dqops.checks.CheckTarget;
import com.dqops.services.check.mapping.models.AllChecksModel;
import com.dqops.services.check.mapping.models.column.AllColumnChecksModel;
import com.dqops.services.check.mapping.models.column.ColumnChecksModel;
import com.dqops.services.check.mapping.models.column.TableColumnChecksModel;
import com.dqops.services.check.mapping.models.table.AllTableChecksModel;
import com.dqops.services.check.mapping.models.table.SchemaTableChecksModel;
import com.dqops.services.check.mapping.models.table.TableChecksModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for manipulating {@link AllChecksModel}.
 */
public class AllChecksModelUtility {
    /**
     * Prunes the provided {@link AllChecksModel} according to the columns mapping.
     * @param tablesToColumnsMapping Table name to column name mapping. Doesn't consider name patterns. If check in <code>sourceModel</code> is table level, only the key-set is considered.
     * @param sourceModel            {@link AllChecksModel} that will be pruned.
     */
    public static void pruneToConcreteTargets(Map<String, List<String>> tablesToColumnsMapping,
                                              AllChecksModel sourceModel) {
        CheckTarget checkTargetFromTable = sourceModel.getTableChecksModel() != null
                && sourceModel.getTableChecksModel().getSchemaTableChecksModels() != null
                && !sourceModel.getTableChecksModel().getSchemaTableChecksModels().isEmpty()
                    ? sourceModel.getTableChecksModel().getCheckTarget()
                : null;

        CheckTarget checkTargetFromColumns = sourceModel.getColumnChecksModel() != null
                && sourceModel.getColumnChecksModel().getTableColumnChecksModels() != null
                && !sourceModel.getColumnChecksModel().getTableColumnChecksModels().isEmpty()
                    ? sourceModel.getColumnChecksModel().getCheckTarget()
                : null;
        CheckTarget checkTarget = checkTargetFromTable != null ? checkTargetFromTable : checkTargetFromColumns;

        if (checkTarget == null) {
            // Empty model.
            return;
        }

        switch (checkTarget) {
            case table:
                Set<String> selectedTableNames = tablesToColumnsMapping.keySet();
                pruneAllTableChecksToConcreteTargets(selectedTableNames, sourceModel.getTableChecksModel());
                if (sourceModel.getTableChecksModel().getSchemaTableChecksModels() == null) {
                    sourceModel.setTableChecksModel(null);
                }
                break;

            case column:
                pruneAllColumnChecksToConcreteTargets(tablesToColumnsMapping, sourceModel.getColumnChecksModel());
                if (sourceModel.getColumnChecksModel().getTableColumnChecksModels() == null) {
                    sourceModel.setColumnChecksModel(null);
                }
                break;
        }
    }

    protected static void pruneAllTableChecksToConcreteTargets(Set<String> tables, AllTableChecksModel sourceModel) {
        List<SchemaTableChecksModel> schemaTableChecksModels = sourceModel.getSchemaTableChecksModels();
        for (SchemaTableChecksModel schemaTableChecksModel: schemaTableChecksModels) {
            List<TableChecksModel> filteredTableChecksModels = schemaTableChecksModel.getTableChecksModels().stream()
                    .filter(tableChecksModel -> tables.contains(tableChecksModel.getTableName()))
                    .collect(Collectors.toList());
            if (filteredTableChecksModels.isEmpty()) {
                filteredTableChecksModels = null;
            }
            schemaTableChecksModel.setTableChecksModels(filteredTableChecksModels);
        }

        List<SchemaTableChecksModel> filteredSchemaTableChecksModels = sourceModel.getSchemaTableChecksModels().stream()
                .filter(schemaTableChecksModel -> schemaTableChecksModel.getTableChecksModels() != null)
                .collect(Collectors.toList());
        if (filteredSchemaTableChecksModels.isEmpty()) {
            filteredSchemaTableChecksModels = null;
        }
        sourceModel.setSchemaTableChecksModels(filteredSchemaTableChecksModels);
    }

    protected static void pruneAllColumnChecksToConcreteTargets(Map<String, List<String>> tablesToColumns, AllColumnChecksModel sourceModel) {
        List<TableColumnChecksModel> filteredTableColumnChecksModels = sourceModel.getTableColumnChecksModels().stream()
                .filter(tableColumnChecksModel -> tablesToColumns.containsKey(tableColumnChecksModel.getSchemaTableName().getTableName()))
                .collect(Collectors.toList());

        for (TableColumnChecksModel tableColumnChecksModel: filteredTableColumnChecksModels) {
            List<String> selectedColumns = tablesToColumns.get(tableColumnChecksModel.getSchemaTableName().getTableName());
            List<ColumnChecksModel> filteredColumnChecksModels = tableColumnChecksModel.getColumnChecksModels().stream()
                    .filter(columnChecksModel -> selectedColumns.contains(columnChecksModel.getColumnName()))
                    .collect(Collectors.toList());
            if (filteredColumnChecksModels.isEmpty()) {
                filteredColumnChecksModels = null;
            }
            tableColumnChecksModel.setColumnChecksModels(filteredColumnChecksModels);
        }

        filteredTableColumnChecksModels = filteredTableColumnChecksModels.stream()
                .filter(tableColumnChecksModel -> tableColumnChecksModel.getColumnChecksModels() != null)
                .collect(Collectors.toList());
        if (filteredTableColumnChecksModels.isEmpty()) {
            filteredTableColumnChecksModels = null;
        }
        sourceModel.setTableColumnChecksModels(filteredTableColumnChecksModels);
    }
}
