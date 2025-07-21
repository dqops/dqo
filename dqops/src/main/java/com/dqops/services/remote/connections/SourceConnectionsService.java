/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.remote.connections;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.rest.models.remote.ConnectionTestModel;

/**
 * Management service for remote connection.
 */
public interface SourceConnectionsService {
    /**
     * Returns the status of the remote connection.
     * @param principal User principal.
     * @param connectionSpec Connection spec model.
     * @param connectionName Connection name.
     * @param verifyNameUniqueness Verify if the name is unique. Name uniqueness is not verified when the connection is checked again on the connection details screen (re-tested).
     * @return Connection status acquired remotely.
     */

    ConnectionTestModel testConnection(DqoUserPrincipal principal,
                                       String connectionName,
                                       ConnectionSpec connectionSpec,
                                       boolean verifyNameUniqueness);
}
