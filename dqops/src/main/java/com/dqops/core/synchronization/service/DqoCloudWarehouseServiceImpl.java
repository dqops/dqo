/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.service;

import com.dqops.cloud.rest.api.TenantDataWarehouseApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.RefreshTableRequest;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.fileexchange.TargetTableModifiedPartitions;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
        refreshTableRequest.setDataDomain(Objects.equals(userIdentity.getDataDomainCloud(), UserDomainIdentity.ROOT_DATA_DOMAIN) ? null : userIdentity.getDataDomainCloud());

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
        try {
            tenantDataWarehouseApi.refreshNativeTable(refreshTableRequest, userIdentity.getTenantOwner(), userIdentity.getTenantId(), targetTableName);
        }
        catch (Exception ex) {
            throw new DqoRuntimeException("Failed to refresh a table in the data warehouse, table: " + targetTableModifiedPartitions.getTargetTable() + ", error: " +
                    ex.getMessage(), ex);
        }
    }
}
