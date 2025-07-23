/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.remote.tables;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.rest.models.remote.RemoteTableListModel;

import java.util.List;

/**
 * Management service for tables on the source database.
 */
public interface SourceTablesService {

    /**
     * Returns a list of tables on a schema on the source database.
     * @param connectionName     Connection name. Required import.
     * @param schemaName         Schema name.
     * @param tableNameContains  Optional filter to return tables that contain this text in the name.
     * @param principal          Calling user principal.
     * @return Schema list acquired remotely.
     */
    List<RemoteTableListModel> showTablesOnRemoteSchema(String connectionName,
                                                        String schemaName,
                                                        String tableNameContains,
                                                        DqoUserPrincipal principal);
}
