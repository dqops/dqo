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

import ai.dqo.connectors.*;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.rest.models.remote.ConnectionRemoteModel;
import ai.dqo.rest.models.remote.ConnectionStatusRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Connection status on remote database management service.
 */
@Component
public class SourceConnectionsServiceImpl implements SourceConnectionsService {

    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SecretValueProvider secretValueProvider;


    @Autowired
    public SourceConnectionsServiceImpl(UserHomeContextFactory userHomeContextFactory, ConnectionProviderRegistry connectionProviderRegistry,
                                        SecretValueProvider secretValueProvider) {
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
    }

    /**
     * Returns a list of schemas for local connection.
     * @param connectionSpec     Connection name.
     * @return Schema list acquired remotely. Null in case of object not found.
     */
    @Override
    public ConnectionRemoteModel showSchemas(ConnectionSpec connectionSpec) {
        ConnectionRemoteModel connectionRemoteModel = new ConnectionRemoteModel();

        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);
        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);

        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            sourceConnection.listSchemas();
            connectionRemoteModel.setConnectionStatus(ConnectionStatusRemote.SUCCESS);

        } catch (Exception e) {
            connectionRemoteModel.setConnectionStatus(ConnectionStatusRemote.FAIL);
            connectionRemoteModel.setMessage(e.getMessage());
        }
        return connectionRemoteModel;
    }

}
