/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.services.check;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.ConnectionSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.mapping.models.column.AllColumnChecksModel;
import com.dqops.services.check.mapping.models.column.ColumnChecksModel;
import com.dqops.services.check.mapping.models.column.TableColumnChecksModel;
import com.dqops.services.check.mapping.models.table.AllTableChecksModel;
import com.dqops.services.check.mapping.models.table.SchemaTableChecksModel;
import com.dqops.services.check.mapping.models.table.TableChecksModel;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.dqops.utils.reflection.FieldInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for creating flat collections of self-contained check configuration models.
 */
@Service
public class CheckFlatConfigurationFactoryImpl implements CheckFlatConfigurationFactory {
    private AllChecksModelFactory allChecksModelFactory;

    @Autowired
    public CheckFlatConfigurationFactoryImpl(AllChecksModelFactory allChecksModelFactory) {
        this.allChecksModelFactory = allChecksModelFactory;
    }

    /**
     * Flattens information contained in <code>allChecksModel</code>.
     *
     * @param allChecksModel Partial model of checks on a single connection.
     * @return List of self-contained check configuration models.
     */
    @Override
    public List<CheckConfigurationModel> fromAllChecksModel(AllChecksModel allChecksModel) {
        String connectionName = allChecksModel.getConnectionName();
        List<CheckConfigurationModel> resultList = new ArrayList<>();

        AllTableChecksModel allTableChecksModel = allChecksModel.getTableChecksModel();
        if (allTableChecksModel != null) {
            for (SchemaTableChecksModel schemaTableChecksModel : allTableChecksModel.getSchemaTableChecksModels()) {
                String schemaName = schemaTableChecksModel.getSchemaName();
                for (TableChecksModel tableChecksModel : schemaTableChecksModel.getTableChecksModels()) {
                    String tableName = tableChecksModel.getTableName();
                    String tableLevelFilter = tableChecksModel.getTableLevelFilter();
                    for (Map.Entry<CheckContainerTypeModel, CheckContainerModel> checkContainerModelEntry:
                            tableChecksModel.getCheckContainers().entrySet()) {
                        CheckContainerTypeModel checkContainerTypeModel = checkContainerModelEntry.getKey();
                        CheckContainerModel checkContainerModel = checkContainerModelEntry.getValue();

                        resultList.addAll(this.createCheckConfigurationModelsForContainer(
                                connectionName,
                                schemaName,
                                tableName,
                                tableLevelFilter,
                                null,
                                checkContainerTypeModel,
                                checkContainerModel));
                    }
                }
            }
        }

        AllColumnChecksModel allColumnChecksModel = allChecksModel.getColumnChecksModel();
        if (allColumnChecksModel != null) {
            for (TableColumnChecksModel tableColumnChecksModel : allColumnChecksModel.getTableColumnChecksModels()) {
                PhysicalTableName physicalTableName = tableColumnChecksModel.getSchemaTableName();
                String tableLevelFilter = tableColumnChecksModel.getTableLevelFilter();
                for (ColumnChecksModel columnChecksModel : tableColumnChecksModel.getColumnChecksModels()) {
                    String columnName = columnChecksModel.getColumnName();
                    for (Map.Entry<CheckContainerTypeModel, CheckContainerModel> checkContainerModelEntry:
                            columnChecksModel.getCheckContainers().entrySet()) {
                        CheckContainerTypeModel checkContainerTypeModel = checkContainerModelEntry.getKey();
                        CheckContainerModel checkContainerModel = checkContainerModelEntry.getValue();

                        resultList.addAll(this.createCheckConfigurationModelsForContainer(
                                connectionName,
                                physicalTableName.getSchemaName(),
                                physicalTableName.getTableName(),
                                tableLevelFilter,
                                columnName,
                                checkContainerTypeModel,
                                checkContainerModel));
                    }
                }
            }
        }

        return resultList;
    }

    /**
     * Gets a collection of check configuration models that fit the provided filters.
     *
     * @param checkSearchFilters Check search filters.
     * @return List of self-contained check configuration models that fit the filters.
     */
    @Override
    public List<CheckConfigurationModel> fromCheckSearchFilters(CheckSearchFilters checkSearchFilters) {
        return this.allChecksModelFactory.fromCheckSearchFilters(checkSearchFilters).stream()
                .flatMap(allChecksModel -> this.fromAllChecksModel(allChecksModel).stream())
                .collect(Collectors.toList());
    }

    /**
     * Create a configuration model for a single check container.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @param tableFilter Table-level SQL WHERE filter.
     * @param columnName Column name.
     * @param checkContainerTypeModel Check type and check timescale.
     * @param checkContainerModel Check container model.
     * @return List of check configuration models for this check container.
     */
    protected List<CheckConfigurationModel> createCheckConfigurationModelsForContainer(
            String connectionName,
            String schemaName,
            String tableName,
            String tableFilter,
            String columnName,
            CheckContainerTypeModel checkContainerTypeModel,
            CheckContainerModel checkContainerModel) {
        List<CheckConfigurationModel> containerResults = new ArrayList<>();
        for (QualityCategoryModel qualityCategoryModel : checkContainerModel.getCategories()) {
            if (qualityCategoryModel == null) {
                continue;
            }
            String categoryName = qualityCategoryModel.getCategory();
            containerResults.addAll(qualityCategoryModel.getChecks().stream()
                    .map(checkModel -> {
                        CheckConfigurationModel model = new CheckConfigurationModel();
                        model.setConnectionName(connectionName);
                        model.setSchemaName(schemaName);
                        model.setTableName(tableName);
                        model.setTableLevelFilter(tableFilter);
                        model.setColumnName(columnName);
                        model.setCategoryName(categoryName);
                        model.setCheckType(checkContainerTypeModel.getCheckType());
                        model.setCheckTimeScale(checkContainerTypeModel.getCheckTimeScale());
                        model.setCheckTarget(columnName == null ? CheckTarget.table : CheckTarget.column);

                        model.setCheckName(checkModel.getCheckName());
                        model.setConfigured(checkModel.isConfigured());
                        model.setDisabled(checkModel.isDisabled());
                        model.setCheckSpec(checkModel.getCheckSpec());
                        model.setSensorLevelFilter(checkModel.getFilter());
                        model.setSensorParametersSpec(checkModel.getSensorParametersSpec());
                        model.setSensorParameters(checkModel.getSensorParameters());

                        RuleThresholdsModel ruleThresholdsModel = checkModel.getRule();
                        if (ruleThresholdsModel != null) {
                            model.setWarning(ruleThresholdsModel.getWarning());
                            model.setError(ruleThresholdsModel.getError());
                            model.setFatal(ruleThresholdsModel.getFatal());
                        }

                        return model;
                    }).collect(Collectors.toList()));
        }
        return containerResults;
    }
}
