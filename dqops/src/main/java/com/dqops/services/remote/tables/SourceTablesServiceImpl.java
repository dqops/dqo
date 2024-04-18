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
package com.dqops.services.remote.tables;

import com.dqops.connectors.*;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.remote.RemoteTableListModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Schema on remote database management service.
 */
@Component
@Slf4j
public class SourceTablesServiceImpl implements SourceTablesService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private SecretValueProvider secretValueProvider;

    @Autowired
    public SourceTablesServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                   ConnectionProviderRegistry connectionProviderRegistry,
                                   SecretValueProvider secretValueProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
    }

    /**
     * Returns a list of tables on a schema on the source database.
     *
     * @param connectionName Connection name. Required import.
     * @param schemaName     Schema name.
     * @param principal      Calling user principal.
     * @return Table list acquired remotely.
     */
    public List<RemoteTableListModel> showTablesOnRemoteSchema(String connectionName, String schemaName, DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        Set<String> importedTableNames = connectionWrapper.getTables().toList().stream()
                .map(TableWrapper::getPhysicalTableName)
                .filter(physicalTableName -> physicalTableName.getSchemaName().equals(schemaName))
                .map(PhysicalTableName::getTableName)
                .collect(Collectors.toSet());

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        List<RemoteTableListModel> remoteTableListModels;

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<SourceTableModel> sourceTableModels = sourceConnection.listTables(schemaName, secretValueLookupContext);
            remoteTableListModels = sourceTableModels.stream()
                    .map(sourceTableModel -> new RemoteTableListModel(){{
                        setConnectionName(connectionName);
                        setSchemaName(sourceTableModel.getSchemaName());
                        setAlreadyImported(importedTableNames.contains(sourceTableModel.getTableName().getTableName()));
                        setTableName(sourceTableModel.getTableName().getTableName());
                    }}).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to list remote tables, error: " + e.getMessage(), e);
            throw new SourceTablesServiceException("Source database connection error: " + e.getMessage(), e);
        }

        return remoteTableListModels;
    }
}
