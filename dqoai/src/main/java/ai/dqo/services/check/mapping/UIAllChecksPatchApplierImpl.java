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

package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.mapping.models.UICheckContainerModel;
import ai.dqo.services.check.mapping.models.UICheckContainerTypeModel;
import ai.dqo.services.check.mapping.models.column.UIAllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UIColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.UITableColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.UIAllTableChecksModel;
import ai.dqo.services.check.mapping.models.table.UISchemaTableChecksModel;
import ai.dqo.services.check.mapping.models.table.UITableChecksModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UIAllChecksPatchApplierImpl implements UIAllChecksPatchApplier {

    private UiToSpecCheckMappingService uiToSpecCheckMappingService;

    @Autowired
    public UIAllChecksPatchApplierImpl(UiToSpecCheckMappingService uiToSpecCheckMappingService) {
        this.uiToSpecCheckMappingService = uiToSpecCheckMappingService;
    }

    /**
     * Apply a patch to the check configuration on connection level.
     *
     * @param allChecksPatch    Patch with the changes to be applied. Incomplete, targets only that which is to be updated.
     * @param connectionWrapper Connection wrapper to the connection this patch refers to.
     */
    @Override
    public void applyPatchOnConnection(UIAllChecksModel allChecksPatch, ConnectionWrapper connectionWrapper) {
        UIAllColumnChecksModel allColumnChecksPatch = allChecksPatch.getColumnChecksModel();
        if (allColumnChecksPatch != null) {
            this.applyColumnPatchOnConnection(allColumnChecksPatch, connectionWrapper);
        }

        UIAllTableChecksModel allTableChecksPatch = allChecksPatch.getTableChecksModel();
        if (allTableChecksPatch != null) {
            this.applyTablePatchOnConnection(allTableChecksPatch, connectionWrapper);
        }
    }


    protected void applyColumnPatchOnConnection(UIAllColumnChecksModel allColumnChecksPatch, ConnectionWrapper connectionWrapper) {
        TableList tables = connectionWrapper.getTables();

        List<UITableColumnChecksModel> tableColumnChecksPatches = allColumnChecksPatch.getUiTableColumnChecksModels();
        for (UITableColumnChecksModel tableColumnChecksPatch: tableColumnChecksPatches) {
            PhysicalTableName physicalTableName = tableColumnChecksPatch.getSchemaTableName();
            TableWrapper table = tables.getByObjectName(physicalTableName, true);
            if (table != null) {
                this.applyColumnPatchOnTable(tableColumnChecksPatch, table);
            }
        }
    }

    protected void applyColumnPatchOnTable(UITableColumnChecksModel tableColumnChecksPatch, TableWrapper tableWrapper) {
        ColumnSpecMap columns = tableWrapper.getSpec().getColumns();

        List<UIColumnChecksModel> columnChecksPatches = tableColumnChecksPatch.getUiColumnChecksModels();
        for (UIColumnChecksModel columnChecksPatch: columnChecksPatches) {
            String columnName = columnChecksPatch.getColumnName();
            ColumnSpec column = columns.get(columnName);
            if (column != null) {
                this.applyColumnPatchOnColumn(columnChecksPatch, column);
            }
        }
    }

    protected void applyColumnPatchOnColumn(UIColumnChecksModel columnChecksPatch, ColumnSpec columnSpec) {
        Map<UICheckContainerTypeModel, UICheckContainerModel> checkContainerPatches = columnChecksPatch.getCheckContainers();

        for (Map.Entry<UICheckContainerTypeModel, UICheckContainerModel> checkContainerPatchPair: checkContainerPatches.entrySet()) {
            UICheckContainerTypeModel checkTypeTarget = checkContainerPatchPair.getKey();
            UICheckContainerModel checkPatch = checkContainerPatchPair.getValue();

            AbstractRootChecksContainerSpec toPatch = columnSpec.getColumnCheckRootContainer(
                    checkTypeTarget.getCheckType(), checkTypeTarget.getCheckTimeScale(), true);
            this.uiToSpecCheckMappingService.updateCheckContainerSpec(checkPatch, toPatch);
        }
    }


    protected void applyTablePatchOnConnection(UIAllTableChecksModel allTableChecksPatch, ConnectionWrapper connectionWrapper) {
        List<UISchemaTableChecksModel> schemaTableChecksPatches = allTableChecksPatch.getUiSchemaTableChecksModels();
        for (UISchemaTableChecksModel schemaTableChecksPatch: schemaTableChecksPatches) {
            String schemaName = schemaTableChecksPatch.getSchemaName();
            this.applyTablePatchOnSchema(schemaTableChecksPatch, connectionWrapper, schemaName);
        }
    }

    protected void applyTablePatchOnSchema(UISchemaTableChecksModel schemaTableChecksPatch, ConnectionWrapper connectionWrapper, String schemaName) {
        TableList tables = connectionWrapper.getTables();

        List<UITableChecksModel> tableChecksPatches = schemaTableChecksPatch.getUiTableChecksModels();
        for (UITableChecksModel tableChecksPatch: tableChecksPatches) {
            String tableName = tableChecksPatch.getTableName();
            PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper table = tables.getByObjectName(schemaTableName, true);

            if (table != null) {
                this.applyTablePatchOnTable(tableChecksPatch, table);
            }
        }
    }

    protected void applyTablePatchOnTable(UITableChecksModel tableChecksPatch, TableWrapper tableWrapper) {
        Map<UICheckContainerTypeModel, UICheckContainerModel> checkContainerPatches = tableChecksPatch.getCheckContainers();

        for (Map.Entry<UICheckContainerTypeModel, UICheckContainerModel> checkContainerPatchPair: checkContainerPatches.entrySet()) {
            UICheckContainerTypeModel checkTypeTarget = checkContainerPatchPair.getKey();
            UICheckContainerModel checkPatch = checkContainerPatchPair.getValue();

            AbstractRootChecksContainerSpec toPatch = tableWrapper.getSpec().getTableCheckRootContainer(checkTypeTarget.getCheckType(), checkTypeTarget.getCheckTimeScale());
            this.uiToSpecCheckMappingService.updateCheckContainerSpec(checkPatch, toPatch);
        }
    }
}
