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

package com.dqops.core.jobqueue;

import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.jobqueue.concurrency.DqoJobConcurrencyLimiter;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProvider;
import com.dqops.core.jobqueue.exceptions.DqoJobQueuePushFailedException;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.principal.DqoUserPrincipal;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * The implementation of the main DQOps job queue that runs independent jobs and child jobs, but will reject all {@link ParentDqoQueueJob} parent jobs.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoJobQueueImpl extends BaseDqoJobQueueImpl implements DqoJobQueue {
    /**
     * Creates a new dqo queue job.
     * @param queueConfigurationProperties dqo.cloud.* configuration parameters.
     * @param jobConcurrencyLimiter Job concurrency limiter.
     * @param parallelJobLimitProvider Dependency to a service that retrieves the parallel threads limit that is supported.
     * @param dqoJobIdGenerator Job ID generator.
     * @param queueMonitoringService Queue monitoring service.
     */
    public DqoJobQueueImpl(DqoQueueConfigurationProperties queueConfigurationProperties,
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
    public <T> PushJobResult<T> pushJob(DqoQueueJob<T> job, DqoUserPrincipal principal) {
        if (job instanceof ParentDqoQueueJob) {
            throw new DqoJobQueuePushFailedException("Parent jobs must be pushed to the ParentDqoJobQueue only.");
        }

        return super.pushJobCore(job, null, principal);
    }

    /**
     * Pushes a job to the job queue without waiting, storing also a reference to the parent job.
     *
     * @param job Job to be pushed.
     * @param parentJobId Parent job id.
     * @param principal Principal that will be used to run the job.
     * @return Started job summary and a future to await for finish.
     */
    @Override
    public <T> PushJobResult<T> pushJob(DqoQueueJob<T> job, DqoQueueJobId parentJobId, DqoUserPrincipal principal) {
        if (job instanceof ParentDqoQueueJob) {
            throw new DqoJobQueuePushFailedException("Parent jobs must be pushed to the ParentDqoJobQueue only.");
        }

        return super.pushJobCore(job, parentJobId, principal);
    }

    /**
     * Pushes a collection of child jobs.
     * @param childJobs Collection of child jobs.
     * @param parentJobId Parent job id.
     * @param principal Principal that will be used to run the job.
     * @return Child jobs container that will track the completion of all child jobs.
     * @param <T> Child job result type.
     */
    @Override
    public <T> ChildDqoQueueJobsContainer<T> pushChildJobs(Collection<DqoQueueJob<T>> childJobs, DqoQueueJobId parentJobId, DqoUserPrincipal principal) {
        assert parentJobId != null;
        ChildDqoQueueJobsContainer<T> childDqoQueueJobsContainer = new ChildDqoQueueJobsContainer<T>(childJobs);
        if (childJobs.isEmpty()) {
            return childDqoQueueJobsContainer;
        }
        int maxDegreeOfParallelism = this.parallelJobLimitProvider.getMaxDegreeOfParallelism();

        BiConsumer<DqoQueueJob<?>, DqoJobCompletionStatus> childJobFinishedHandler =
                (DqoQueueJob<?> job, DqoJobCompletionStatus completionStatus) ->
        {
            DqoQueueJob<T> finishedJob = (DqoQueueJob<T>) job;
            DqoQueueJob<T> nextJobToSchedule = childDqoQueueJobsContainer.notifyChildJobFinished(finishedJob, completionStatus);
            if (nextJobToSchedule != null) {
                this.pushJob(nextJobToSchedule, parentJobId, principal);
            }
        };

        for (DqoQueueJob<T> childJob : childDqoQueueJobsContainer.getJobs()) {
            childJob.registerOnFinishedCallback(childJobFinishedHandler);
        }

        for (int i = 0; i < maxDegreeOfParallelism; i++) {
            DqoQueueJob<T> nextChildJob = childDqoQueueJobsContainer.pullNextJob();
            if (nextChildJob == null) {
                break;
            }

            assert !(nextChildJob instanceof ParentDqoQueueJob);
            this.pushJob(nextChildJob, parentJobId, principal);
        }

        return childDqoQueueJobsContainer;
    }
}
