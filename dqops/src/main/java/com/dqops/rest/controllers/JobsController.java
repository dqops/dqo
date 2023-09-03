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
package com.dqops.rest.controllers;

import com.dqops.core.configuration.DqoQueueConfigurationProperties;
import com.dqops.core.configuration.DqoQueueWaitTimeoutsConfigurationProperties;
import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJob;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobResult;
import com.dqops.core.jobqueue.monitoring.DqoJobHistoryEntryModel;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueIncrementalSnapshotModel;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueInitialSnapshotModel;
import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.synchronization.jobs.*;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.jobs.RunChecksQueueJob;
import com.dqops.execution.checks.jobs.RunChecksParameters;
import com.dqops.execution.checks.jobs.RunChecksJobResult;
import com.dqops.execution.checks.jobs.RunChecksQueueJobResult;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerProvider;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJob;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJobParameters;
import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListenerProvider;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Jobs controller that supports publishing new jobs.
 */
@RestController
@RequestMapping("/api/jobs")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Jobs", description = "Jobs management controller that supports starting new jobs, such as running selected data quality checks")
@Slf4j
public class JobsController {
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private ParentDqoJobQueue parentDqoJobQueue;
    private JobSchedulerService jobSchedulerService;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider;
    private final DqoJobQueueMonitoringService jobQueueMonitoringService;
    private final DqoQueueConfigurationProperties dqoQueueConfigurationProperties;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;
    private DqoQueueWaitTimeoutsConfigurationProperties dqoQueueWaitTimeoutsConfigurationProperties;
    private SynchronizationStatusTracker synchronizationStatusTracker;

