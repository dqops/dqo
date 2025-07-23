/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.quartz;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Default quartz job listener that is logging the job executions.
 */
@Component
public class ScheduledJobListener implements JobListener {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledJobListener.class);

    @Override
    public String getName() {
        return "DQOps Job Listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String jobName = jobDetail.getKey().getName();
        LOG.debug("Job is about to be executed: " + jobName);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String jobName = jobDetail.getKey().getName();
        if (e == null) {
            LOG.debug("Job was executed successfully: " + jobName);
        }
        else {
            LOG.error("Job " + jobName + " failed to execute, error: " + e.getMessage(), e);
        }
    }
}
