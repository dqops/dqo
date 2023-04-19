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

package ai.dqo.services.metadata;

import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.check.CheckTemplate;
import ai.dqo.services.check.mapping.UIAllChecksModelFactory;
import ai.dqo.services.check.mapping.models.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SchemaServiceImpl implements SchemaService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final UIAllChecksModelFactory uiAllChecksModelFactory;

    @Autowired
    public SchemaServiceImpl(UserHomeContextFactory userHomeContextFactory,
                             UIAllChecksModelFactory uiAllChecksModelFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.uiAllChecksModelFactory = uiAllChecksModelFactory;
    }

    /**
     * Finds tables in a schema located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @return List of table wrappers in the requested schema. Null if schema doesn't exist.
     */
    @Override
    public List<TableWrapper> getSchemaTables(UserHome userHome, String connectionName, String schemaName) {
        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        List<TableWrapper> tableWrappers = connectionWrapper.getTables().toList().stream()
                .filter(table -> table.getPhysicalTableName().getSchemaName().equals(schemaName))
                .collect(Collectors.toList());
        if (tableWrappers.isEmpty()) {
            return null;
        }
        return tableWrappers;
    }

    /**
     * Retrieves a list of check templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param checkType      Check type.
     * @param checkTimeScale (Optional) Check time-scale.
     * @param checkTarget    (Optional) Check target.
     * @param checkCategory  (Optional) Check category.
     * @param checkName      (Optional) Check name.
     * @return List of check templates in the requested schema, matching the optional filters. Null if schema doesn't exist.
     */
    @Override
    public List<CheckTemplate> getCheckTemplates(String connectionName,
                                                 String schemaName,
                                                 CheckType checkType,
                                                 CheckTimeScale checkTimeScale,
                                                 CheckTarget checkTarget,
                                                 String checkCategory,
                                                 String checkName) {
        if (Strings.isNullOrEmpty(connectionName)
                || Strings.isNullOrEmpty(schemaName)
                || checkType == null) {
            // Connection name, schema name and check type have to be provided.
            return null;
        }
        if ((checkType == CheckType.PARTITIONED || checkType == CheckType.RECURRING) && checkTimeScale == null) {
            // Time scale has to be provided for partitioned and recurring checks.
            return null;
        }

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnectionName(connectionName);
        checkSearchFilters.setSchemaTableName(schemaName + ".*");
        checkSearchFilters.setCheckType(checkType);
        checkSearchFilters.setTimeScale(checkTimeScale);
        // Filtering by checkTarget has to be done apart from these filters.
        checkSearchFilters.setCheckCategory(checkCategory);
        checkSearchFilters.setCheckName(checkName);

        List<UIAllChecksModel> uiAllChecksModels = this.uiAllChecksModelFactory.fromCheckSearchFilters(checkSearchFilters);

        Stream<UICheckContainerModel> columnCheckContainers = uiAllChecksModels.stream()
                .map(UIAllChecksModel::getColumnChecksModel)
                .flatMap(model -> model.getUiTableColumnChecksModels().stream())
                .flatMap(model -> model.getUiColumnChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream());
        Stream<UICheckContainerModel> tableCheckContainers = uiAllChecksModels.stream()
                .map(UIAllChecksModel::getTableChecksModel)
                .flatMap(model -> model.getUiSchemaTableChecksModels().stream())
                .flatMap(model -> model.getUiTableChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream());

        UICheckContainerTypeModel uiCheckContainerTypeModel = new UICheckContainerTypeModel(checkType, checkTimeScale);

        Stream<CheckTemplate> checkTemplates;
        if (checkTarget == null) {
            checkTemplates = Stream.concat(
                    this.getCheckTemplatesFromCheckContainers(columnCheckContainers, uiCheckContainerTypeModel, CheckTarget.column),
                    this.getCheckTemplatesFromCheckContainers(tableCheckContainers, uiCheckContainerTypeModel, CheckTarget.table));
        } else {
            switch (checkTarget) {
                case column:
                    checkTemplates = this.getCheckTemplatesFromCheckContainers(columnCheckContainers, uiCheckContainerTypeModel, CheckTarget.column);
                    break;
                case table:
                    checkTemplates = this.getCheckTemplatesFromCheckContainers(tableCheckContainers, uiCheckContainerTypeModel, CheckTarget.table);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported check target: " + checkTarget.name());
            }
        }

        return checkTemplates.collect(Collectors.toList());
    }

    /**
     * Generates a distinct {@link CheckTemplate} stream from {@link UICheckContainerModel} stream,
     * provided parameters specifying the source of the base stream.
     * @param checkContainers    Base check containers stream.
     * @param checkTarget        Check target specifying the source of the base stream.
     * @param checkContainerType Check container type specifying the source of the base stream.
     * @return Stream of check templates modelling the checks that are present in the base stream.
     */
    protected Stream<CheckTemplate> getCheckTemplatesFromCheckContainers(Stream<UICheckContainerModel> checkContainers,
                                                                         UICheckContainerTypeModel checkContainerType,
                                                                         CheckTarget checkTarget) {
        return checkContainers
                .flatMap(model -> model.getCategories().stream())
                .map(categoryModel -> {
                    Map<String, UICheckModel> checkNameToExampleCheck = new HashMap<>();
                    for (UICheckModel checkModel: categoryModel.getChecks()) {
                        if (!checkNameToExampleCheck.containsKey(checkModel.getCheckName())) {
                            checkNameToExampleCheck.put(checkModel.getCheckName(), checkModel);
                        }
                    }

                    return checkNameToExampleCheck.values().stream()
                            .map(uiCheckModel -> CheckTemplate.fromUiCheckModel(
                                    uiCheckModel, categoryModel.getCategory(), checkContainerType, checkTarget)
                            );
                })
                .reduce(Stream.empty(), Stream::concat);
    }
}
