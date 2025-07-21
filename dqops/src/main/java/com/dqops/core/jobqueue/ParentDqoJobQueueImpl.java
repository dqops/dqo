/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.jobqueue;

import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.jobqueue.concurrency.DqoJobConcurrencyLimiter;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProvider;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.principal.DqoUserPrincipal;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The implementation of the parent DQOps job queue that runs only {@link ParentDqoQueueJob} parent jobs. All other jobs will be rejected.
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
     * @param principal Principal that will be used to run the job.
     * @return Started job summary and a future to await for finish.
     */
    @Override
    public <T> PushJobResult<T> pushJob(ParentDqoQueueJob<T> job, DqoUserPrincipal principal) {
        return super.pushJobCore(job, null, principal);
    }
}
