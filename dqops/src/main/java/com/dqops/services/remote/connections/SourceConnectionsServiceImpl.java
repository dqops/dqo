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

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.SourceConnection;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.fileslisting.DuckdbTestConnection;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.remote.ConnectionTestModel;
import com.dqops.rest.models.remote.ConnectionTestStatus;
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
    private final DuckdbTestConnection duckdbTestConnection;

    @Autowired
    public SourceConnectionsServiceImpl(ConnectionProviderRegistry connectionProviderRegistry,
                                        SecretValueProvider secretValueProvider,
                                        UserHomeContextFactory userHomeContextFactory,
                                        DuckdbTestConnection duckdbTestConnection) {
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
        this.userHomeContextFactory = userHomeContextFactory;
        this.duckdbTestConnection = duckdbTestConnection;
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

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            if(providerType.equals(ProviderType.duckdb)){
                DuckdbParametersSpec duckdbParametersSpec = sourceConnection.getConnectionSpec().getDuckdb();
                duckdbTestConnection.testConnection(duckdbParametersSpec);
            } else {
                sourceConnection.listSchemas();
            }
            connectionTestModel.setConnectionTestResult(ConnectionTestStatus.SUCCESS);

        } catch (Throwable e) {
            log.info("Failed to test a connection, error: " + e.getMessage(), e);
            connectionTestModel.setConnectionTestResult(ConnectionTestStatus.FAILURE);
            connectionTestModel.setErrorMessage(e.getMessage());
        }
        return connectionTestModel;
    }
}
