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
package com.dqops.services.remote.schemas;

import com.dqops.connectors.*;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.remote.SchemaRemoteModel;
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
public class SourceSchemasServiceImpl implements SourceSchemasService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private SecretValueProvider secretValueProvider;

    @Autowired
    public SourceSchemasServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                    ConnectionProviderRegistry connectionProviderRegistry,
                                    SecretValueProvider secretValueProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
    }

    /**
     * Returns a list of schemas for local connection.
     * @param connectionName     Connection name.
     * @param principal          Calling user principal.
     * @return Schema list acquired remotely. Null in case of object not found.
     */
    public List<SchemaRemoteModel> showSchemas(String connectionName, DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        Set<String> importedSchemaNames = connectionWrapper.getTables().toList().stream()
                .map(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName())
                .collect(Collectors.toSet());

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        List<SchemaRemoteModel> schemaRemoteModels;

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<SourceSchemaModel> sourceSchemaModels = sourceConnection.listSchemas();
            schemaRemoteModels = sourceSchemaModels.stream()
                .map(sourceSchemaModel -> new SchemaRemoteModel(){{
                    setSchemaName(sourceSchemaModel.getSchemaName());
                    setConnectionName(connectionName);
                    setAlreadyImported(importedSchemaNames.contains(this.getSchemaName()));
                    setImportTableJobParameters(new ImportTablesQueueJobParameters(connectionName, sourceSchemaModel.getSchemaName(), null));
                }}).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to list schemas from a connection, error: " + e.getMessage(), e);
            throw new SourceSchemasServiceException("Source database connection error: " + e.getMessage(), e);
        }

        return schemaRemoteModels;
    }
}
