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

import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Special purpose job that is pushed to the queue. Every worker thread that receives this job will
 * instantly shut down and push the job back to the queue - to kill (poison) other threads and effectively
 * stop the job queue without any delay caused by a delay.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class PoisonDqoJobQueueJob extends DqoQueueJob<Void> {
    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute(DqoJobExecutionContext jobExecutionContext) {
        // do nothing
        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.queue_thread_shutdown;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        return null;
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel();
    }
}
