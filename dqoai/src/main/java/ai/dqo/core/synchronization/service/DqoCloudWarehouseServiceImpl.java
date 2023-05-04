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
package ai.dqo.core.synchronization.service;

import ai.dqo.cloud.rest.api.TenantDataWarehouseApi;
import ai.dqo.cloud.rest.handler.ApiClient;
import ai.dqo.cloud.rest.model.RefreshTableRequest;
import ai.dqo.core.dqocloud.client.DqoCloudApiClientFactory;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.fileexchange.TargetTableModifiedPartitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service that asks the DQO Cloud to refresh the data quality data warehouse after uploading new parquet files.
 */
@Component
public class DqoCloudWarehouseServiceImpl implements DqoCloudWarehouseService {
    private static final Map<DqoRoot, RefreshTableRequest.TableEnum> ROOT_TABLE_REQUEST_MAPPING = new HashMap<>() {{
        put(DqoRoot.data_sensor_readouts, RefreshTableRequest.TableEnum.SENSOR_READOUTS);
        put(DqoRoot.data_check_results, RefreshTableRequest.TableEnum.CHECK_RESULTS);
        put(DqoRoot.data_errors, RefreshTableRequest.TableEnum.ERRORS);
        put(DqoRoot.data_statistics, RefreshTableRequest.TableEnum.STATISTICS);
        put(DqoRoot.data_incidents, RefreshTableRequest.TableEnum.INCIDENTS);
    }};

    private DqoCloudApiClientFactory dqoCloudApiClientFactory;

    /**
     * Creates a new instance of a warehouse client, accepting required dependencies.
     * @param dqoCloudApiClientFactory DQO Cloud client factory.
     */
    @Autowired
    public DqoCloudWarehouseServiceImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
    }

    /**
     * Refreshes a target table, sending additional information with the list of modified connections, tables and months that should be refreshed.
     * @param targetTableModifiedPartitions Target table modified partitions. Identifies the target table and lists all unique connections, tables, dates of the month to be refreshed.
     */
    @Override
    public void refreshNativeTable(TargetTableModifiedPartitions targetTableModifiedPartitions) {
        RefreshTableRequest.TableEnum targetTableParameter = ROOT_TABLE_REQUEST_MAPPING.get(targetTableModifiedPartitions.getTargetTable());
        if (targetTableParameter == null) {
            return; // this target is not stored in the data warehouse, it could be the "sources" folder with yaml files, etc.
        }

        ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient();
        TenantDataWarehouseApi tenantDataWarehouseApi = new TenantDataWarehouseApi(authenticatedClient);

        RefreshTableRequest refreshTableRequest = new RefreshTableRequest();
        refreshTableRequest.setTable(targetTableParameter);

        if (targetTableModifiedPartitions.getAffectedConnections().size() > 0) {
            refreshTableRequest.setConnections(new ArrayList<>(targetTableModifiedPartitions.getAffectedConnections()));
        }

        if (targetTableModifiedPartitions.getAffectedTables().size() > 0) {
            refreshTableRequest.setTables(new ArrayList<>(targetTableModifiedPartitions.getAffectedTables()));
        }

        if (targetTableModifiedPartitions.getAffectedMonths().size() > 0) {
            refreshTableRequest.setMonths(new ArrayList<>(targetTableModifiedPartitions.getAffectedMonths()));
        }

        tenantDataWarehouseApi.refreshNativeTable(refreshTableRequest);
    }
}
