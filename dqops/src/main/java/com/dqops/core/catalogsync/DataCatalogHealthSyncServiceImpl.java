/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.catalogsync;

import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Service that initiates sending updated health statuses to the data catalog for requested tables.
 */
@Component
public class DataCatalogHealthSyncServiceImpl implements DataCatalogHealthSyncService {
    private final TableStatusCache tableStatusCache;
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;

    /**
     * Dependency injection constructor.
     * @param tableStatusCache Table status cache.
     * @param hierarchyNodeTreeSearcher Hierarchy search service.
     */
    @Autowired
    public DataCatalogHealthSyncServiceImpl(TableStatusCache tableStatusCache,
                                            HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher) {
        this.tableStatusCache = tableStatusCache;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
    }

    /**
     * Retrieves and sends the current data quality health status to the data catalog for each table in the given user home
     * that matches the table filter.
     * @param userHome User home with tables to iterate over and synchronize.
     * @param tableSearchFilters Table search filter for matching tables.
     */
    @Override
    public void synchronizeDataCatalog(UserHome userHome, TableSearchFilters tableSearchFilters) {
        String dataDomain = userHome.getUserIdentity().getDataDomainCloud();
        Collection<TableWrapper> tableWrappers = this.hierarchyNodeTreeSearcher.findTables(userHome.getConnections(), tableSearchFilters);

        for (TableWrapper tableWrapper : tableWrappers) {
            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                continue;
            }

            DomainConnectionTableKey domainConnectionTableKey = new DomainConnectionTableKey(
                    dataDomain, tableSpec.getHierarchyId().getConnectionName(), tableSpec.getPhysicalTableName());

            this.tableStatusCache.sendCurrentTableStatusToDataCatalog(domainConnectionTableKey);
        }
    }
}
