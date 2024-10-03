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

package com.dqops.core.similarity;

import com.dqops.core.domains.DataDomainsService;
import com.dqops.core.domains.LocalDataDomainModel;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexList;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexSpec;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexWrapper;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Background service that reconciles search indexes in the background.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TableSimilarityReconciliationServiceImpl {
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
    private boolean started;

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
    public void start() {
        this.started = true;
    }

    /**
     * Updates the search index. This operation is called by a CRON scheduler.
     */
    @Scheduled(fixedRate = REINDEX_DELAY, initialDelay = INITIAL_DELAY, timeUnit = TimeUnit.SECONDS)
    public void reconcileSearchIndex() {
        if (!this.started) {
            return;
        }

        List<LocalDataDomainModel> allDataDomains = this.dataDomainsService.getAllDataDomains();
        for (LocalDataDomainModel dataDomainModel : allDataDomains) {
            UserDomainIdentity domainAdminIdentity = userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(dataDomainModel.getDomainName());

            UserHomeContext domainUserHomeContextReadOnly = this.userHomeContextFactory.openLocalUserHome(domainAdminIdentity, true);
            UserHomeContext domainUserHomeContextWrite = this.userHomeContextFactory.openLocalUserHome(domainAdminIdentity, false);
            ConnectionList connectionList = domainUserHomeContextReadOnly.getUserHome().getConnections();
            ConnectionSimilarityIndexList connectionSimilarityIndices = domainUserHomeContextWrite.getUserHome().getConnectionSimilarityIndices();

            for (ConnectionWrapper connectionWrapper : connectionList) {
                ConnectionSimilarityIndexWrapper connectionSimilarityIndexWrapper = connectionSimilarityIndices.getByObjectName(
                        connectionWrapper.getName(), true);
                if (connectionSimilarityIndexWrapper == null) {
                    connectionSimilarityIndexWrapper = connectionSimilarityIndices.createAndAddNew(connectionWrapper.getName());
                }

                ConnectionSimilarityIndexSpec similarityIndexSpec = connectionSimilarityIndexWrapper.getSpec();
                for (TableWrapper tableWrapper : connectionWrapper.getTables()) {
                    // read also a file modification date of the TableSpec, also index the naming

                }

                for (PhysicalTableName indexedTableKey : similarityIndexSpec.getAllTables()) {
                    if (connectionWrapper.getTables().getByObjectName(indexedTableKey, true) == null) {
                        similarityIndexSpec.remove(indexedTableKey);
                    }
                }
            }

            for (ConnectionSimilarityIndexWrapper connectionSimilarityIndexWrapper : connectionSimilarityIndices.toList()) {
                if (connectionList.getByObjectName(connectionSimilarityIndexWrapper.getConnectionName(), true) == null) {
                    connectionSimilarityIndices.remove(connectionSimilarityIndexWrapper.getConnectionName());
                }
            }

            domainUserHomeContextWrite.flush();
        }
    }
}
