/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.services.check.mapping;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.services.check.mapping.models.AllChecksModel;
import ai.dqo.services.check.mapping.models.CheckContainerModel;
import ai.dqo.services.check.mapping.models.CheckContainerTypeModel;
import ai.dqo.services.check.mapping.models.column.AllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.ColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.TableColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.AllTableChecksModel;
import ai.dqo.services.check.mapping.models.table.SchemaTableChecksModel;
import ai.dqo.services.check.mapping.models.table.TableChecksModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AllChecksPatchApplierImpl implements AllChecksPatchApplier {

    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;

    @Autowired
    public AllChecksPatchApplierImpl(ModelToSpecCheckMappingService modelToSpecCheckMappingService) {
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
    }

    /**
     * Apply a patch to the check configuration on connection level.
     *
     * @param allChecksPatch    Patch with the changes to be applied. Incomplete, targets only that which is to be updated.
     * @param connectionWrapper Connection wrapper to the connection this patch refers to.
     */
    @Override
    public void applyPatchOnConnection(AllChecksModel allChecksPatch, ConnectionWrapper connectionWrapper) {
        AllColumnChecksModel allColumnChecksPatch = allChecksPatch.getColumnChecksModel();
        if (allColumnChecksPatch != null) {
            this.applyColumnPatchOnConnection(allColumnChecksPatch, connectionWrapper);
        }

        AllTableChecksModel allTableChecksPatch = allChecksPatch.getTableChecksModel();
        if (allTableChecksPatch != null) {
            this.applyTablePatchOnConnection(allTableChecksPatch, connectionWrapper);
        }
    }


    protected void applyColumnPatchOnConnection(AllColumnChecksModel allColumnChecksPatch, ConnectionWrapper connectionWrapper) {
        TableList tables = connectionWrapper.getTables();

        List<TableColumnChecksModel> tableColumnChecksPatches = allColumnChecksPatch.getTableColumnChecksModels();
        for (TableColumnChecksModel tableColumnChecksPatch: tableColumnChecksPatches) {
            PhysicalTableName physicalTableName = tableColumnChecksPatch.getSchemaTableName();
            TableWrapper table = tables.getByObjectName(physicalTableName, true);
            if (table != null) {
                this.applyColumnPatchOnTable(tableColumnChecksPatch, table);
            }
        }
    }

    protected void applyColumnPatchOnTable(TableColumnChecksModel tableColumnChecksPatch, TableWrapper tableWrapper) {
        ColumnSpecMap columns = tableWrapper.getSpec().getColumns();

        List<ColumnChecksModel> columnChecksPatches = tableColumnChecksPatch.getColumnChecksModels();
        for (ColumnChecksModel columnChecksPatch: columnChecksPatches) {
            String columnName = columnChecksPatch.getColumnName();
            ColumnSpec column = columns.get(columnName);
            if (column != null) {
                this.applyColumnPatchOnColumn(columnChecksPatch, column);
            }
        }
    }

    protected void applyColumnPatchOnColumn(ColumnChecksModel columnChecksPatch, ColumnSpec columnSpec) {
        Map<CheckContainerTypeModel, CheckContainerModel> checkContainerPatches = columnChecksPatch.getCheckContainers();

        for (Map.Entry<CheckContainerTypeModel, CheckContainerModel> checkContainerPatchPair: checkContainerPatches.entrySet()) {
            CheckContainerTypeModel checkTypeTarget = checkContainerPatchPair.getKey();
            CheckContainerModel checkPatch = checkContainerPatchPair.getValue();

            AbstractRootChecksContainerSpec toPatch = columnSpec.getColumnCheckRootContainer(
                    checkTypeTarget.getCheckType(), checkTypeTarget.getCheckTimeScale(), true);
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkPatch, toPatch);
        }
    }


    protected void applyTablePatchOnConnection(AllTableChecksModel allTableChecksPatch, ConnectionWrapper connectionWrapper) {
        List<SchemaTableChecksModel> schemaTableChecksPatches = allTableChecksPatch.getSchemaTableChecksModels();
        for (SchemaTableChecksModel schemaTableChecksPatch: schemaTableChecksPatches) {
            String schemaName = schemaTableChecksPatch.getSchemaName();
            this.applyTablePatchOnSchema(schemaTableChecksPatch, connectionWrapper, schemaName);
        }
    }

    protected void applyTablePatchOnSchema(SchemaTableChecksModel schemaTableChecksPatch, ConnectionWrapper connectionWrapper, String schemaName) {
        TableList tables = connectionWrapper.getTables();

        List<TableChecksModel> tableChecksPatches = schemaTableChecksPatch.getTableChecksModels();
        for (TableChecksModel tableChecksPatch: tableChecksPatches) {
            String tableName = tableChecksPatch.getTableName();
            PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper table = tables.getByObjectName(schemaTableName, true);

            if (table != null) {
                this.applyTablePatchOnTable(tableChecksPatch, table);
            }
        }
    }

    protected void applyTablePatchOnTable(TableChecksModel tableChecksPatch, TableWrapper tableWrapper) {
        Map<CheckContainerTypeModel, CheckContainerModel> checkContainerPatches = tableChecksPatch.getCheckContainers();

        for (Map.Entry<CheckContainerTypeModel, CheckContainerModel> checkContainerPatchPair: checkContainerPatches.entrySet()) {
            CheckContainerTypeModel checkTypeTarget = checkContainerPatchPair.getKey();
            CheckContainerModel checkPatch = checkContainerPatchPair.getValue();

            AbstractRootChecksContainerSpec toPatch = tableWrapper.getSpec().getTableCheckRootContainer(
                    checkTypeTarget.getCheckType(), checkTypeTarget.getCheckTimeScale(), true);
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkPatch, toPatch);
        }
    }
}
