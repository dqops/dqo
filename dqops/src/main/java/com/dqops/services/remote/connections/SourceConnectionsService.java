/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
