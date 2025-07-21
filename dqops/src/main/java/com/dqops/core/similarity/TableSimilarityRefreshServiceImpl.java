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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service that receives notifications about updated table statistics to refresh the search indexes.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class TableSimilarityRefreshServiceImpl implements TableSimilarityRefreshService {
    public static int SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE = 1000000; // the number of awaiting operations in the backpressure buffer (queue)
    public static long BATCH_COLLECTION_TIMEOUT_MS = 300L; // the time to wait for all updates before starting a new batch

    private volatile boolean started;
    private Sinks.Many<SimilarityContainerQueueKey> messageQueueSink;
    private Disposable subscription;

    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private final UserHomeContextFactory userHomeContextFactory;
    private final TableSimilarityScoreFactory tableSimilarityScoreFactory;
    private final StatisticsDataService statisticsDataService;
    private final Object lock = new Object();
    private final Map<SimilarityContainerQueueKey, Set<PhysicalTableName>> connectionsToRefresh = new ConcurrentHashMap<>();

    @Autowired
    public TableSimilarityRefreshServiceImpl(
            UserDomainIdentityFactory userDomainIdentityFactory,
            UserHomeContextFactory userHomeContextFactory,
            TableSimilarityScoreFactory tableSimilarityScoreFactory,
            StatisticsDataService statisticsDataService) {
        this.userDomainIdentityFactory = userDomainIdentityFactory;
        this.userHomeContextFactory = userHomeContextFactory;
        this.tableSimilarityScoreFactory = tableSimilarityScoreFactory;
        this.statisticsDataService = statisticsDataService;
    }

    /**
     * Creates a failure handler with a new duration.
     * @return Failure handler.
     */
    protected Sinks.EmitFailureHandler createFailureHandler() {
        return Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(30L));
    }

    /**
     * Notifies the table similarity refresh service that a table was updated and its search index should be refreshed.
     * @param dataDomain Data domain name.
     * @param connection Connection name.
     * @param table Physical table name.
     */
    @Override
    public void refreshTable(String dataDomain, String connection, PhysicalTableName table) {
        SimilarityContainerQueueKey similarityContainerQueueKey = new SimilarityContainerQueueKey(dataDomain, connection);

        try {
            synchronized (this.lock) {
                Set<PhysicalTableName> tableQueue = this.connectionsToRefresh.get(similarityContainerQueueKey);
                if (tableQueue != null) {
                    tableQueue.add(table);
                } else {
                    tableQueue = new HashSet<>();
                    tableQueue.add(table);
                    this.connectionsToRefresh.put(similarityContainerQueueKey, tableQueue);

                    this.messageQueueSink.emitNext(similarityContainerQueueKey, createFailureHandler());
                }
            }
        }
        catch (Exception ex) {
            log.error("Failed to queue refreshing search statistics for table " + connection + "." + table.toBaseFileName() + ", error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Refreshes tables in a connection.
     * @param connectionQueueKey Connection to refresh.
     * @return Empty mono.
     */
    public Mono<Void> onRefreshConnection(SimilarityContainerQueueKey connectionQueueKey) {
        try {
            Set<PhysicalTableName> updatedTables;
            synchronized (this.lock) {
                updatedTables = this.connectionsToRefresh.get(connectionQueueKey);
                this.connectionsToRefresh.remove(connectionQueueKey);
            }

            UserDomainIdentity domainAdminIdentity = userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(connectionQueueKey.getDataDomain());

            UserHomeContext domainUserHomeContextReadOnly = this.userHomeContextFactory.openLocalUserHome(domainAdminIdentity, true);
            UserHomeContext domainUserHomeContextWrite = this.userHomeContextFactory.openLocalUserHome(domainAdminIdentity, false);
            ConnectionList connectionList = domainUserHomeContextReadOnly.getUserHome().getConnections();
            ConnectionSimilarityIndexList connectionSimilarityIndices = domainUserHomeContextWrite.getUserHome().getConnectionSimilarityIndices();

            String connectionName = connectionQueueKey.getConnectionName();
            ConnectionSimilarityIndexWrapper connectionSimilarityIndexWrapper = connectionSimilarityIndices.getByObjectName(
                    connectionName, true);
            if (connectionSimilarityIndexWrapper == null) {
                connectionSimilarityIndexWrapper = connectionSimilarityIndices.createAndAddNew(connectionName);
            }

            ConnectionSimilarityIndexSpec similarityIndexSpec = connectionSimilarityIndexWrapper.getSpec().deepClone();

            ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                connectionSimilarityIndices.remove(connectionName);
            } else {
                for (PhysicalTableName updatedTableName : updatedTables) {
                    if (!this.started) {
                        return Mono.empty(); // shutdown
                    }

                    TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(updatedTableName, true);
                    if (tableWrapper == null) {
                        similarityIndexSpec.remove(updatedTableName);
                        continue;
                    }

                    TableSimilarityContainer tableSimilarityContainerCurrent = similarityIndexSpec.get(updatedTableName);
                    Instant statisticsLastModified = this.statisticsDataService.getStatisticsLastModified(connectionName, updatedTableName, domainAdminIdentity);

                    if (tableSimilarityContainerCurrent != null && Objects.equals(tableSimilarityContainerCurrent.getLm(), statisticsLastModified)) {
                        continue;
                    }

                    TableSimilarityContainer tableSimilarityContainer = this.tableSimilarityScoreFactory.calculateSimilarityScore(connectionName, updatedTableName, domainAdminIdentity);
                    if (tableSimilarityContainer == null) {
                        similarityIndexSpec.remove(updatedTableName);
                    } else {
                        similarityIndexSpec.set(updatedTableName, tableSimilarityContainer);
                    }
                }

                connectionSimilarityIndexWrapper.setSpec(similarityIndexSpec);
            }

            domainUserHomeContextWrite.flush();
        }
        catch (Exception ex) {
            log.error("Failed to refresh the search index of a connection " + connectionQueueKey.getConnectionName() + ", error: " + ex.getMessage(), ex);
        }
        
        return Mono.empty();
    }

    /**
     * Starts the service.
     */
    @Override
    public void start() {
        if (this.started) {
            return;
        }

        this.started = true;

        this.messageQueueSink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<List<SimilarityContainerQueueKey>> requestLoadFlux = this.messageQueueSink.asFlux()
                .onBackpressureBuffer(SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE)
                .buffer(Duration.ofMillis(BATCH_COLLECTION_TIMEOUT_MS));  // wait 300 millis, maybe multiple file system updates are made, like changing multiple parquet files... we want to merge all file changes
        int concurrency = Runtime.getRuntime().availableProcessors();
        this.subscription = requestLoadFlux.subscribeOn(Schedulers.boundedElastic())
                .flatMap(connectionsToRefresh -> Flux.fromIterable(connectionsToRefresh))
                .parallel(concurrency)
                .flatMap(connectionQueueKey -> {
                    return onRefreshConnection(connectionQueueKey);
                })
                .subscribe();
    }

    /**
     * Stops the service.
     */
    @Override
    public void stop() {
        if (!this.started) {
            return;
        }

        try {
            this.started = false;
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds( 30L));
            this.messageQueueSink.emitComplete(emitFailureHandler);
            this.subscription.dispose();
        }
        catch (Exception ex) {
            log.error("Failed to stop the table search index service, error: " + ex.getMessage(), ex);
        }
    }
}
