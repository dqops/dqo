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

package com.dqops.metadata.lineage.lineagecache;

import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.metadata.lineage.TableLineageSourceSpec;
import com.dqops.metadata.lineage.TableLineageSourceSpecList;
import com.dqops.metadata.sources.*;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service that manages the cache of data lineage and updates it when it receives notifications about loaded or updated connections and tables.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class TableLineageCacheImpl implements TableLineageCache {
    public static int SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE = 1000000; // the number of awaiting operations in the backpressure buffer (queue)

    private final Map<TableLineageCacheKey, TableLineageCacheEntry> tableCache = new LinkedHashMap<>();
    private final DqoQueueConfigurationProperties dqoQueueConfigurationProperties;
    private final UserHomeContextFactory userHomeContextFactory;
    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private boolean started;
    private Sinks.Many<TableLineageCacheKey> loadObjectRequestSink;
    private Disposable subscription;
    private int queuedOperationsCount;
    private final Object lock = new Object();
    private CompletableFuture<Integer> queueEmptyFuture;

    /**
     * Creates a new instance of the data lineage cache, configuring the cache size.
     * @param dqoQueueConfigurationProperties Queue configuration parameters - to configure backpressure.
     * @param userHomeContextFactory User home context factory to load the user home.
     * @param userDomainIdentityFactory User domain identity for the admin user that identifies the correct data domain.
     */
    @Autowired
    public TableLineageCacheImpl(DqoQueueConfigurationProperties dqoQueueConfigurationProperties,
                                 UserHomeContextFactory userHomeContextFactory,
                                 UserDomainIdentityFactory userDomainIdentityFactory) {
        this.dqoQueueConfigurationProperties = dqoQueueConfigurationProperties;
        this.userHomeContextFactory = userHomeContextFactory;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
        this.queueEmptyFuture = new CompletableFuture<>();
        this.queueEmptyFuture.complete(0);
    }

    /**
     * Returns the information about the given table, its upstream tables and downstream tables.
     * @param tableLineageCacheKey Table entry cache.
     * @return Table entry node or null when the lineage for the table was not retrieved.
     */
    @Override
    public TableLineageCacheEntry getTableLineageEntry(TableLineageCacheKey tableLineageCacheKey) {
        TableLineageCacheEntry tableLineageCacheEntry;

        synchronized (this.lock) {
            tableLineageCacheEntry = this.tableCache.get(tableLineageCacheKey);
        }

        if (tableLineageCacheEntry == null) {
            this.invalidateObject(tableLineageCacheKey, false);
        }

        return tableLineageCacheEntry;
    }

    /**
     * Creates a failure handler with a new duration.
     * @return Failure handler.
     */
    protected Sinks.EmitFailureHandler createFailureHandler() {
        return Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(
                this.dqoQueueConfigurationProperties.getPublishBusyLoopingDurationSeconds()));
    }

    /**
     * The operation that is called to request loading data lineage for a new table.
     * @param targetKey Entry key.
     * @return Current entry instance.
     */
    protected TableLineageCacheEntry loadEntryCore(TableLineageCacheKey targetKey) {
        TableLineageCacheEntry lineageLoadEntry = new TableLineageCacheEntry(targetKey, TableLineageRefreshStatus.LOADING_QUEUED);
        if (this.loadObjectRequestSink != null) {
            this.loadObjectRequestSink.emitNext(targetKey, createFailureHandler());
            incrementAwaitingOperationsCount();
        }
        return lineageLoadEntry;
    }

    /**
     * Notifies the data lineage cache that a table yaml files were updated (or loaded into the cache) and should be scanned to load the data lineage.
     * @param tableLineageKey The target key that identifies an object that should be scanned for data lineage.
     * @param replacingCachedFile True when we are replacing a file that was already in a file system cache, false when a file is just placed into a cache,
     *                            and it is not a real invalidation, but just a notification that a file was just cached.
     */
    @Override
    public void invalidateObject(TableLineageCacheKey tableLineageKey, boolean replacingCachedFile) {
        synchronized (this.lock) {
            TableLineageCacheEntry currentTableLineageEntry = this.tableCache.get(tableLineageKey);
            if (currentTableLineageEntry == null) {
                currentTableLineageEntry = loadEntryCore(tableLineageKey);
                this.tableCache.put(tableLineageKey, currentTableLineageEntry);
            }

            TableLineageRefreshStatus currentEntryStatus = currentTableLineageEntry.getStatus();

            if (currentEntryStatus == TableLineageRefreshStatus.LOADING_QUEUED || currentEntryStatus == TableLineageRefreshStatus.REFRESH_QUEUED) {
                return; // another refresh was already queued
            }

            if (currentEntryStatus == TableLineageRefreshStatus.LOADING && !replacingCachedFile) {
                return; // this refresh was triggered by this class when it was loading yaml files and they were cached for the first time, it is not a change to a file
            }

            currentTableLineageEntry.setStatus(TableLineageRefreshStatus.REFRESH_QUEUED);
            this.loadObjectRequestSink.emitNext(tableLineageKey, createFailureHandler());
            incrementAwaitingOperationsCount();
        }
    }

    /**
     * Returns a future that is completed when there are no queued data lineage load/reload operations.
     * @param waitTimeoutMilliseconds Optional timeout to wait for the completion of the future. If the timeout elapses, the future is completed with a value <code>false</code>.
     * @return Future that is completed when the data lineage from all requested tables were loaded.
     */
    @Override
    public CompletableFuture<Boolean> getQueueEmptyFuture(Long waitTimeoutMilliseconds) {
        synchronized (this.lock) {
            CompletableFuture<Boolean> booleanCompletableFuture = this.queueEmptyFuture
                    .thenApply(result -> true);

            if (waitTimeoutMilliseconds != null) {
                CompletableFuture<Boolean> timeoutFuture = new CompletableFuture<Boolean>().completeOnTimeout(false, waitTimeoutMilliseconds, TimeUnit.MILLISECONDS);
                booleanCompletableFuture = (CompletableFuture<Boolean>)(CompletableFuture<?>)CompletableFuture.anyOf(booleanCompletableFuture, timeoutFuture);
            }

            return booleanCompletableFuture;
        }
    }

    /**
     * Increments the count of ongoing refresh operations.
     */
    protected void incrementAwaitingOperationsCount() {
        synchronized (this.lock) {
            if (this.queuedOperationsCount == 0) {
                this.queueEmptyFuture = new CompletableFuture<>(); // uncompleted future
            }
            this.queuedOperationsCount++;
        }
    }

    /**
     * Decrements the number of queued refresh operations, releasing the future.
     */
    protected void decrementAwaitingOperationsCount() {
        synchronized (this.lock) {
            this.queuedOperationsCount--;
            if (this.queuedOperationsCount == 0) {
                this.queueEmptyFuture.complete(0);
            }
        }
    }

    /**
     * Loads all tables, scans them for all data lineages. The source and target tables are updated in the cache.
     * @param targetKeys A list of target objects that should be analyzed to find all labels.
     */
    public void onRequestLoadLabelsForObjects(List<TableLineageCacheKey> targetKeys) {
        Map<String, List<TableLineageCacheKey>> targetsPerDomain = new LinkedHashMap<>();
        for (TableLineageCacheKey tableRefreshKey : targetKeys) {
            List<TableLineageCacheKey> dataDomainTargetList = targetsPerDomain.get(tableRefreshKey.getDataDomain());
            if (dataDomainTargetList == null) {
                dataDomainTargetList = new ArrayList<>();
                targetsPerDomain.put(tableRefreshKey.getDataDomain(), dataDomainTargetList);
            }
            dataDomainTargetList.add(tableRefreshKey);
        }

        for (List<TableLineageCacheKey> targetsByDataDomain : targetsPerDomain.values()) {
            String dataDomainName = targetsByDataDomain.get(0).getDataDomain();

            try {
                importDataLineageForDataDomain(dataDomainName, targetsByDataDomain);
            }
            catch (Exception ex) {
                log.error("Failed to import data lineage for the data domain \"" + dataDomainName + "\", error: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Loads data linage for target objects from one data domain. Defines also source tables if they are not present.
     * @param dataDomainName Data domain name.
     * @param targetsByDataDomain List of target tables in that data domain.
     */
    public void importDataLineageForDataDomain(String dataDomainName, List<TableLineageCacheKey> targetsByDataDomain) {
        UserDomainIdentity domainIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(dataDomainName);
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(domainIdentity, true);

        for (TableLineageCacheKey targetTableKey : targetsByDataDomain) {
            TableLineageCacheEntry currentTableLineageCacheEntry;

            synchronized (this.lock) {
                currentTableLineageCacheEntry = this.tableCache.get(targetTableKey);
                currentTableLineageCacheEntry.setStatus(TableLineageRefreshStatus.LOADING);
            }

            try {
                ConnectionList connectionList = userHomeContext.getUserHome().getConnections();
                ConnectionWrapper connectionWrapper = connectionList.getByObjectName(targetTableKey.getConnectionName(), true);
                if (connectionWrapper != null && connectionWrapper.getSpec() != null) {
                    TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(targetTableKey.getPhysicalTableName(), true);
                    if (tableWrapper != null && tableWrapper.getSpec() != null) {
                        TableSpec targetTableSpec = tableWrapper.getSpec();
                        Set<TableLineageCacheKey> previousSourceTables = currentTableLineageCacheEntry.getUpstreamSourceTables();
                        Set<TableLineageCacheKey> newSourceTables = new LinkedHashSet<>();

                        TableLineageSourceSpecList sourceTables = targetTableSpec.getSourceTables();
                        if (sourceTables != null) {
                            for (TableLineageSourceSpec sourceTableSpec : sourceTables) {
                                TableLineageCacheKey sourceTableKey = new TableLineageCacheKey(dataDomainName, sourceTableSpec.getSourceConnection(),
                                        new PhysicalTableName(sourceTableSpec.getSourceSchema(), sourceTableSpec.getSourceTable()));
                                newSourceTables.add(sourceTableKey);
                            }
                        }

                        for (TableLineageCacheKey newSourceTableKey : newSourceTables) {
                            if (previousSourceTables.contains(newSourceTableKey)) {
                                continue;  // no changes
                            }

                            currentTableLineageCacheEntry.addUpstreamSourceTable(newSourceTableKey);
                            TableLineageCacheEntry sourceTableLineageCacheEntry;

                            synchronized (this.lock) {
                                sourceTableLineageCacheEntry = this.tableCache.get(newSourceTableKey);
                                if (sourceTableLineageCacheEntry == null) {
                                    sourceTableLineageCacheEntry = new TableLineageCacheEntry(newSourceTableKey, TableLineageRefreshStatus.LOADED); // if it will be loaded, it should be refreshed
                                    this.tableCache.put(newSourceTableKey, sourceTableLineageCacheEntry);
                                }
                            }

                            sourceTableLineageCacheEntry.addDownstreamTargetTable(targetTableKey);
                        }

                        for (TableLineageCacheKey deletedSourceTableKey : previousSourceTables) {
                            if (newSourceTables.contains(deletedSourceTableKey)) {
                                continue;  // no changes
                            }

                            currentTableLineageCacheEntry.removeUpstreamSourceTable(deletedSourceTableKey);
                            TableLineageCacheEntry sourceTableLineageCacheEntry;

                            synchronized (this.lock) {
                                sourceTableLineageCacheEntry = this.tableCache.get(deletedSourceTableKey);
                            }

                            if (sourceTableLineageCacheEntry != null) {
                                sourceTableLineageCacheEntry.removeDownstreamTargetTable(targetTableKey);
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
                log.error("Failed to load data lineage for the target table " + targetTableKey.toString() + ", error: " + ex.getMessage(), ex);
            }
            finally {
                synchronized (this.lock) {
                    currentTableLineageCacheEntry.setStatus(TableLineageRefreshStatus.LOADED);
                }
                decrementAwaitingOperationsCount();
            }
        }
    }

    /**
     * Starts a service that loads and refreshes the data lineage from tables.
     */
    @Override
    public void start() {
        if (this.started) {
            return;
        }
        
        this.started = true;

        this.queueEmptyFuture = new CompletableFuture<>();
        this.queueEmptyFuture.complete(0);

        this.loadObjectRequestSink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<List<TableLineageCacheKey>> requestLoadFlux = this.loadObjectRequestSink.asFlux()
                .onBackpressureBuffer(SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE)
                .buffer(Duration.ofMillis(50))  // wait 50 millis, maybe multiple file system updates are made, we want to merge all file changes
                .publishOn(Schedulers.parallel());
        int concurrency = Runtime.getRuntime().availableProcessors();
        this.subscription = requestLoadFlux
                .flatMap((List<TableLineageCacheKey> targetKeys) -> Mono.just(targetKeys)) // single thread forwarder
                .parallel(concurrency)
                .flatMap((List<TableLineageCacheKey> targetKeys) -> {
                    onRequestLoadLabelsForObjects(targetKeys);
                    return Mono.empty();
                })
                .subscribe();
    }

    /**
     * Stops the data lineage cache service.
     */
    @Override
    public void stop() {
        if (!this.started) {
            return;
        }

        try {
            this.started = false;
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(
                    this.dqoQueueConfigurationProperties.getPublishBusyLoopingDurationSeconds()));
            this.loadObjectRequestSink.emitComplete(emitFailureHandler);
            this.subscription.dispose();
        }
        catch (Exception ex) {
            log.error("Failed to stop the data lineage cache service, error: " + ex.getMessage(), ex);
        }
    }
}
