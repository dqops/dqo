/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.table.impl;

/**
 * Service that launches an editor to edit the table specification.
 */
public interface TableEditService {
    /**
     * Finds a table and opens the default text editor to edit the yaml file.
     * @param connectionName Connection name.
     * @param fullTableName Full table name e.g. schema.table.
     * @return Error code: 0 when the table was found, -1 when the connection or table was not found.
     */
    int launchEditorForTable(String connectionName, String fullTableName);
}
