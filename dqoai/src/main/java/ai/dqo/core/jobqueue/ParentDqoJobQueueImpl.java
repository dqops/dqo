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

package ai.dqo.core.jobqueue;

import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.jobqueue.concurrency.DqoJobConcurrencyLimiter;
import ai.dqo.core.jobqueue.concurrency.ParallelJobLimitProvider;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The implementation of the parent DQO job queue that runs only {@link ParentDqoQueueJob} parent jobs. All other jobs will be rejected.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ParentDqoJobQueueImpl extends BaseDqoJobQueueImpl implements ParentDqoJobQueue {
    /**
     * Creates a new dqo queue job.
     * @param queueConfigurationProperties dqo.cloud.* configuration parameters.
     * @param jobConcurrencyLimiter Job concurrency limiter.
     * @param parallelJobLimitProvider Dependency to a service that retrieves the parallel threads limit that is supported.
     * @param dqoJobIdGenerator Job ID generator.
     * @param queueMonitoringService Queue monitoring service.
     */
    public ParentDqoJobQueueImpl(DqoQueueConfigurationProperties queueConfigurationProperties,
                                 ParallelJobLimitProvider parallelJobLimitProvider,
                                 DqoJobConcurrencyLimiter jobConcurrencyLimiter,
                                 DqoJobIdGenerator dqoJobIdGenerator,
                                 DqoJobQueueMonitoringService queueMonitoringService) {
        super(queueConfigurationProperties, parallelJobLimitProvider, jobConcurrencyLimiter, dqoJobIdGenerator, queueMonitoringService);
    }

    /**
     * Pushes a job to the job queue without waiting.
     *
     * @param job Job to be pushed.
     * @return Started job summary and a future to await for finish.
     */
    @Override
    public <T> PushJobResult<T> pushJob(ParentDqoQueueJob<T> job) {
        return super.pushJobCore(job);
    }
}
