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

package com.dqops.data.checkresults.statuscache;

import com.dqops.core.configuration.DqoCacheConfigurationProperties;
import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityFactory;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.checkresults.services.models.currentstatus.TableCurrentDataQualityStatusFilterParameters;
import com.dqops.data.checkresults.services.models.currentstatus.TableCurrentDataQualityStatusModel;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Service that keeps a cache of last known table statuses. It starts loading a table status when
 * the table status is requested. This service reloads the last known table status when the parquet files are updated.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class TableStatusCacheImpl implements TableStatusCache {
    public static int SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE = 1000000; // the number of awaiting operations in the backpressure buffer (queue)

    private final Cache<CurrentTableStatusKey, CurrentTableStatusCacheEntry> tableStatusCache;
    private final DqoCacheConfigurationProperties dqoCacheConfigurationProperties;
    private final DqoQueueConfigurationProperties dqoQueueConfigurationProperties;
    private final CheckResultsDataService checkResultsDataService;
    private final UserDomainIdentityFactory userDomainIdentityFactory;
    private boolean started;
    private Sinks.Many<CurrentTableStatusKey> loadTableStatusRequestSink;
    private Disposable subscription;
    private Sinks.EmitFailureHandler emitFailureHandlerPublisher;

    /**
     * Creates a new instance of the cache, configuring the cache size.
     * @param dqoCacheConfigurationProperties Cache configuration parameters.
     * @param dqoQueueConfigurationProperties Queue configuration parameters - to configure backpressure.
     * @param checkResultsDataService Data quality check data service to load the current status.
     * @param userDomainIdentityFactory User domain identity for the admin user that identifies the correct data domain.
     */
    @Autowired
    public TableStatusCacheImpl(DqoCacheConfigurationProperties dqoCacheConfigurationProperties,
                                DqoQueueConfigurationProperties dqoQueueConfigurationProperties,
                                CheckResultsDataService checkResultsDataService,
                                UserDomainIdentityFactory userDomainIdentityFactory) {
        this.tableStatusCache = Caffeine.newBuilder()
                .maximumSize(dqoCacheConfigurationProperties.getYamlFilesLimit()) // TODO: Separate a different parameter
                .expireAfterWrite(dqoCacheConfigurationProperties.getExpireAfterSeconds(), TimeUnit.SECONDS) // TODO: Separate a different parameter
                .build();
        this.dqoCacheConfigurationProperties = dqoCacheConfigurationProperties;
        this.dqoQueueConfigurationProperties = dqoQueueConfigurationProperties;
        this.checkResultsDataService = checkResultsDataService;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
        this.emitFailureHandlerPublisher = Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(
                this.dqoQueueConfigurationProperties.getPublishBusyLoopingDurationSeconds()));
    }

    /**
     * The operation that is called to create a new table entry for the cache and queue a table load operation.
     * @param tableStatusKey Table status key.
     * @return Table status cache entry.
     */
    protected CurrentTableStatusCacheEntry loadEntryCore(CurrentTableStatusKey tableStatusKey) {
        CurrentTableStatusCacheEntry currentTableStatusCacheEntry = new CurrentTableStatusCacheEntry(tableStatusKey, CurrentTableStatusEntryStatus.LOADING_QUEUED);
        this.loadTableStatusRequestSink.emitNext(tableStatusKey, this.emitFailureHandlerPublisher);
        return currentTableStatusCacheEntry;
    }

    /**
     * Retrieves the current table status for a requested table.
     * @param tableStatusKey Table status key.
     * @return Table status model or null when it is not yet loaded.
     */
    @Override
    public TableCurrentDataQualityStatusModel getCurrentTableStatus(CurrentTableStatusKey tableStatusKey) {
        CurrentTableStatusCacheEntry currentTableStatusCacheEntry = this.tableStatusCache.get(tableStatusKey, this::loadEntryCore);
        return currentTableStatusCacheEntry.getStatusModel();
    }

    /**
     * Notifies the table status cache that the table result were updated and should be invalidated.
     * @param tableStatusKey Table status key.
     */
    @Override
    public void invalidateTableStatus(CurrentTableStatusKey tableStatusKey) {
        CurrentTableStatusCacheEntry currentTableStatusCacheEntry = this.tableStatusCache.get(tableStatusKey, this::loadEntryCore);
        if (currentTableStatusCacheEntry.getStatus() == CurrentTableStatusEntryStatus.LOADING_QUEUED ||
                currentTableStatusCacheEntry.getStatus() == CurrentTableStatusEntryStatus.REFRESH_QUEUED) {
            return; // another refresh was already queued
        }

        currentTableStatusCacheEntry.setStatus(CurrentTableStatusEntryStatus.REFRESH_QUEUED);
        this.loadTableStatusRequestSink.emitNext(tableStatusKey, this.emitFailureHandlerPublisher);
    }

    /**
     * Loads a table status or refreshes a table status.
     * @param tableStatusKey Table status key.
     */
    public void onRequestLoadTableStatus(CurrentTableStatusKey tableStatusKey) {
        CurrentTableStatusCacheEntry currentTableStatusCacheEntry = this.tableStatusCache.get(tableStatusKey, this::loadEntryCore);

        currentTableStatusCacheEntry.setStatus(CurrentTableStatusEntryStatus.LOADING);

        try {
            UserDomainIdentity userDomainIdentity = this.userDomainIdentityFactory.createDataDomainAdminIdentityForLocalDomain(tableStatusKey.getDataDomain());

            TableCurrentDataQualityStatusFilterParameters filterParameters =
                TableCurrentDataQualityStatusFilterParameters.builder()
                    .connectionName(tableStatusKey.getConnectionName())
                    .physicalTableName(tableStatusKey.getPhysicalTableName())
                    .profiling(true)
                    .monitoring(true)
                    .partitioned(true)
                    .build();

            TableCurrentDataQualityStatusModel tableCurrentDataQualityStatusModel =
                    this.checkResultsDataService.analyzeTableMostRecentQualityStatus(filterParameters, userDomainIdentity);
            currentTableStatusCacheEntry.setStatusModel(tableCurrentDataQualityStatusModel); // also sets the status
        }
        catch (Exception ex) {
            currentTableStatusCacheEntry.setStatus(CurrentTableStatusEntryStatus.LOADED);
            log.error("Failed to load the current table status for the table " + tableStatusKey.toString() + ", error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Starts a service that loads table statuses of requested tables.
     */
    @Override
    public void start() {
        if (this.started) {
            return;
        }
        
        this.started = true;

        this.loadTableStatusRequestSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<CurrentTableStatusKey>> requestLoadFlux = this.loadTableStatusRequestSink.asFlux()
                .onBackpressureBuffer(SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE)
                .buffer(Duration.ofMillis(10)); // wait 10 millis, maybe multiple file system updates are made, like changing multiple parquet files... we want to merge all file changes
        this.subscription = requestLoadFlux.subscribeOn(Schedulers.parallel())
                .parallel()
                .flatMap(list -> Flux.fromIterable(list))
                .subscribe(tableKey -> onRequestLoadTableStatus(tableKey));
    }

    /**
     * Stops a table current status loader cache.
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
            this.loadTableStatusRequestSink.emitComplete(emitFailureHandler);
            this.subscription.dispose();
        }
        catch (Exception ex) {
            log.error("Failed to stop the table status cache, error: " + ex.getMessage(), ex);
        }
    }
}