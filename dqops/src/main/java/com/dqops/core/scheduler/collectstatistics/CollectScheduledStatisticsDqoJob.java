/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.collectstatistics;

import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.ParentDqoQueueJob;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;
import com.dqops.execution.statistics.StatisticsCollectorsExecutionService;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListenerProvider;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQOps job that collects scheduled statistics for one CRON schedule within the job scheduler (quartz).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CollectScheduledStatisticsDqoJob extends ParentDqoQueueJob<StatisticsCollectionExecutionSummary> {
    private JobSchedulerService jobSchedulerService;
    private StatisticsCollectorsExecutionService statisticsCollectorsExecutionService;
    private ExecutionContextFactory executionContextFactory;
    private StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider;
    private CronScheduleSpec cronSchedule;

    /**
     * Creates a dqo job that runs checks scheduled for one cron expression.
     * @param jobSchedulerService Job scheduler service - used to ask for the reporting mode.
     * @param statisticsCollectorsExecutionService Statistics collection service that performs statistics collection (profiling).
     * @param executionContextFactory Check execution context that will create a context - opening the local user home.
     * @param statisticsCollectorExecutionProgressListenerProvider Statistics job execution listener provider to create a job listener.
     */
    @Autowired
    public CollectScheduledStatisticsDqoJob(JobSchedulerService jobSchedulerService,
                                            StatisticsCollectorsExecutionService statisticsCollectorsExecutionService,
                                            ExecutionContextFactory executionContextFactory,
                                            StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider) {
        this.jobSchedulerService = jobSchedulerService;
        this.statisticsCollectorsExecutionService = statisticsCollectorsExecutionService;
        this.executionContextFactory = executionContextFactory;
        this.statisticsCollectorExecutionProgressListenerProvider = statisticsCollectorExecutionProgressListenerProvider;
    }

    /**
     * Cron schedule (cron expression and time zone) that is executed.
     * @return Cron schedule.
     */
    public CronScheduleSpec getCronSchedule() {
        return cronSchedule;
    }

    /**
     * Sets the cron schedule that is triggered and whose checks are executed.
     * @param cronSchedule Cron schedule.
     */
    public void setCronSchedule(CronScheduleSpec cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public StatisticsCollectionExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        StatisticsCollectorExecutionReportingMode reportingMode = this.jobSchedulerService.getCollectStatisticsReportingMode();

        UserDomainIdentity userDomainIdentity = this.getPrincipal().getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);

        StatisticsCollectorSearchFilters statisticsCollectorSearchFilters = new StatisticsCollectorSearchFilters();
        statisticsCollectorSearchFilters.setEnabledCronScheduleExpression(this.cronSchedule.getCronExpression());

        StatisticsCollectorExecutionProgressListener progressListener =
                this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(reportingMode,
                        reportingMode != StatisticsCollectorExecutionReportingMode.silent);

        StatisticsCollectionExecutionSummary statisticsCollectionExecutionSummary = this.statisticsCollectorsExecutionService.executeStatisticsCollectors(
                executionContext,
                statisticsCollectorSearchFilters,
                progressListener,
                StatisticsDataScope.table,
                null,
                false,
                false,
                true,
                jobExecutionContext.getJobId(),
                jobExecutionContext.getCancellationToken(),
                this.getPrincipal());

        return statisticsCollectionExecutionSummary;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.collect_scheduled_statistics;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        return null; // user can start any number of "run check" operations, the concurrency will be applied later on a table level
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
           setCollectScheduledStatisticsParameters(cronSchedule);
        }};
    }
}
