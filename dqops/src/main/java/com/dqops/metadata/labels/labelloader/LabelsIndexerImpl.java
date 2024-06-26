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

package com.dqops.metadata.labels.labelloader;

import com.dqops.core.configuration.DqoCacheConfigurationProperties;
import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.labels.labelcontainers.GlobalLabelsContainer;
import com.dqops.metadata.labels.labelcontainers.LabelCountContainer;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service that receives notifications about loaded or updated connections and tables, and imports labels into a global container. 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class LabelsIndexerImpl implements LabelsIndexer {
    public static int SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE = 1000000; // the number of awaiting operations in the backpressure buffer (queue)

    private final Cache<LabelRefreshKey, LabelsLoadEntry> ongoingOperations;
    private final DqoCacheConfigurationProperties dqoCacheConfigurationProperties;
    private final DqoQueueConfigurationProperties dqoQueueConfigurationProperties;
    private final UserHomeContextFactory userHomeContextFactory;
    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private final GlobalLabelsContainer globalLabelsContainer;
    private boolean started;
    private Sinks.Many<LabelRefreshKey> loadObjectRequestSink;
    private Disposable subscription;
    private Sinks.EmitFailureHandler emitFailureHandlerPublisher;
    private int queuedOperationsCount;
    private final Object lock = new Object();
    private CompletableFuture<Integer> queueEmptyFuture;

    /**
     * Creates a new instance of the label indexer, configuring the cache size.
     * @param dqoCacheConfigurationProperties Cache configuration parameters.
     * @param dqoQueueConfigurationProperties Queue configuration parameters - to configure backpressure.
     * @param userHomeContextFactory User home context factory to load the user home.
     * @param userDomainIdentityFactory User domain identity for the admin user that identifies the correct data domain.
     * @param globalLabelsContainer Global labels container where the labels are pushed.
     */
    @Autowired
    public LabelsIndexerImpl(DqoCacheConfigurationProperties dqoCacheConfigurationProperties,
                             DqoQueueConfigurationProperties dqoQueueConfigurationProperties,
                             UserHomeContextFactory userHomeContextFactory,
                             UserDomainIdentityFactory userDomainIdentityFactory,
                             GlobalLabelsContainer globalLabelsContainer) {
        this.ongoingOperations = Caffeine.newBuilder()
                .maximumSize(dqoCacheConfigurationProperties.getYamlFilesLimit()) // TODO: Separate a different parameter
                .expireAfterWrite(12 * 3600, TimeUnit.SECONDS)
                .build();
        this.dqoCacheConfigurationProperties = dqoCacheConfigurationProperties;
        this.dqoQueueConfigurationProperties = dqoQueueConfigurationProperties;
        this.userHomeContextFactory = userHomeContextFactory;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
        this.globalLabelsContainer = globalLabelsContainer;
        this.emitFailureHandlerPublisher = Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(
                this.dqoQueueConfigurationProperties.getPublishBusyLoopingDurationSeconds()));
        this.queueEmptyFuture = new CompletableFuture<>();
        this.queueEmptyFuture.complete(0);
    }

    /**
     * The operation that is called to request loading labels for a new entry (connection or table).
     * @param targetKey Entry key.
     * @return Current entry instance.
     */
    protected LabelsLoadEntry loadEntryCore(LabelRefreshKey targetKey) {
        LabelsLoadEntry labelsLoadEntry = new LabelsLoadEntry(targetKey, LabelRefreshStatus.LOADING_QUEUED);
        if (this.loadObjectRequestSink != null) {
            this.loadObjectRequestSink.emitNext(targetKey, this.emitFailureHandlerPublisher);
            incrementAwaitingOperationsCount();
        }
        return labelsLoadEntry;
    }

    /**
     * Notifies the label indexer that a connection or a table yaml files were updated (or loaded into the cache) and should be scanned to load labels.
     * @param targetLabelsKey The target key that identifies an object that should be scanned for labels.
     * @param replacingCachedFile True when we are replacing a file that was already in a file system cache, false when a file is just placed into a cache,
     *                            and it is not a real invalidation, but just a notification that a file was just cached.
     */
    @Override
    public void invalidateObject(LabelRefreshKey targetLabelsKey, boolean replacingCachedFile) {
        LabelsLoadEntry currentTableStatusCacheEntry = this.ongoingOperations.get(targetLabelsKey, this::loadEntryCore);
        LabelRefreshStatus currentEntryStatus = currentTableStatusCacheEntry.getStatus();

        if (currentEntryStatus == LabelRefreshStatus.LOADING_QUEUED || currentEntryStatus == LabelRefreshStatus.REFRESH_QUEUED) {
            return; // another refresh was already queued
        }

        if (currentEntryStatus == LabelRefreshStatus.LOADING && !replacingCachedFile) {
            return; // this refresh was triggered by this class when it was loading yaml files and they were cached for the first time, it is not a change to a file
        }

        currentTableStatusCacheEntry.setStatus(LabelRefreshStatus.REFRESH_QUEUED);
        this.loadObjectRequestSink.emitNext(targetLabelsKey, this.emitFailureHandlerPublisher);
        incrementAwaitingOperationsCount();
    }

    /**
     * Returns a future that is completed when there are no queued label load reload operations.
     * @param waitTimeoutMilliseconds Optional timeout to wait for the completion of the future. If the timeout elapses, the future is completed with a value <code>false</code>.
     * @return Future that is completed when the labels from all requested tables and connections were loaded.
     */
    @Override
    public CompletableFuture<Boolean> getQueueEmptyFuture(Long waitTimeoutMilliseconds) {
        synchronized (this.lock) {
            CompletableFuture<Boolean> booleanCompletableFuture = this.queueEmptyFuture
                    .thenApply(result -> true);

            if (waitTimeoutMilliseconds != null) {
                booleanCompletableFuture = booleanCompletableFuture.completeOnTimeout(false, waitTimeoutMilliseconds, TimeUnit.MILLISECONDS);

//                CompletableFuture<Boolean> timeoutFuture = new CompletableFuture<Boolean>().completeOnTimeout(false, waitTimeoutMilliseconds, TimeUnit.MILLISECONDS);
//                booleanCompletableFuture = (CompletableFuture<Boolean>)(CompletableFuture<?>)CompletableFuture.anyOf(booleanCompletableFuture, timeoutFuture);

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
     * Loads all tables and connections, scans them for all labels. The labels are updated in the global labels container.
     * @param targetKeys A list of target objects that should be analyzed to find all labels.
     */
    public void onRequestLoadLabelsForObjects(List<LabelRefreshKey> targetKeys) {
        Map<String, List<LabelRefreshKey>> targetsPerDomain = new LinkedHashMap<>();
        for (LabelRefreshKey labelRefreshKey : targetKeys) {
            List<LabelRefreshKey> dataDomainTargetList = targetsPerDomain.get(labelRefreshKey.getDataDomain());
            if (dataDomainTargetList == null) {
                dataDomainTargetList = new ArrayList<>();
                targetsPerDomain.put(labelRefreshKey.getDataDomain(), dataDomainTargetList);
            }
            dataDomainTargetList.add(labelRefreshKey);
        }

        for (List<LabelRefreshKey> targetsByDataDomain : targetsPerDomain.values()) {
            String dataDomainName = targetsByDataDomain.get(0).getDataDomain();

            try {
                importLabelsForDataDomain(dataDomainName, targetsByDataDomain);
            }
            catch (Exception ex) {
                log.error("Failed to import labels for the data domain \"" + dataDomainName + "\", error: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Loads labels for target objects (connections and tables) from one data domain.
     * @param dataDomainName Data domain name.
     * @param targetsByDataDomain List of targets (connections and tables) in that data domain.
     */
    public void importLabelsForDataDomain(String dataDomainName, List<LabelRefreshKey> targetsByDataDomain) {
        UserDomainIdentity domainIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForCloudDomain(dataDomainName);
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(domainIdentity, true);

        for (LabelRefreshKey targetKey : targetsByDataDomain) {
            LabelsLoadEntry currentEntry = this.ongoingOperations.get(targetKey, this::loadEntryCore);

            try {
                currentEntry.setStatus(LabelRefreshStatus.LOADING);

                switch (targetKey.getTarget()) {
                    case CONNECTION: {
                        importConnectionLabels(dataDomainName, userHomeContext.getUserHome(), targetKey.getConnectionName());
                        break;
                    }
                    case TABLE: {
                        importTableLabels(dataDomainName, userHomeContext.getUserHome(), targetKey.getConnectionName(), targetKey.getPhysicalTableName());
                        break;
                    }
                }

            }
            catch (Exception ex) {
                log.error("Failed to labels for the target " + targetKey.toString() + ", error: " + ex.getMessage(), ex);
            }
            finally {
                currentEntry.setStatus(LabelRefreshStatus.LOADED);
                decrementAwaitingOperationsCount();
            }
        }
    }

    /**
     * Gathers labels from a connection and imports them to the global container.
     * @param dataDomainName Data domain name.
     * @param userHome User home where the connection specification is stored.
     * @param connectionName Connection name.
     */
    public void importConnectionLabels(String dataDomainName, UserHome userHome, String connectionName) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            // deleted
            this.globalLabelsContainer.importConnectionLabels(dataDomainName, connectionName, null);
            return;
        }

        LabelSetSpec labels = connectionWrapper.getSpec().getLabels();
        if (labels == null) {
            this.globalLabelsContainer.importConnectionLabels(dataDomainName, connectionName, null);
            return;
        }

        LabelCountContainer labelsContainer = new LabelCountContainer();
        labelsContainer.importLabelSet(labels);
        this.globalLabelsContainer.importConnectionLabels(dataDomainName, connectionName, labelsContainer);
    }

    /**
     * Gathers labels from a table (and columns) and imports them to the global container.
     * @param dataDomainName Data domain name.
     * @param userHome User home where the table specification is stored.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     */
    public void importTableLabels(String dataDomainName, UserHome userHome, String connectionName, PhysicalTableName physicalTableName) {
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            // the connection is missing, so the table is also missing
            this.globalLabelsContainer.importTableLabels(dataDomainName, connectionName, physicalTableName, null);
            this.globalLabelsContainer.importColumnLabels(dataDomainName, connectionName, physicalTableName, null);
            return;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
            // the table is missing (deleted)
            this.globalLabelsContainer.importTableLabels(dataDomainName, connectionName, physicalTableName, null);
            this.globalLabelsContainer.importColumnLabels(dataDomainName, connectionName, physicalTableName, null);
            return;
        }

        LabelSetSpec tableLabels = tableWrapper.getSpec().getLabels();
        if (tableLabels == null) {
            this.globalLabelsContainer.importTableLabels(dataDomainName, connectionName, physicalTableName, null);
        } else {
            LabelCountContainer tableLabelsContainer = new LabelCountContainer();
            tableLabelsContainer.importLabelSet(tableLabels);
            this.globalLabelsContainer.importTableLabels(dataDomainName, connectionName, physicalTableName, tableLabelsContainer);
        }

        LabelCountContainer columnLabelsContainer = new LabelCountContainer();
        for (ColumnSpec columnSpec : tableWrapper.getSpec().getColumns().values()) {
            LabelSetSpec columnLabelsSet = columnSpec.getLabels();
            columnLabelsContainer.importLabelSet(columnLabelsSet);
        }

        if (columnLabelsContainer.isEmpty()) {
            columnLabelsContainer = null;
        }

        this.globalLabelsContainer.importColumnLabels(dataDomainName, connectionName, physicalTableName, columnLabelsContainer);
    }

    /**
     * Starts a service that loads and refreshes the labels from connections and tables.
     */
    @Override
    public void start() {
        if (this.started) {
            return;
        }
        
        this.started = true;

        this.queueEmptyFuture = new CompletableFuture<>();
        this.queueEmptyFuture.complete(0);

        this.loadObjectRequestSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<LabelRefreshKey>> requestLoadFlux = this.loadObjectRequestSink.asFlux()
                .onBackpressureBuffer(SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE)
                .buffer(Duration.ofMillis(50))  // wait 50 millis, maybe multiple file system updates are made, we want to merge all file changes
                .publishOn(Schedulers.parallel());
        this.subscription = requestLoadFlux
                .doOnNext((List<LabelRefreshKey> targetKeys) -> onRequestLoadLabelsForObjects(targetKeys))
                .subscribe();
    }

    /**
     * Stops the labels indexer service.
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
            log.error("Failed to stop the labels indexer service, error: " + ex.getMessage(), ex);
        }
    }
}
