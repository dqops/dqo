/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.remote.schemas;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.rest.models.remote.SchemaRemoteModel;

import java.util.List;

/**
 * Schema on remote database management service.
 */
public interface SourceSchemasService {

    /**
     * Returns a list of schemas for local connection.
     * @param connectionName     Connection name.
     * @param principal          Calling user principal.
     * @return Schema list acquired remotely.
     */
    List<SchemaRemoteModel> showSchemas(String connectionName, DqoUserPrincipal principal);
}