    /**
     * Creates a new controller, injecting dependencies.
     * @param dqoQueueJobFactory DQO queue job factory used to create new instances of jobs.
     * @param dqoJobQueue Job queue used to publish or review running jobs.
     * @param parentDqoJobQueue Job queue for managing parent jobs (jobs that will start other child jobs).
     * @param jobSchedulerService Job scheduler service used to start and stop the scheduler.
     * @param checkExecutionProgressListenerProvider Check execution progress listener provider used to create a valid progress listener when starting a "runchecks" job.
     * @param statisticsCollectorExecutionProgressListenerProvider Profiler execution progress listener provider used to create a valid progress listener when starting a "runprofilers" job.
     * @param jobQueueMonitoringService Job queue monitoring service.
     * @param dqoQueueConfigurationProperties Queue configuration parameters.
     * @param dqoSchedulerConfigurationProperties DQO job scheduler configuration properties.
     * @param dqoQueueWaitTimeoutsConfigurationProperties DQO queue default wait time parameters.
     * @param synchronizationStatusTracker Synchronization change tracker.
     */
    @Autowired
    public JobsController(DqoQueueJobFactory dqoQueueJobFactory,
                          DqoJobQueue dqoJobQueue,
                          ParentDqoJobQueue parentDqoJobQueue,
                          JobSchedulerService jobSchedulerService,
                          CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                          StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider,
                          DqoJobQueueMonitoringService jobQueueMonitoringService,
                          DqoQueueConfigurationProperties dqoQueueConfigurationProperties,
                          DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties,
                          DqoQueueWaitTimeoutsConfigurationProperties dqoQueueWaitTimeoutsConfigurationProperties,
                          SynchronizationStatusTracker synchronizationStatusTracker) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.jobSchedulerService = jobSchedulerService;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.statisticsCollectorExecutionProgressListenerProvider = statisticsCollectorExecutionProgressListenerProvider;
        this.jobQueueMonitoringService = jobQueueMonitoringService;
        this.dqoQueueConfigurationProperties = dqoQueueConfigurationProperties;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
        this.dqoQueueWaitTimeoutsConfigurationProperties = dqoQueueWaitTimeoutsConfigurationProperties;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
    }

    /**
     * Starts a new background job that will run selected data quality checks.
     * @param runChecksParameters Run checks parameters with a check filter and an optional time range.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/runchecks", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "runChecks", notes = "Starts a new background job that will run selected data quality checks", response = RunChecksQueueJobResult.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data quality checks was added to the queue", response = RunChecksQueueJobResult.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<RunChecksQueueJobResult>> runChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data quality check run configuration (target checks and an optional time range)")
            @RequestBody RunChecksParameters runChecksParameters,
            @ApiParam(name = "wait", value = "Wait until the checks finish to run, the default value is false (queue a background job and return the job id)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                CheckRunReportingMode.silent, false);
        runChecksParameters.setProgressListener(progressListener);
        runChecksJob.setParameters(runChecksParameters);

        PushJobResult<CheckExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runChecksJob);

        if (wait.isPresent() && wait.get()) {
            // wait for the result
            long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                    this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();
            CompletableFuture<CheckExecutionSummary> timeoutLimitedFuture = pushJobResult.getFinishedFuture()
                    .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
            Mono<RunChecksQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutLimitedFuture)
                    .map(summary -> {
                        RunChecksJobResult runChecksJobResult = RunChecksJobResult.fromCheckExecutionSummary(summary);
                        DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(runChecksJob.getJobId());
                        return new RunChecksQueueJobResult(pushJobResult.getJobId(), runChecksJobResult, jobHistoryEntryModel.getStatus());
                    });

            return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
        }

        Mono<RunChecksQueueJobResult> resultWithOnlyJobId = Mono.just(new RunChecksQueueJobResult(pushJobResult.getJobId()));
        return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
    }

    /**
     * Waits for a previously submitted "run checks" job. Returns the result that may contain the result if the job finished before the timeout elapsed.
     * @return Job summary response with the identity of the started job.
     */
    @GetMapping(value = "/runchecks/{jobId}/wait", produces = "application/json")
    @ApiOperation(value = "waitForRunChecksJob", notes = "Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.",
            response = RunChecksQueueJobResult.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Run checks job information returned. When the wait timeout has elapsed, the job status could be still queued or running and the result will be missing.",
                    response = RunChecksQueueJobResult.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<RunChecksQueueJobResult>> waitForRunChecksJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable long jobId,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but could be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(new DqoQueueJobId(jobId));
        if (jobHistoryEntryModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        CompletableFuture<?> jobFinishedFuture = jobHistoryEntryModel.getJobQueueEntry().getJob().getFinishedFuture();
        long defaultWaitTimeout = this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();

        long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() : defaultWaitTimeout;
        CompletableFuture<?> timeoutLimitedFuture = jobFinishedFuture
                .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);

        Mono<RunChecksQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutLimitedFuture)
                .map(_none -> {
                    DqoJobHistoryEntryModel mostRecentJobModel = this.jobQueueMonitoringService.getJob(new DqoQueueJobId(jobId));
                    if (mostRecentJobModel == null) {
                        return null;
                    }

                    RunChecksQueueJobResult jobResult = new RunChecksQueueJobResult(
                            mostRecentJobModel.getJobId(),
                            mostRecentJobModel.getParameters().getRunChecksParameters().getRunChecksResult(),
                            mostRecentJobModel.getStatus());
                    return jobResult;
                });

        return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.OK); // 200
    }

    /**
     * Starts a new background job that will run selected data statistics collectors on the whole table.
     * @param statisticsCollectorSearchFilters Data statistics collector filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/collectstatistics/table", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "collectStatisticsOnTable", notes = "Starts a new background job that will run selected data statistics collectors on a whole table", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data statistics collection was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<DqoQueueJobId>> collectStatisticsOnTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data statistics collectors filter") @RequestBody StatisticsCollectorSearchFilters statisticsCollectorSearchFilters) {
        CollectStatisticsQueueJob runProfilersJob = this.dqoQueueJobFactory.createCollectStatisticsJob();
        StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                StatisticsCollectorExecutionReportingMode.silent, false);
        CollectStatisticsQueueJobParameters collectStatisticsQueueJobParameters = new CollectStatisticsQueueJobParameters(
                statisticsCollectorSearchFilters,
                progressListener,
                StatisticsDataScope.table,
                false);
        runProfilersJob.setParameters(collectStatisticsQueueJobParameters);

        PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runProfilersJob);
        return new ResponseEntity<>(Mono.just(pushJobResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Starts a new background job that will run selected data statistics collectors for each data stream separately. Uses the default data group on each table.
     * @param statisticsCollectorSearchFilters Data statistics collectors filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/collectstatistics/withgrouping", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "collectStatisticsOnDataGroups", notes = "Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data statistics collection was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<DqoQueueJobId>> collectStatisticsOnDataGroups(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data statistics collectors filter") @RequestBody StatisticsCollectorSearchFilters statisticsCollectorSearchFilters) {
        CollectStatisticsQueueJob runProfilersJob = this.dqoQueueJobFactory.createCollectStatisticsJob();
        StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                StatisticsCollectorExecutionReportingMode.silent, false);
        CollectStatisticsQueueJobParameters collectStatisticsQueueJobParameters = new CollectStatisticsQueueJobParameters(
                statisticsCollectorSearchFilters,
                progressListener,
                StatisticsDataScope.data_groupings,
                false);
        runProfilersJob.setParameters(collectStatisticsQueueJobParameters);

        PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runProfilersJob);
        return new ResponseEntity<>(Mono.just(pushJobResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Retrieves a list of all queued and recently finished jobs.
     * @return List of all active or recently finished jobs on the queue.
     */
    @GetMapping(value = "/jobs", produces = "application/json")
    @ApiOperation(value = "getAllJobs", notes = "Retrieves a list of all queued and recently finished jobs.",
            response = DqoJobQueueInitialSnapshotModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all queued and finished jobs returned. Call jobchangessince/{changeSequence} to receive incremental changes.",
                    response = DqoJobQueueInitialSnapshotModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoJobQueueInitialSnapshotModel>> getAllJobs(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        Mono<DqoJobQueueInitialSnapshotModel> initialJobList = this.jobQueueMonitoringService.getInitialJobList();
        return new ResponseEntity<>(initialJobList, HttpStatus.OK); // 200
    }

    /**
     * Retrieves the status of a single job.
     * @return Returns the model of a job.
     */
    @GetMapping(value = "/jobs/{jobId}", produces = "application/json")
    @ApiOperation(value = "getJob", notes = "Retrieves the current status of a single job, identified by a job id.",
            response = DqoJobHistoryEntryModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieves the current status of a single job, identified by a job id.",
                    response = DqoJobHistoryEntryModel.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoJobHistoryEntryModel>> getJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable long jobId) {
        DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(new DqoQueueJobId(jobId));
        if (jobHistoryEntryModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Mono.just(jobHistoryEntryModel), HttpStatus.OK); // 200
    }

    /**
     * Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.
     * @return Returns the model of a job. The model contains the result if the job finished before the wait timeout elapsed.
     */
    @GetMapping(value = "/jobs/{jobId}/wait", produces = "application/json")
    @ApiOperation(value = "waitForJob", notes = "Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.",
            response = DqoJobHistoryEntryModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The job status was returned. If the response is returned before the wait timeout, the response will contain information about a finished job. When the wait timeout has elapsed, the job status could be still queued or running.",
                    response = DqoJobHistoryEntryModel.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoJobHistoryEntryModel>> waitForJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable long jobId,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but could be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(new DqoQueueJobId(jobId));
        if (jobHistoryEntryModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        CompletableFuture<?> jobFinishedFuture = jobHistoryEntryModel.getJobQueueEntry().getJob().getFinishedFuture();
        long defaultWaitTimeout = this.dqoQueueWaitTimeoutsConfigurationProperties.getWaitTimeForJobType(jobHistoryEntryModel.getJobType());

        long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() : defaultWaitTimeout;
        CompletableFuture<?> timeoutLimitedFuture = jobFinishedFuture
                .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);

        Mono<DqoJobHistoryEntryModel> monoWithResultAndTimeout = Mono.fromFuture(timeoutLimitedFuture)
                .map(_none -> {
                    DqoJobHistoryEntryModel mostRecentJobModel = this.jobQueueMonitoringService.getJob(new DqoQueueJobId(jobId));
                    return mostRecentJobModel;
                });

        return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.OK); // 200
    }

    /**
     * Cancels a running job.
     * @param jobId Job id of a job to cancel.
     * @return Empty response.
     */
    @DeleteMapping(value = "/jobs/{jobId}", produces = "application/json")
    @ApiOperation(value = "cancelJob", notes = "Cancels a running job")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Job was cancelled"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<?>> cancelJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable long jobId) {
        DqoQueueJobId dqoQueueJobId = new DqoQueueJobId(jobId);
        this.dqoJobQueue.cancelJob(dqoQueueJobId);
        this.parentDqoJobQueue.cancelJob(dqoQueueJobId);  // we don't know on which queue the job is running, but it cannot run on both and it is safe to cancel a missing job, so we cancel on both the queues

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Retrieves an incremental list of job changes (new jobs or job status changes).
     * @return List of jobs that have changed since the given sequence number.
     */
    @GetMapping(value = "/jobchangessince/{sequenceNumber}", produces = "application/json")
    @ApiOperation(value = "getJobChangesSince", notes = "Retrieves an incremental list of job changes (new jobs or job status changes)",
            response = DqoJobQueueIncrementalSnapshotModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all queued and finished jobs returned. Call jobchangessince/{sequenceNumber} to receive incremental changes.",
                    response = DqoJobQueueIncrementalSnapshotModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoJobQueueIncrementalSnapshotModel>> getJobChangesSince(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Change sequence number to get job changes after that sequence") @PathVariable long sequenceNumber) {
        try {
            Mono<DqoJobQueueIncrementalSnapshotModel> incrementalJobChanges = this.jobQueueMonitoringService.getIncrementalJobChanges(
                    sequenceNumber, this.dqoQueueConfigurationProperties.getGetJobChangesSinceWaitSeconds(), TimeUnit.SECONDS);
            Mono<DqoJobQueueIncrementalSnapshotModel> returnEmptyWhenError = incrementalJobChanges.doOnError(
                    error -> Mono.just(new DqoJobQueueIncrementalSnapshotModel(
                            new ArrayList<>(), this.synchronizationStatusTracker.getCurrentSynchronizationStatus(), sequenceNumber)));
            return new ResponseEntity<>(returnEmptyWhenError, HttpStatus.OK); // 200
        }
        catch (Exception ex) {
            log.error("Failed to retrieve recent jobs, error: " + ex.getMessage(), ex);
            return new ResponseEntity<>(Mono.just(new DqoJobQueueIncrementalSnapshotModel(
                    new ArrayList<>(), this.synchronizationStatusTracker.getCurrentSynchronizationStatus(), sequenceNumber)), HttpStatus.OK);
        }
    }

    /**
     * Starts a new background job that will import selected tables.
     * @param importParameters Import tables job parameters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/importtables",consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "importTables", notes = "Starts a new background job that will import selected tables.", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will import selected tables was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<DqoQueueJobId>> importTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Import tables job parameters")
            @RequestBody ImportTablesQueueJobParameters importParameters) {
        // TODO: Add listener.
        ImportTablesQueueJob importTablesJob = this.dqoQueueJobFactory.createImportTablesJob();
        importTablesJob.setImportParameters(importParameters);
        PushJobResult<ImportTablesQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(importTablesJob);
        return new ResponseEntity<>(Mono.just(pushJobResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Starts a new background job that will delete specified data from the .data folder.
     * @param deleteStoredDataParameters Delete stored data job parameters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/deletestoreddata", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "deleteStoredData", notes = "Starts a new background job that will delete stored data about check results, sensor readouts etc.", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will delete stored registry data was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<DqoQueueJobId>> deleteStoredData(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Delete stored data job parameters")
            @RequestBody DeleteStoredDataQueueJobParameters deleteStoredDataParameters) {
        DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
        deleteStoredDataJob.setDeletionParameters(deleteStoredDataParameters);
        PushJobResult<DeleteStoredDataQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob);
        return new ResponseEntity<>(Mono.just(pushJobResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Starts a file synchronization job that will synchronize files to DQO Cloud.
     * @param synchronizeFolderParameters Delete stored data job parameters.
     * @return Job summary response with the identity of the started jobs.
     */
    @PostMapping(value = "/synchronize",consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "synchronizeFolders", notes = "Starts multiple file synchronization jobs that will synchronize files from selected DQO User home folders to the DQO Cloud. " +
            "The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New jobs that will synchronize folders were added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<DqoQueueJobId>> synchronizeFolders(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Selection of folders that should be synchronized to the DQO Cloud")
            @RequestBody SynchronizeMultipleFoldersDqoQueueJobParameters synchronizeFolderParameters) {
        SynchronizeMultipleFoldersDqoQueueJob synchronizeMultipleFoldersJob = this.dqoQueueJobFactory.createSynchronizeMultipleFoldersJob();
        synchronizeMultipleFoldersJob.setParameters(synchronizeFolderParameters);
        PushJobResult<Void> jobPushResult = this.parentDqoJobQueue.pushJob(synchronizeMultipleFoldersJob);

        return new ResponseEntity<>(Mono.just(jobPushResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Retrieves the state of the job scheduler.
     * @return true when the cron scheduler is running, false when it is stopped.
     */
    @GetMapping(value = "/scheduler/isrunning", produces = "application/json")
    @ApiOperation(value = "isCronSchedulerRunning", notes = "Checks if the DQO internal CRON scheduler is running and processing jobs scheduled using cron expressions.",
            response = Boolean.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler status was checked and returned",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<Boolean>> isCronSchedulerRunning(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        Boolean started = this.jobSchedulerService.isStarted();
        return new ResponseEntity<>(Mono.just(started), HttpStatus.OK); // 200
    }

    /**
     * Starts the cron job scheduler (when it is not running).
     * @return Nothing.
     */
    @PostMapping(value = "/scheduler/status/start", produces = "application/json")
    @ApiOperation(value = "startCronScheduler", notes = "Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.",
            response = Void.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler was started or was already running",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> startCronScheduler(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        if (!this.jobSchedulerService.isStarted()) {
            this.jobSchedulerService.start(
                    this.dqoSchedulerConfigurationProperties.getSynchronizationMode(),
                    this.dqoSchedulerConfigurationProperties.getCheckRunMode());
            this.jobSchedulerService.triggerMetadataSynchronization();
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
    }

    /**
     * Stops the cron job scheduler (when it is not running).
     * @return Nothing.
     */
    @PostMapping(value = "/scheduler/status/stop", produces = "application/json")
    @ApiOperation(value = "stopCronScheduler", notes = "Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.",
            response = Void.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler was stopped or was already not running",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<?>> stopCronScheduler(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        if (this.jobSchedulerService.isStarted()) {
            this.jobSchedulerService.shutdown();
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
    }
}
