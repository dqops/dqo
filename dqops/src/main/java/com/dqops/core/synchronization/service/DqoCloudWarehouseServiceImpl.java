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
package com.dqops.core.synchronization.service;

import com.dqops.cloud.rest.api.TenantDataWarehouseApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.RefreshTableRequest;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.TargetTableModifiedPartitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service that asks the DQOps Cloud to refresh the data quality data warehouse after uploading new parquet files.
 */
@Component
public class DqoCloudWarehouseServiceImpl implements DqoCloudWarehouseService {
    private static final Map<DqoRoot, RefreshTableRequest.TableEnum> ROOT_TABLE_REQUEST_MAPPING = new LinkedHashMap<>() {{
        put(DqoRoot.data_sensor_readouts, RefreshTableRequest.TableEnum.SENSOR_READOUTS);
        put(DqoRoot.data_check_results, RefreshTableRequest.TableEnum.CHECK_RESULTS);
        put(DqoRoot.data_errors, RefreshTableRequest.TableEnum.ERRORS);
        put(DqoRoot.data_statistics, RefreshTableRequest.TableEnum.STATISTICS);
        put(DqoRoot.data_incidents, RefreshTableRequest.TableEnum.INCIDENTS);
    }};

    private DqoCloudApiClientFactory dqoCloudApiClientFactory;

    /**
     * Creates a new instance of a warehouse client, accepting required dependencies.
     * @param dqoCloudApiClientFactory DQOps Cloud client factory.
     */
    @Autowired
    public DqoCloudWarehouseServiceImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
    }

    /**
     * Refreshes a target table, sending additional information with the list of modified connections, tables and months that should be refreshed.
     * @param targetTableModifiedPartitions Target table modified partitions. Identifies the target table and lists all unique connections, tables, dates of the month to be refreshed.
     * @param userIdentity User identity that identifies the target data domain.
     */
    @Override
    public void refreshNativeTable(TargetTableModifiedPartitions targetTableModifiedPartitions, UserDomainIdentity userIdentity) {
        RefreshTableRequest.TableEnum targetTableParameter = ROOT_TABLE_REQUEST_MAPPING.get(targetTableModifiedPartitions.getTargetTable());
        if (targetTableParameter == null) {
            return; // this target is not stored in the data warehouse, it could be the "sources" folder with yaml files, etc.
        }

        ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userIdentity);
        TenantDataWarehouseApi tenantDataWarehouseApi = new TenantDataWarehouseApi(authenticatedClient);

        RefreshTableRequest refreshTableRequest = new RefreshTableRequest();
        refreshTableRequest.setTable(targetTableParameter);

        if (!targetTableModifiedPartitions.getAffectedConnections().isEmpty()) {
            refreshTableRequest.setConnections(new ArrayList<>(targetTableModifiedPartitions.getAffectedConnections()));
        }

        if (!targetTableModifiedPartitions.getAffectedTables().isEmpty()) {
            refreshTableRequest.setTables(new ArrayList<>(targetTableModifiedPartitions.getAffectedTables()));
        }

        if (!targetTableModifiedPartitions.getAffectedMonths().isEmpty()) {
            refreshTableRequest.setMonths(new ArrayList<>(targetTableModifiedPartitions.getAffectedMonths()));
        }

        if (!targetTableModifiedPartitions.getAffectedPartitions().isEmpty()) {
            refreshTableRequest.setPartitions(new ArrayList<>(targetTableModifiedPartitions.getAffectedPartitions()));
        }

        String targetTableName = targetTableModifiedPartitions.getTargetTable().toString();
        tenantDataWarehouseApi.refreshNativeTable(refreshTableRequest, userIdentity.getTenantOwner(), userIdentity.getTenantId(), targetTableName);
    }
}
