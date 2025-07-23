/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.similarity;

import com.dqops.core.domains.DataDomainsService;
import com.dqops.core.domains.LocalDataDomainModel;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexList;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexSpec;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexWrapper;
import com.dqops.metadata.similarity.TableSimilarityContainer;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Background service that reconciles search indexes in the background.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableSimilarityReconciliationServiceImpl implements TableSimilarityReconciliationService, DisposableBean {
    /**
     * The delay between reindexing the search index.
     */
    public static final long REINDEX_DELAY = 3600 * 12;

    /**
     * The initial delay to update the search index since the startup.
     */
    public static final long INITIAL_DELAY = 10 * 60;

    private final DataDomainsService dataDomainsService;
    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private final UserHomeContextFactory userHomeContextFactory;
    private final TableSimilarityScoreFactory tableSimilarityScoreFactory;
    private final StatisticsDataService statisticsDataService;
    private volatile boolean started;

    @Autowired
    public TableSimilarityReconciliationServiceImpl(
            DataDomainsService dataDomainsService,
            UserDomainIdentityFactory userDomainIdentityFactory,
            UserHomeContextFactory userHomeContextFactory,
            TableSimilarityScoreFactory tableSimilarityScoreFactory,
            StatisticsDataService statisticsDataService) {
        this.dataDomainsService = dataDomainsService;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
        this.userHomeContextFactory = userHomeContextFactory;
        this.tableSimilarityScoreFactory = tableSimilarityScoreFactory;
        this.statisticsDataService = statisticsDataService;
    }

    /**
     * Enables the service.
     */
    @Override
    public void start() {
        this.started = true;
    }

    /**
     * Called by spring when the service is shutting down. Stops an ongoing reconciliation.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        this.started = false;
    }

    /**
     * Updates the search index. This operation is called by a CRON scheduler.
     */
    @Override
    @Scheduled(fixedRate = REINDEX_DELAY, initialDelay = INITIAL_DELAY, timeUnit = TimeUnit.SECONDS)
    public void reconcileSearchIndex() {
        if (!this.started) {
            return;
        }

        List<LocalDataDomainModel> allDataDomains = this.dataDomainsService.getAllDataDomains();
        for (LocalDataDomainModel dataDomainModel : allDataDomains) {
            if (!this.started) {
                return; // shutdown
            }

            UserDomainIdentity domainAdminIdentity = userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(dataDomainModel.getDomainName());

            UserHomeContext domainUserHomeContextReadOnly = this.userHomeContextFactory.openLocalUserHome(domainAdminIdentity, true);
            UserHomeContext domainUserHomeContextWrite = this.userHomeContextFactory.openLocalUserHome(domainAdminIdentity, false);
            ConnectionList connectionList = domainUserHomeContextReadOnly.getUserHome().getConnections();
            ConnectionSimilarityIndexList connectionSimilarityIndices = domainUserHomeContextWrite.getUserHome().getConnectionSimilarityIndices();

            for (ConnectionWrapper connectionWrapper : connectionList) {
                if (!this.started) {
                    return; // shutdown
                }

                String connectionName = connectionWrapper.getName();
                ConnectionSimilarityIndexWrapper connectionSimilarityIndexWrapper = connectionSimilarityIndices.getByObjectName(
                        connectionName, true);
                if (connectionSimilarityIndexWrapper == null) {
                    connectionSimilarityIndexWrapper = connectionSimilarityIndices.createAndAddNew(connectionName);
                }
                boolean modified = false;

                ConnectionSimilarityIndexSpec similarityIndexSpec = connectionSimilarityIndexWrapper.getSpec().deepClone();
                for (TableWrapper tableWrapper : connectionWrapper.getTables()) {
                    if (!this.started) {
                        return; // shutdown
                    }

                    // read also a file modification date of the TableSpec, also index the naming
                    PhysicalTableName physicalTableName = tableWrapper.getPhysicalTableName();
                    TableSimilarityContainer tableSimilarityContainerCurrent = similarityIndexSpec.get(physicalTableName);
                    Instant statisticsLastModified = this.statisticsDataService.getStatisticsLastModified(connectionName, physicalTableName, domainAdminIdentity);

                    if (tableSimilarityContainerCurrent != null && Objects.equals(tableSimilarityContainerCurrent.getLm(), statisticsLastModified)) {
                        continue;
                    }

                    TableSimilarityContainer tableSimilarityContainer = this.tableSimilarityScoreFactory.calculateSimilarityScore(connectionName, physicalTableName, domainAdminIdentity);
                    if (tableSimilarityContainer == null) {
                        similarityIndexSpec.remove(physicalTableName);
                    } else {
                        similarityIndexSpec.set(physicalTableName, tableSimilarityContainer);
                    }

                    modified = true;
                }

                for (PhysicalTableName indexedTableKey : similarityIndexSpec.getAllTables()) {
                    if (connectionWrapper.getTables().getByObjectName(indexedTableKey, true) == null) {
                        similarityIndexSpec.remove(indexedTableKey);
                        modified = true;
                    }
                }

                if (modified) {
                    connectionSimilarityIndexWrapper.setSpec(similarityIndexSpec);
                }
            }

            for (ConnectionSimilarityIndexWrapper connectionSimilarityIndexWrapper : connectionSimilarityIndices.toList()) {
                if (connectionList.getByObjectName(connectionSimilarityIndexWrapper.getConnectionName(), true) == null) {
                    connectionSimilarityIndices.remove(connectionSimilarityIndexWrapper.getConnectionName());
                }
            }

            if (!this.started) {
                return; // shutdown
            }

            domainUserHomeContextWrite.flush();
        }
    }
}
