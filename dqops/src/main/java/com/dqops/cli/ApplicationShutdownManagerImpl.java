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
package com.dqops.cli;

import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
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

    /**
     * Constructor that receives dependencies to services that should be notified to shut down.
     * @param applicationContext Spring application context - used to stop the web server.
     * @param dqoJobQueue Job queue.
     * @param parentDqoJobQueue Second job queue for parent jobs.
     * @param jobSchedulerService Job scheduler.
     * @param jobQueueMonitoringService Job queue monitoring service.
     * @param tableStatusCache Table status cache.
     */
    @Autowired
    public ApplicationShutdownManagerImpl(ApplicationContext applicationContext,
                                          DqoJobQueue dqoJobQueue,
                                          ParentDqoJobQueue parentDqoJobQueue,
                                          JobSchedulerService jobSchedulerService,
                                          DqoJobQueueMonitoringService jobQueueMonitoringService,
                                          TableStatusCache tableStatusCache) {
        this.applicationContext = applicationContext;
        this.dqoJobQueue = dqoJobQueue;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.jobSchedulerService = jobSchedulerService;
        this.jobQueueMonitoringService = jobQueueMonitoringService;
        this.tableStatusCache = tableStatusCache;
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
        this.tableStatusCache.stop();
        this.dqoJobQueue.stop();
        this.parentDqoJobQueue.stop();
        this.jobQueueMonitoringService.stop();
        this.jobSchedulerService.shutdown();
        SpringApplication.exit(this.applicationContext, () -> returnCode);
    }
}
