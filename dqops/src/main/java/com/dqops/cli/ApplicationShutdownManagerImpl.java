/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli;

import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.similarity.TableSimilarityRefreshService;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.labels.labelloader.LabelsIndexer;
import com.dqops.metadata.lineage.lineagecache.TableLineageCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring boot shutdown manager - allows the application to request the shutdown, because the web server must be stopped.
 */
@Component
@Slf4j
public class ApplicationShutdownManagerImpl implements ApplicationShutdownManager {
    private ApplicationContext applicationContext;
    private DqoJobQueue dqoJobQueue;
    private ParentDqoJobQueue parentDqoJobQueue;
    private JobSchedulerService jobSchedulerService;
    private DqoJobQueueMonitoringService jobQueueMonitoringService;
    private TableStatusCache tableStatusCache;
    private LabelsIndexer labelsIndexer;
    private TableLineageCache tableLineageCache;
    private TableSimilarityRefreshService tableSimilarityRefreshService;

    /**
     * Constructor that receives dependencies to services that should be notified to shut down.
     * @param applicationContext Spring application context - used to stop the web server.
     * @param dqoJobQueue Job queue.
     * @param parentDqoJobQueue Second job queue for parent jobs.
     * @param jobSchedulerService Job scheduler.
     * @param jobQueueMonitoringService Job queue monitoring service.
     * @param tableStatusCache Table status cache.
     * @param labelsIndexer Label indexer service that indexes all labels.
     * @param tableLineageCache Table data lineage cache.
     * @param tableSimilarityRefreshService Table similarity refresh service.
     */
    @Autowired
    public ApplicationShutdownManagerImpl(ApplicationContext applicationContext,
                                          DqoJobQueue dqoJobQueue,
                                          ParentDqoJobQueue parentDqoJobQueue,
                                          JobSchedulerService jobSchedulerService,
                                          DqoJobQueueMonitoringService jobQueueMonitoringService,
                                          TableStatusCache tableStatusCache,
                                          LabelsIndexer labelsIndexer,
                                          TableLineageCache tableLineageCache,
                                          TableSimilarityRefreshService tableSimilarityRefreshService) {
        this.applicationContext = applicationContext;
        this.dqoJobQueue = dqoJobQueue;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.jobSchedulerService = jobSchedulerService;
        this.jobQueueMonitoringService = jobQueueMonitoringService;
        this.tableStatusCache = tableStatusCache;
        this.labelsIndexer = labelsIndexer;
        this.tableLineageCache = tableLineageCache;
        this.tableSimilarityRefreshService = tableSimilarityRefreshService;
    }

    /**
     * Initializes an application shutdown, given the application return code to return.
     * @param returnCode Return code.
     */
    @Override
    public void initiateShutdown(int returnCode) {
        if (log.isDebugEnabled()) {
            log.debug("Shutdown initialized with a return code: " + returnCode);
        }
        this.tableSimilarityRefreshService.stop();
        this.labelsIndexer.stop();
        this.tableStatusCache.stop();
        this.tableLineageCache.stop();
        this.dqoJobQueue.stop();
        this.parentDqoJobQueue.stop();
        this.jobQueueMonitoringService.stop();
        this.jobSchedulerService.shutdown();
        SpringApplication.exit(this.applicationContext, () -> returnCode);
    }
}
