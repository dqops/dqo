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

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.SourceConnection;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.remote.ConnectionTestStatus;
import com.dqops.rest.models.remote.ConnectionTestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Management service for remote connection.
 */
@Component
@Slf4j
public class SourceConnectionsServiceImpl implements SourceConnectionsService {
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SecretValueProvider secretValueProvider;
    private final UserHomeContextFactory userHomeContextFactory;


    @Autowired
    public SourceConnectionsServiceImpl(ConnectionProviderRegistry connectionProviderRegistry,
                                        SecretValueProvider secretValueProvider,
                                        UserHomeContextFactory userHomeContextFactory) {
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
        this.userHomeContextFactory = userHomeContextFactory;
    }


    /**
     * Returns the status of the remote connection.
     * @param principal User principal.
     * @param connectionSpec Connection spec model.
     * @param connectionName Connection name.
     * @param verifyNameUniqueness Verify if the name is unique. Name uniqueness is not verified when the connection is checked again on the connection details screen (re-tested).
     * @return Status of the remote connection.
     */
    @Override
    public ConnectionTestModel testConnection(DqoUserPrincipal principal,
                                              String connectionName,
                                              ConnectionSpec connectionSpec,
                                              boolean verifyNameUniqueness) {
        ConnectionTestModel connectionTestModel = new ConnectionTestModel();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        if (verifyNameUniqueness) {
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper != null) {
                connectionTestModel.setConnectionTestResult(ConnectionTestStatus.CONNECTION_ALREADY_EXISTS);
                connectionTestModel.setErrorMessage("A connection with the name you specified: " + connectionName + " already exists!");
                return connectionTestModel;
            }
        }

        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);
        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);

        try {
            SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext);
            sourceConnection.listSchemas();
            connectionTestModel.setConnectionTestResult(ConnectionTestStatus.SUCCESS);

        } catch (Exception e) {
            log.info("Failed to test a connection, error: " + e.getMessage(), e);
            connectionTestModel.setConnectionTestResult(ConnectionTestStatus.FAILURE);
            connectionTestModel.setErrorMessage(e.getMessage());
        }
        return connectionTestModel;
    }
}
