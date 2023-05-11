/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.services.check.mapping.utils;

import ai.dqo.checks.CheckTarget;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.mapping.models.column.UIAllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UIColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UITableColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.UIAllTableChecksModel;
import ai.dqo.services.check.mapping.models.table.UISchemaTableChecksModel;
import ai.dqo.services.check.mapping.models.table.UITableChecksModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for manipulating {@link UIAllChecksModel}.
 */
public class UIAllChecksModelUtility {
    /**
     * Prunes the provided {@link UIAllChecksModel} according to the columns mapping.
     * @param tablesToColumnsMapping Table name to column name mapping. Doesn't consider name patterns. If check in <code>sourceModel</code> is table level, only the key-set is considered.
     * @param sourceModel            {@link UIAllChecksModel} that will be pruned.
     */
    public static void pruneToConcreteTargets(Map<String, List<String>> tablesToColumnsMapping,
                                              UIAllChecksModel sourceModel) {
        CheckTarget checkTargetFromTable = sourceModel.getTableChecksModel() != null
                && sourceModel.getTableChecksModel().getUiSchemaTableChecksModels() != null
                && !sourceModel.getTableChecksModel().getUiSchemaTableChecksModels().isEmpty()
                    ? sourceModel.getTableChecksModel().getCheckTarget()
                : null;

        CheckTarget checkTargetFromColumns = sourceModel.getColumnChecksModel() != null
                && sourceModel.getColumnChecksModel().getUiTableColumnChecksModels() != null
                && !sourceModel.getColumnChecksModel().getUiTableColumnChecksModels().isEmpty()
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
                if (sourceModel.getTableChecksModel().getUiSchemaTableChecksModels() == null) {
                    sourceModel.setTableChecksModel(null);
                }
                break;

            case column:
                pruneAllColumnChecksToConcreteTargets(tablesToColumnsMapping, sourceModel.getColumnChecksModel());
                if (sourceModel.getColumnChecksModel().getUiTableColumnChecksModels() == null) {
                    sourceModel.setColumnChecksModel(null);
                }
                break;
        }
    }

    protected static void pruneAllTableChecksToConcreteTargets(Set<String> tables, UIAllTableChecksModel sourceModel) {
        List<UISchemaTableChecksModel> schemaTableChecksModels = sourceModel.getUiSchemaTableChecksModels();
        for (UISchemaTableChecksModel schemaTableChecksModel: schemaTableChecksModels) {
            List<UITableChecksModel> filteredTableChecksModels = schemaTableChecksModel.getUiTableChecksModels().stream()
                    .filter(tableChecksModel -> tables.contains(tableChecksModel.getTableName()))
                    .collect(Collectors.toList());
            if (filteredTableChecksModels.isEmpty()) {
                filteredTableChecksModels = null;
            }
            schemaTableChecksModel.setUiTableChecksModels(filteredTableChecksModels);
        }

        List<UISchemaTableChecksModel> filteredSchemaTableChecksModels = sourceModel.getUiSchemaTableChecksModels().stream()
                .filter(schemaTableChecksModel -> schemaTableChecksModel.getUiTableChecksModels() != null)
                .collect(Collectors.toList());
        if (filteredSchemaTableChecksModels.isEmpty()) {
            filteredSchemaTableChecksModels = null;
        }
        sourceModel.setUiSchemaTableChecksModels(filteredSchemaTableChecksModels);
    }

    protected static void pruneAllColumnChecksToConcreteTargets(Map<String, List<String>> tablesToColumns, UIAllColumnChecksModel sourceModel) {
        List<UITableColumnChecksModel> filteredTableColumnChecksModels = sourceModel.getUiTableColumnChecksModels().stream()
                .filter(tableColumnChecksModel -> tablesToColumns.containsKey(tableColumnChecksModel.getSchemaTableName().getTableName()))
                .collect(Collectors.toList());

        for (UITableColumnChecksModel tableColumnChecksModel: filteredTableColumnChecksModels) {
            List<String> selectedColumns = tablesToColumns.get(tableColumnChecksModel.getSchemaTableName().getTableName());
            List<UIColumnChecksModel> filteredColumnChecksModels = tableColumnChecksModel.getUiColumnChecksModels().stream()
                    .filter(columnChecksModel -> selectedColumns.contains(columnChecksModel.getColumnName()))
                    .collect(Collectors.toList());
            if (filteredColumnChecksModels.isEmpty()) {
                filteredColumnChecksModels = null;
            }
            tableColumnChecksModel.setUiColumnChecksModels(filteredColumnChecksModels);
        }

        filteredTableColumnChecksModels = filteredTableColumnChecksModels.stream()
                .filter(tableColumnChecksModel -> tableColumnChecksModel.getUiColumnChecksModels() != null)
                .collect(Collectors.toList());
        if (filteredTableColumnChecksModels.isEmpty()) {
            filteredTableColumnChecksModels = null;
        }
        sourceModel.setUiTableColumnChecksModels(filteredTableColumnChecksModels);
    }
}
