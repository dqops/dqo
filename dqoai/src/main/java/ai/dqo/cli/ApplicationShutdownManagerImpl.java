/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli;

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.scheduler.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring boot shutdown manager - allows the application to request the shutdown, because the web server must be stopped.
 */
@Component
public class ApplicationShutdownManagerImpl implements ApplicationShutdownManager {
    private ApplicationContext applicationContext;
    private DqoJobQueue dqoJobQueue;
    private JobSchedulerService jobSchedulerService;

    /**
     * Constructor that receives dependencies to services that should be notified to shutdown.
     * @param applicationContext Spring application context - used to stop the web server.
     * @param dqoJobQueue Job queue.
     * @param jobSchedulerService Job scheduler.
     */
    @Autowired
    public ApplicationShutdownManagerImpl(ApplicationContext applicationContext,
                                          DqoJobQueue dqoJobQueue,
                                          JobSchedulerService jobSchedulerService) {
        this.applicationContext = applicationContext;
        this.dqoJobQueue = dqoJobQueue;
        this.jobSchedulerService = jobSchedulerService;
    }

    /**
     * Initializes an application shutdown, given the application return code to return.
     * @param returnCode Return code.
     */
    @Override
    public void initiateShutdown(int returnCode) {
        this.dqoJobQueue.stop();
        jobSchedulerService.shutdown();
        SpringApplication.exit(this.applicationContext, () -> returnCode);
    }
}
