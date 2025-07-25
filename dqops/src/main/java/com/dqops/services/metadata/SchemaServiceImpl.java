/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.metadata;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.services.check.CheckFlatConfigurationFactory;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.models.*;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SchemaServiceImpl implements SchemaService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final AllChecksModelFactory allChecksModelFactory;
    private final CheckFlatConfigurationFactory checkFlatConfigurationFactory;

    @Autowired
    public SchemaServiceImpl(UserHomeContextFactory userHomeContextFactory,
                             AllChecksModelFactory allChecksModelFactory,
                             CheckFlatConfigurationFactory checkFlatConfigurationFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.allChecksModelFactory = allChecksModelFactory;
        this.checkFlatConfigurationFactory = checkFlatConfigurationFactory;
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
     * @param principal      User principal.
     * @return List of check templates in the requested schema, matching the optional filters. Null if schema doesn't exist.
     */
    @Override
    public List<CheckTemplate> getCheckTemplates(String connectionName,
                                                 String schemaName,
                                                 CheckType checkType,
                                                 CheckTimeScale checkTimeScale,
                                                 CheckTarget checkTarget,
                                                 String checkCategory,
                                                 String checkName,
                                                 DqoUserPrincipal principal) {
        if (Strings.isNullOrEmpty(connectionName) || checkType == null) {
            // Connection name, schema name and check type have to be provided.
            return null;
        }
        if ((checkType == CheckType.partitioned || checkType == CheckType.monitoring) && checkTimeScale == null) {
            // Time scale has to be provided for partitioned and monitoring checks.
            return null;
        }

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnection(connectionName);
        checkSearchFilters.setCheckType(checkType);
        checkSearchFilters.setTimeScale(checkTimeScale);
        // Filtering by checkTarget has to be done apart from these filters.
        checkSearchFilters.setCheckCategory(checkCategory);
        checkSearchFilters.setCheckName(checkName);

        AllChecksModel allChecksModel = this.allChecksModelFactory.createTemplatedCheckModelsAvailableOnConnection(
                connectionName, schemaName, checkSearchFilters, principal);

        List<CheckContainerModel> columnCheckContainers = allChecksModel.getColumnChecksModel()
                .getTableColumnChecksModels().stream()
                .flatMap(model -> model.getColumnChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream())
                .collect(Collectors.toList());
        List<CheckContainerModel> tableCheckContainers = allChecksModel.getTableChecksModel()
                .getSchemaTableChecksModels().stream()
                .flatMap(model -> model.getTableChecksModels().stream())
                .flatMap(model -> model.getCheckContainers().values().stream())
                .collect(Collectors.toList());

        CheckContainerTypeModel checkContainerTypeModel = new CheckContainerTypeModel(checkType, checkTimeScale);

        List<CheckTemplate> checkTemplates;
        if (checkTarget == null) {
            List<CheckTemplate> columnTemplates = this.getCheckTemplatesFromCheckContainers(columnCheckContainers, checkContainerTypeModel, CheckTarget.column);
            List<CheckTemplate> tableTemplates = this.getCheckTemplatesFromCheckContainers(tableCheckContainers, checkContainerTypeModel, CheckTarget.table);
            checkTemplates = Stream.concat(
                    columnTemplates.stream(),
                    tableTemplates.stream())
                    .collect(Collectors.toList());
        } else {
            switch (checkTarget) {
                case column:
                    checkTemplates = this.getCheckTemplatesFromCheckContainers(columnCheckContainers, checkContainerTypeModel, CheckTarget.column);
                    break;
                case table:
                    checkTemplates = this.getCheckTemplatesFromCheckContainers(tableCheckContainers, checkContainerTypeModel, CheckTarget.table);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported check target: " + checkTarget.name());
            }
        }

        return checkTemplates;
    }

    /**
     * Retrieves a UI friendly data quality profiling check configuration list on a requested schema.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param checkContainerTypeModel Check container type model.
     * @param tableNamePattern  (Optional) Table search pattern filter.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkTarget       (Optional) Filter on check target.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @param limit             Result limit.
     * @param principal         User principal.
     * @return UI friendly data quality profiling check configuration list on a requested schema.
     */
    @Override
    public List<CheckConfigurationModel> getCheckConfigurationsOnSchema(String connectionName,
                                                                        String schemaName,
                                                                        CheckContainerTypeModel checkContainerTypeModel,
                                                                        String tableNamePattern,
                                                                        String columnNamePattern,
                                                                        String columnDataType,
                                                                        CheckTarget checkTarget,
                                                                        String checkCategory,
                                                                        String checkName,
                                                                        Boolean checkEnabled,
                                                                        Boolean checkConfigured,
                                                                        int limit,
                                                                        DqoUserPrincipal principal) {
        String tableSearchPattern = PhysicalTableName.fromSchemaTableFilter(
                new PhysicalTableName(schemaName, Optional.ofNullable(tableNamePattern).orElse(""))
                        .toTableSearchFilter()
        ).toTableSearchFilter();

        CheckSearchFilters filters = new CheckSearchFilters();
        filters.setCheckType(checkContainerTypeModel.getCheckType());
        filters.setTimeScale(checkContainerTypeModel.getCheckTimeScale());
        filters.setConnection(connectionName);
        filters.setFullTableName(tableSearchPattern);
        filters.setColumn(columnNamePattern);
        filters.setColumnDataType(columnDataType);
        filters.setCheckTarget(checkTarget);
        filters.setCheckCategory(checkCategory);
        filters.setCheckName(checkName);
        filters.setEnabled(checkEnabled);
        filters.setCheckConfigured(checkConfigured);

        return this.checkFlatConfigurationFactory.findAllCheckConfigurations(filters, principal, limit);
    }

    /**
     * Generates a distinct {@link CheckTemplate} stream from {@link CheckContainerModel} stream,
     * provided parameters specifying the source of the base stream.
     * @param checkContainers    Base check containers stream.
     * @param checkTarget        Check target specifying the source of the base stream.
     * @param checkContainerType Check container type specifying the source of the base stream.
     * @return Stream of check templates modelling the checks that are present in the base stream.
     */
    protected List<CheckTemplate> getCheckTemplatesFromCheckContainers(Collection<CheckContainerModel> checkContainers,
                                                                         CheckContainerTypeModel checkContainerType,
                                                                         CheckTarget checkTarget) {
        List<QualityCategoryModel> categoryContainers = checkContainers
                .stream()
                .flatMap(model -> model.getCategories().stream())
                .collect(Collectors.toList());

        List<CheckTemplate> allTemplates = categoryContainers
                .stream()
                .map(categoryModel -> {
                    Map<String, CheckModel> checkNameToExampleCheck = new LinkedHashMap<>();
                    for (CheckModel checkModel : categoryModel.getChecks()) {
                        if (!checkNameToExampleCheck.containsKey(checkModel.getCheckName())) {
                            checkNameToExampleCheck.put(checkModel.getCheckName(), checkModel);
                        }
                    }

                    return checkNameToExampleCheck.values().stream()
                            .map(uiCheckModel -> CheckTemplate.fromUiCheckModel(
                                    uiCheckModel, categoryModel.getCategory(), checkContainerType, checkTarget)
                            );
                })
                .reduce(Stream.empty(), Stream::concat)
                .collect(Collectors.toList());

        return allTemplates;
    }
}
