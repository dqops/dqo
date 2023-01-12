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
package ai.dqo.services.remote.sourcetables;

import ai.dqo.connectors.*;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.remote.TableRemoteBasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Schema on remote database management service.
 */
@Component
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
     * @return Table list acquired remotely.
     */
    public List<TableRemoteBasicModel> showTablesOnRemoteSchema(String connectionName, String schemaName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);

        List<TableRemoteBasicModel> tableRemoteBasicModels;

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            List<SourceTableModel> sourceTableModels = sourceConnection.listTables(schemaName);
            tableRemoteBasicModels = sourceTableModels.stream()
                    .map(sourceTableModel -> new TableRemoteBasicModel(){{
                        setConnectionName(connectionName);
                        setSchemaName(sourceTableModel.getSchemaName());
                        setAlreadyImported(importedTableNames.contains(sourceTableModel.getTableName().getTableName()));
                        setTableName(sourceTableModel.getTableName().getTableName());
                    }}).collect(Collectors.toList());
        } catch (Exception e) {
            throw new SourceTablesServiceException("Source database connection error: " + e.getMessage(), e);
        }

        return tableRemoteBasicModels;
    }
}
