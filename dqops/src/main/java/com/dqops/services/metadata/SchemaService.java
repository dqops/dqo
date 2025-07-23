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
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.models.CheckConfigurationModel;

import java.util.List;

/**
 * Service that performs schema operations.
 */
public interface SchemaService {
    /**
     * Finds tables in a schema located in provided user home.
     * @param userHome       User home.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @return List of table wrappers in the requested schema. Null if schema doesn't exist.
     */
    List<TableWrapper> getSchemaTables(UserHome userHome, String connectionName, String schemaName);

    /**
     * Retrieves a list of check templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param checkType      (Optional) Check type.
     * @param checkTimeScale (Optional) Check time-scale.
     * @param checkTarget    (Optional) Check target.
     * @param checkCategory  (Optional) Check category.
     * @param checkName      (Optional) Check name.
     * @param principal      User principal.
     * @return List of check templates in the requested schema, matching the optional filters. Null if schema doesn't exist.
     */
    List<CheckTemplate> getCheckTemplates(String connectionName,
                                          String schemaName,
                                          CheckType checkType,
                                          CheckTimeScale checkTimeScale,
                                          CheckTarget checkTarget,
                                          String checkCategory,
                                          String checkName,
                                          DqoUserPrincipal principal);

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
     * @param principal         User principal.
     * @return UI friendly data quality profiling check configuration list on a requested schema.
     */
    List<CheckConfigurationModel> getCheckConfigurationsOnSchema(
            String connectionName,
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
            DqoUserPrincipal principal);
}
