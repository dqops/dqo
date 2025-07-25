/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks;

import com.dqops.core.jobqueue.*;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.jobs.RunChecksOnTableQueueJob;
import com.dqops.execution.checks.jobs.RunChecksOnTableParameters;
import com.dqops.execution.checks.progress.*;
import com.dqops.execution.checks.scheduled.ScheduledChecksCollection;
import com.dqops.execution.checks.scheduled.ScheduledTableChecksCollection;
import com.dqops.execution.checks.scheduled.ScheduledTargetChecksFindService;
import com.dqops.execution.sensors.*;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service that executes data quality checks.
 */
@Service
@Slf4j
public class CheckExecutionServiceImpl implements CheckExecutionService {
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private final DqoJobQueue dqoJobQueue;
    private final ScheduledTargetChecksFindService scheduledTargetChecksFindService;
    private final TableCheckExecutionService tableCheckExecutionService;

    /**
     * Creates a data quality check execution service.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher.
     * @param dqoQueueJobFactory DQOps job factory, used to create child jobs that will run checks per table.
     * @param dqoJobQueue DQOps job queue where child jobs (that run checks per table) are scheduled.
     * @param scheduledTargetChecksFindService Service that finds matching checks that are assigned to a given schedule.
     * @param tableCheckExecutionService Check execution service that executes checks on a single table.
     */
    @Autowired
    public CheckExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                     DqoQueueJobFactory dqoQueueJobFactory,
                                     DqoJobQueue dqoJobQueue,
                                     ScheduledTargetChecksFindService scheduledTargetChecksFindService,
                                     TableCheckExecutionService tableCheckExecutionService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.scheduledTargetChecksFindService = scheduledTargetChecksFindService;
        this.tableCheckExecutionService = tableCheckExecutionService;
    }

    /**
     * Executes data quality checks. Reports progress and saves the results.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters Check search filters to find the right checks.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param collectErrorSamples Collect error samples for failed checks. Can disable or enable sample collection independent of other parameters.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param executionTarget Check execution mode (sensors, rules, or both).
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all checks without starting additional jobs.
     * @param parentJobId Parent job id for the parent job.
     * @param jobCancellationToken Job cancellation token.
     * @param principal Principal that will be used to run the job.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    @Override
    public CheckExecutionSummary executeChecks(ExecutionContext executionContext,
                                               CheckSearchFilters checkSearchFilters,
                                               TimeWindowFilterParameters userTimeWindowFilters,
                                               Boolean collectErrorSamples,
                                               CheckExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               RunChecksTarget executionTarget,
                                               boolean startChildJobsPerTable,
                                               DqoQueueJobId parentJobId,
                                               JobCancellationToken jobCancellationToken,
                                               DqoUserPrincipal principal) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        Collection<TableWrapper> targetTables = listTargetTables(userHome, checkSearchFilters);
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();
        jobCancellationToken.throwIfCancelled();

        if (startChildJobsPerTable) {
            List<DqoQueueJob<CheckExecutionSummary>> childTableJobs = new ArrayList<>();

            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

                RunChecksOnTableParameters runChecksOnTableParameters = new RunChecksOnTableParameters() {{
                   setConnection(connectionWrapper.getName());
                   setMaxJobsPerConnection(connectionWrapper.getSpec().getParallelJobsLimit());
                   setTable(targetTable.getPhysicalTableName());
                   setCheckSearchFilters(checkSearchFilters);
                   setTimeWindowFilter(userTimeWindowFilters);
                   setProgressListener(progressListener);
                   setDummyExecution(dummySensorExecution);
                   setCollectErrorSamples(collectErrorSamples);
                   setExecutionTarget(executionTarget);
                }};
                RunChecksOnTableQueueJob runChecksOnTableJob = this.dqoQueueJobFactory.createRunChecksOnTableJob();
                runChecksOnTableJob.setParameters(runChecksOnTableParameters);
                childTableJobs.add(runChecksOnTableJob);
            }

            ChildDqoQueueJobsContainer<CheckExecutionSummary> childTableJobsContainer = this.dqoJobQueue.pushChildJobs(childTableJobs, parentJobId, principal);
            List<CheckExecutionSummary> checkExecutionSummaries = childTableJobsContainer.waitForChildResults(jobCancellationToken);
            checkExecutionSummaries.forEach(checkExecutionSummary::append);
        }
        else {
            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());
                CheckExecutionSummary tableCheckExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(
                        executionContext, userHome, connectionWrapper, targetTable,
                        checkSearchFilters, userTimeWindowFilters, collectErrorSamples, progressListener,
                        dummySensorExecution, executionTarget, jobCancellationToken);
                checkExecutionSummary.append(tableCheckExecutionSummary);
            }
        }

        progressListener.onCheckExecutionFinished(new CheckExecutionFinishedEvent(checkExecutionSummary));

        return checkExecutionSummary;
    }

    /**
     * Executes scheduled data quality checks. A list of checks divided by tables must be provided.
     *
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param targetSchedule        Target schedule to match, when finding checks that should be executed.
     * @param progressListener      Progress listener that receives progress calls.
     * @param parentJobId           Parent job id.
     * @param jobCancellationToken  Job cancellation token.
     * @param principal             Principal that will be used to run the job.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    @Override
    public CheckExecutionSummary executeChecksForSchedule(ExecutionContext executionContext,
                                                          CronScheduleSpec targetSchedule,
                                                          CheckExecutionProgressListener progressListener,
                                                          DqoQueueJobId parentJobId,
                                                          JobCancellationToken jobCancellationToken,
                                                          DqoUserPrincipal principal) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        ScheduledChecksCollection checksForSchedule = this.scheduledTargetChecksFindService.findChecksForSchedule(userHome, targetSchedule);
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();
        List<DqoQueueJob<CheckExecutionSummary>> childTableJobs = new ArrayList<>();

        for (ScheduledTableChecksCollection scheduledChecksForTable : checksForSchedule.getTablesWithChecks()) {
            TableSpec targetTable = scheduledChecksForTable.getTargetTable();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
            checkSearchFilters.setEnabled(true);
            checkSearchFilters.setConnection(connectionWrapper.getName());
            checkSearchFilters.setFullTableName(targetTable.getPhysicalTableName().toTableSearchFilter());
            checkSearchFilters.setCheckHierarchyIds(scheduledChecksForTable.getChecks());

            RunChecksOnTableParameters runChecksOnTableParameters = new RunChecksOnTableParameters() {{
                setConnection(connectionWrapper.getName());
                setMaxJobsPerConnection(connectionWrapper.getSpec().getParallelJobsLimit());
                setTable(targetTable.getPhysicalTableName());
                setCheckSearchFilters(checkSearchFilters);
                setTimeWindowFilter(null);
                setProgressListener(progressListener);
                setExecutionTarget(RunChecksTarget.sensors_and_rules);
            }};
            RunChecksOnTableQueueJob runChecksOnTableJob = this.dqoQueueJobFactory.createRunChecksOnTableJob();
            runChecksOnTableJob.setParameters(runChecksOnTableParameters);
            childTableJobs.add(runChecksOnTableJob);
        }

        ChildDqoQueueJobsContainer<CheckExecutionSummary> childTableJobsContainer = this.dqoJobQueue.pushChildJobs(childTableJobs, parentJobId, principal);
        List<CheckExecutionSummary> checkExecutionSummaries = childTableJobsContainer.waitForChildResults(jobCancellationToken);
        checkExecutionSummaries.forEach(checkExecutionSummary::append);

        progressListener.onCheckExecutionFinished(new CheckExecutionFinishedEvent(checkExecutionSummary));

        return checkExecutionSummary;
    }

    /**
     * Executes selected checks on a table. This method is called from {@link com.dqops.execution.checks.jobs.RunChecksOnTableQueueJob}
     * that is a child job which runs checks for each table in parallel.
     * @param executionContext Execution context that provides access to the user home.
     * @param connectionName Connection name on which the checks are executed.
     * @param targetTable Full name of the target table.
     * @param checkSearchFilters Check search filters, may not specify the connection and the table name.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param collectErrorSamples Collect error samples for failed checks.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param executionTarget Check execution mode (sensors, rules, or both).
     * @param jobCancellationToken Job cancellation token.
     * @return Check summary table with the count of alerts, checks and rules for each table, but containing only one row for the target table. The result may be empty if the table was not found.
     */
    @Override
    public CheckExecutionSummary executeSelectedChecksOnTable(ExecutionContext executionContext,
                                                              String connectionName,
                                                              PhysicalTableName targetTable,
                                                              CheckSearchFilters checkSearchFilters,
                                                              TimeWindowFilterParameters userTimeWindowFilters,
                                                              Boolean collectErrorSamples,
                                                              CheckExecutionProgressListener progressListener,
                                                              boolean dummySensorExecution,
                                                              RunChecksTarget executionTarget,
                                                              JobCancellationToken jobCancellationToken) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();

        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new CheckExecutionSummary(); // the connection was probably deleted in the meantime
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(targetTable, true);
        if (tableWrapper == null) {
            return new CheckExecutionSummary(); // the table was probably deleted in the meantime
        }

        CheckExecutionSummary checkExecutionSummary = this.tableCheckExecutionService.executeChecksOnTable(
                executionContext, userHome, connectionWrapper, tableWrapper,
                checkSearchFilters, userTimeWindowFilters, collectErrorSamples, progressListener,
                dummySensorExecution, executionTarget, jobCancellationToken);

        return checkExecutionSummary;
    }

    /**
     * Lists all target tables that were not excluded from the filter and may have checks to be executed.
     * @param userHome User home.
     * @param checkSearchFilters Check search filter.
     * @return Collection of table wrappers.
     */
    public Collection<TableWrapper> listTargetTables(UserHome userHome, CheckSearchFilters checkSearchFilters) {
        Collection<TableWrapper> tables = this.hierarchyNodeTreeSearcher.findTables(userHome.getConnections(), checkSearchFilters);
        return tables;
    }
}
