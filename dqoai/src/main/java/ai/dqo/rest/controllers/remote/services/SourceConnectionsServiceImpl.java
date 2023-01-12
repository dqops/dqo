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
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.remote.ConnectionRemoteModel;
import ai.dqo.rest.models.remote.ConnectionStatusRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Management service for remote connection.
 */
@Component
public class SourceConnectionsServiceImpl implements SourceConnectionsService {

    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SecretValueProvider secretValueProvider;
    private final UserHomeContextFactory userHomeContextFactory;



    @Autowired
    public SourceConnectionsServiceImpl(ConnectionProviderRegistry connectionProviderRegistry,
                                        SecretValueProvider secretValueProvider, UserHomeContextFactory userHomeContextFactory) {
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
        this.userHomeContextFactory = userHomeContextFactory;
    }


    /**
     * Returns the status of the remote connection.
     * @param connectionSpec Connection spec model.
     * @param connectionName Connection name.
     * @return Status of the remote connection.
     */
    @Override
    public ConnectionRemoteModel checkConnection(String connectionName, ConnectionSpec connectionSpec) {
        ConnectionRemoteModel connectionRemoteModel = new ConnectionRemoteModel();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);

        if (connectionWrapper != null) {
            connectionRemoteModel.setConnectionStatus(ConnectionStatusRemote.FAILURE);
            connectionRemoteModel.setErrorMessage("A connection with the name you specified: "+connectionName+ " already exists!");
            return connectionRemoteModel;
        }

        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);
        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);

        try {
            SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true);
            sourceConnection.listSchemas();
            connectionRemoteModel.setConnectionStatus(ConnectionStatusRemote.SUCCESS);

        } catch (Exception e) {
            connectionRemoteModel.setConnectionStatus(ConnectionStatusRemote.FAILURE);
            connectionRemoteModel.setErrorMessage(e.getMessage());
        }
        return connectionRemoteModel;
    }

}
