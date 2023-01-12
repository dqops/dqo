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
package ai.dqo.rest.controllers.remote.services;

import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.rest.models.remote.ConnectionRemoteModel;

/**
 * Management service for remote connection.
 */
public interface SourceConnectionsService {

    /**
     * Returns the status of the remote connection.
     * @param connectionSpec Connection spec model.
     * @param connectionName Connection name.
     * @return Connection status acquired remotely.
     */

    ConnectionRemoteModel checkConnection(String connectionName, ConnectionSpec connectionSpec);
}
