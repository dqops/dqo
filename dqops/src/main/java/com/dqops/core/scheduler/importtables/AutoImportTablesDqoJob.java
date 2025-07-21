/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.importtables;

import com.dqops.core.configuration.DqoMetadataImportConfigurationProperties;
import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJob;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import com.dqops.core.jobqueue.jobs.table.ImportTablesResult;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.sources.AutoImportTablesSpec;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DQOps job that imports tables on connections matching a CRON schedule within the job scheduler (quartz).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AutoImportTablesDqoJob extends ParentDqoQueueJob<Integer> {
    private JobSchedulerService jobSchedulerService;
    private ExecutionContextFactory executionContextFactory;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private DqoMetadataImportConfigurationProperties dqoMetadataImportConfigurationProperties;
    private CronScheduleSpec cronSchedule;

    /**
     * Creates a dqo job that runs checks scheduled for one cron expression.
     * @param jobSchedulerService Job scheduler service - used to ask for the reporting mode.
     * @param executionContextFactory Check execution context that will create a context - opening the local user home.
     * @param dqoQueueJobFactory Job factory to create table import jobs.
     * @param dqoJobQueue DQO job queue to push connection level jobs.
     * @param dqoMetadataImportConfigurationProperties Metadata import configuration parameters to read the tables import limit.
     */
    @Autowired
    public AutoImportTablesDqoJob(JobSchedulerService jobSchedulerService,
                                  ExecutionContextFactory executionContextFactory,
                                  DqoQueueJobFactory dqoQueueJobFactory,
                                  DqoJobQueue dqoJobQueue,
                                  DqoMetadataImportConfigurationProperties dqoMetadataImportConfigurationProperties) {
        this.jobSchedulerService = jobSchedulerService;
        this.executionContextFactory = executionContextFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.dqoMetadataImportConfigurationProperties = dqoMetadataImportConfigurationProperties;
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
    public Integer onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        UserDomainIdentity userDomainIdentity = this.getPrincipal().getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, true);

        ConnectionList connectionList = executionContext.getUserHomeContext().getUserHome().getConnections();
        int connectionsCount = 0;

        List<DqoQueueJob<ImportTablesResult>> childConnectionImportJobs = new ArrayList<>();

        for (ConnectionWrapper connectionWrapper : connectionList) {
            if (connectionWrapper.getSpec() == null || connectionWrapper.getSpec().getAutoImportTables() == null ||
                    connectionWrapper.getSpec().getAutoImportTables().getSchedule() == null ||
                    connectionWrapper.getSpec().getAutoImportTables().getSchedule().isDisabled()) {
                continue;
            }

            AutoImportTablesSpec autoImportTables = connectionWrapper.getSpec().getAutoImportTables();
            String connectionCronExpression = autoImportTables.getSchedule().getCronExpression();
            if (!Objects.equals(connectionCronExpression, this.cronSchedule.getCronExpression())) {
                continue;
            }

            jobExecutionContext.getCancellationToken().throwIfCancelled();
            // import tables for each connection
            ImportTablesQueueJob importTablesJob = this.dqoQueueJobFactory.createImportTablesJob();
            ImportTablesQueueJobParameters importParameters = new ImportTablesQueueJobParameters();
            importParameters.setConnectionName(connectionWrapper.getName());
            importParameters.setSchemaName(autoImportTables.getSchemaFilter());
            importParameters.setTableNameContains(autoImportTables.getTableNameContains());
            importParameters.setTablesImportLimit(this.dqoMetadataImportConfigurationProperties.getAutoImportTablesLimit());
            importTablesJob.setImportParameters(importParameters);

            childConnectionImportJobs.add(importTablesJob);

            connectionsCount++;
        }

        ChildDqoQueueJobsContainer<ImportTablesResult> childConnectionJobsContainer =
                this.dqoJobQueue.pushChildJobs(childConnectionImportJobs, this.getJobId(), this.getPrincipal());
        List<ImportTablesResult> tableImportSummaries = childConnectionJobsContainer.waitForChildResults(jobExecutionContext.getCancellationToken());

        return connectionsCount;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.auto_import_tables;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        return null; // user can start any number of "import tables" operations, the concurrency will be applied later on a connection level
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
           setAutoImportTablesParameters(cronSchedule);
        }};
    }
}
