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
import com.dqops.core.jobqueue.jobs.table.ImportTablesResult;
import com.dqops.core.jobqueue.monitoring.*;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.synchronization.jobs.*;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.jobs.RunChecksQueueJob;
import com.dqops.execution.checks.jobs.RunChecksParameters;
import com.dqops.execution.checks.jobs.RunChecksResult;
import com.dqops.execution.checks.jobs.RunChecksQueueJobResult;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerProvider;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesParameters;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesQueueJob;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesResult;
import com.dqops.execution.errorsampling.jobs.ErrorSamplerResult;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListenerProvider;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionReportingMode;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJob;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJobParameters;
import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;
import com.dqops.execution.statistics.jobs.CollectStatisticsQueueJobResult;
import com.dqops.execution.statistics.jobs.CollectStatisticsResult;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListenerProvider;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.utils.conversion.NumericTypeConverter;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
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
@Api(value = "Jobs", description = "Jobs management controller that supports starting new jobs, such as running selected data quality checks. Provides access to the job queue for incremental monitoring.")
@Slf4j
public class JobsController {
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private ParentDqoJobQueue parentDqoJobQueue;
    private JobSchedulerService jobSchedulerService;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider;
    private final ErrorSamplerExecutionProgressListenerProvider errorSamplerExecutionProgressListenerProvider;
    private final DqoJobQueueMonitoringService jobQueueMonitoringService;
    private final DqoQueueConfigurationProperties dqoQueueConfigurationProperties;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;
    private DqoQueueWaitTimeoutsConfigurationProperties dqoQueueWaitTimeoutsConfigurationProperties;
    private SynchronizationStatusTracker synchronizationStatusTracker;

    /**
     * Creates a new controller, injecting dependencies.
     * @param dqoQueueJobFactory DQOps queue job factory used to create new instances of jobs.
     * @param dqoJobQueue Job queue used to publish or review running jobs.
     * @param parentDqoJobQueue Job queue for managing parent jobs (jobs that will start other child jobs).
     * @param jobSchedulerService Job scheduler service used to start and stop the scheduler.
     * @param checkExecutionProgressListenerProvider Check execution progress listener provider used to create a valid progress listener when starting a "run checks" job.
     * @param statisticsCollectorExecutionProgressListenerProvider Profiler execution progress listener provider used to create a valid progress listener when starting a "run collectors" job.
     * @param errorSamplerExecutionProgressListenerProvider Error sampler execution progress listener factory to create a valid progress listener for the "collect error samples" job.
     * @param jobQueueMonitoringService Job queue monitoring service.
     * @param dqoQueueConfigurationProperties Queue configuration parameters.
     * @param dqoSchedulerConfigurationProperties DQOps job scheduler configuration properties.
     * @param dqoQueueWaitTimeoutsConfigurationProperties DQOps queue default wait time parameters.
     * @param synchronizationStatusTracker Synchronization change tracker.
     */
    @Autowired
    public JobsController(DqoQueueJobFactory dqoQueueJobFactory,
                          DqoJobQueue dqoJobQueue,
                          ParentDqoJobQueue parentDqoJobQueue,
                          JobSchedulerService jobSchedulerService,
                          CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                          StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider,
                          ErrorSamplerExecutionProgressListenerProvider errorSamplerExecutionProgressListenerProvider,
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
        this.errorSamplerExecutionProgressListenerProvider = errorSamplerExecutionProgressListenerProvider;
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
    @ApiOperation(value = "runChecks", notes = "Starts a new background job that will run selected data quality checks",
            response = RunChecksQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data quality checks was added to the queue", response = RunChecksQueueJobResult.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<RunChecksQueueJobResult>>> runChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data quality check run configuration (target checks and an optional time range)")
            @RequestBody RunChecksParameters runChecksParameters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the checks finish to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
            CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                    CheckRunReportingMode.silent, false);
            runChecksParameters.setProgressListener(progressListener);
            runChecksJob.setParameters(runChecksParameters);
            runChecksJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<CheckExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runChecksJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();
                CompletableFuture<CheckExecutionSummary> timeoutLimitedFuture = new CompletableFuture<CheckExecutionSummary>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<CheckExecutionSummary> timeoutOrFinishedFuture =
                        (CompletableFuture<CheckExecutionSummary>)(CompletableFuture<?>)
                        CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<RunChecksQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .map(summary -> {
                            RunChecksResult runChecksResult = RunChecksResult.fromCheckExecutionSummary(summary);
                            DqoJobCompletionStatus jobCompletionStatus = runChecksJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(runChecksJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new RunChecksQueueJobResult(pushJobResult.getJobId(), runChecksResult, dqoJobStatus);
                        });

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<RunChecksQueueJobResult> resultWithOnlyJobId = Mono.just(new RunChecksQueueJobResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Tries to find and parse a job id. Looks up the job by the business key in the job monitoring service.
     * If the job is not found, parses it to a long. If parsing fails (because it is a business key, but the job no longer exists), just returns a fake job id.
     * @param jobIdOrBusinessKey Job id or a job business key.
     * @return Job id object.
     */
    private DqoQueueJobId findJobId(String jobIdOrBusinessKey) {
        DqoQueueJobId dqoQueueJobId = this.jobQueueMonitoringService.lookupJobIdByBusinessKey(jobIdOrBusinessKey);
        if (dqoQueueJobId == null) {
            Long jobIdNumeric = NumericTypeConverter.tryParseLong(jobIdOrBusinessKey);
            if (jobIdNumeric == null) {
                return new DqoQueueJobId(-9999999L); // unknown job
            } else {
               return new DqoQueueJobId(jobIdNumeric);
            }
        }

        return dqoQueueJobId;
    }

    /**
     * Waits for a previously submitted "run checks" job. Returns the result that may contain the result if the job finished before the timeout elapsed.
     * @return Job summary response with the identity of the started job.
     */
    @GetMapping(value = "/runchecks/{jobId}/wait", produces = "application/json")
    @ApiOperation(value = "waitForRunChecksJob", notes = "Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.",
            response = RunChecksQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Run checks job information returned. When the wait timeout has elapsed, the job status could be still queued or running and the result will be missing.",
                    response = RunChecksQueueJobResult.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<RunChecksQueueJobResult>>> waitForRunChecksJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id, it can be a job business key assigned to the job or a job id generated by DQOps") @PathVariable String jobId,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            DqoQueueJobId dqoQueueJobId = findJobId(jobId);

            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
            if (jobHistoryEntryModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            DqoQueueJob<?> job = jobHistoryEntryModel.getJobQueueEntry().getJob();
            CompletableFuture<?> jobFinishedFuture = job.getFinishedFuture();
            long defaultWaitTimeout = this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();

            long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() : defaultWaitTimeout;
            CompletableFuture<RunChecksQueueJobResult> timeoutLimitedFuture = new CompletableFuture<RunChecksQueueJobResult>()
                    .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
            CompletableFuture<RunChecksQueueJobResult> timeoutOrFinishedFuture =
                    (CompletableFuture<RunChecksQueueJobResult>)(CompletableFuture<?>)
                    CompletableFuture.anyOf(jobFinishedFuture, timeoutLimitedFuture);

            Mono<RunChecksQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                    .map(_none -> {
                        DqoJobHistoryEntryModel mostRecentJobModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
                        if (mostRecentJobModel == null) {
                            return null;
                        }

                        DqoJobCompletionStatus jobCompletionStatus = job.getCompletionStatus();
                        DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();

                        RunChecksQueueJobResult jobResult = new RunChecksQueueJobResult(
                                mostRecentJobModel.getJobId(),
                                mostRecentJobModel.getParameters().getRunChecksParameters().getRunChecksResult(),
                                dqoJobStatus);
                        return jobResult;
                    });

            return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.OK); // 200
        }));
    }

    /**
     * Starts a new background job that will run selected data quality checks to capture their error samples.
     * @param collectErrorSamplesParameters Run checks parameters with a check filter and an optional time range.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/collecterrorsamples", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "collectErrorSamples", notes = "Starts a new background job that will run selected data quality checks to collect their error samples",
            response = CollectErrorSamplesResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will collect error samples for data quality checks was added to the queue", response = CollectErrorSamplesResult.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<CollectErrorSamplesResult>>> collectErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data quality check run configuration (target checks and an optional time range)")
            @RequestBody CollectErrorSamplesParameters collectErrorSamplesParameters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the error sampling finish to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            CollectErrorSamplesQueueJob collectErrorSamplesJob = this.dqoQueueJobFactory.createCollectErrorSamplesJob();
            ErrorSamplerExecutionProgressListener progressListener = this.errorSamplerExecutionProgressListenerProvider.getProgressListener(
                    ErrorSamplerExecutionReportingMode.silent, false);
            collectErrorSamplesParameters.setProgressListener(progressListener);
            collectErrorSamplesJob.setParameters(collectErrorSamplesParameters);
            collectErrorSamplesJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<ErrorSamplerExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(collectErrorSamplesJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();
                CompletableFuture<ErrorSamplerExecutionSummary> timeoutLimitedFuture = new CompletableFuture<ErrorSamplerExecutionSummary>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<ErrorSamplerExecutionSummary> timeoutOrFinishedFuture =
                        (CompletableFuture<ErrorSamplerExecutionSummary>)(CompletableFuture<?>)
                                CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<CollectErrorSamplesResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .map(summary -> {
                            ErrorSamplerResult runChecksResult = ErrorSamplerResult.fromErrorSamplerExecutionSummary(summary);
                            DqoJobCompletionStatus jobCompletionStatus = collectErrorSamplesJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(collectErrorSamplesJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new CollectErrorSamplesResult(pushJobResult.getJobId(), runChecksResult, dqoJobStatus);
                        });

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<CollectErrorSamplesResult> resultWithOnlyJobId = Mono.just(new CollectErrorSamplesResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Waits for a previously submitted "collect error samples" job. Returns the result that may contain the result if the job finished before the timeout elapsed.
     * @return Job summary response with the identity of the started job.
     */
    @GetMapping(value = "/collecterrorsamples/{jobId}/wait", produces = "application/json")
    @ApiOperation(value = "waitForCollectErrorSamplesJob", notes = "Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.",
            response = CollectErrorSamplesResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Collect error samples job information returned. When the wait timeout has elapsed, the job status could be still queued or running and the result will be missing.",
                    response = CollectErrorSamplesResult.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CollectErrorSamplesResult>>> waitForCollectErrorSamplesJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id, it can be a job business key assigned to the job or a job id generated by DQOps") @PathVariable String jobId,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            DqoQueueJobId dqoQueueJobId = findJobId(jobId);

            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
            if (jobHistoryEntryModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            DqoQueueJob<?> job = jobHistoryEntryModel.getJobQueueEntry().getJob();
            CompletableFuture<?> jobFinishedFuture = job.getFinishedFuture();
            long defaultWaitTimeout = this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();

            long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() : defaultWaitTimeout;
            CompletableFuture<CollectErrorSamplesResult> timeoutLimitedFuture = new CompletableFuture<CollectErrorSamplesResult>()
                    .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
            CompletableFuture<CollectErrorSamplesResult> timeoutOrFinishedFuture =
                    (CompletableFuture<CollectErrorSamplesResult>)(CompletableFuture<?>)
                            CompletableFuture.anyOf(jobFinishedFuture, timeoutLimitedFuture);

            Mono<CollectErrorSamplesResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                    .map(_none -> {
                        DqoJobHistoryEntryModel mostRecentJobModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
                        if (mostRecentJobModel == null) {
                            return null;
                        }

                        DqoJobCompletionStatus jobCompletionStatus = job.getCompletionStatus();
                        DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();

                        CollectErrorSamplesResult jobResult = new CollectErrorSamplesResult(
                                mostRecentJobModel.getJobId(),
                                mostRecentJobModel.getParameters().getCollectErrorSamplesParameters().getErrorSamplerResult(),
                                dqoJobStatus);
                        return jobResult;
                    });

            return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.OK); // 200
        }));
    }

    /**
     * Starts a new background job that will run selected data statistics collectors on the whole table.
     * @param statisticsCollectorSearchFilters Data statistics collector filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/collectstatistics/table", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "collectStatisticsOnTable", notes = "Starts a new background job that will run selected data statistics collectors for the entire table",
            response = CollectStatisticsQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data statistics collection was added to the queue", response = CollectStatisticsQueueJobResult.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<CollectStatisticsQueueJobResult>>> collectStatisticsOnTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data statistics collectors filter") @RequestBody StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the statistic collection job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            CollectStatisticsQueueJob runProfilersJob = this.dqoQueueJobFactory.createCollectStatisticsJob();
            StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                    StatisticsCollectorExecutionReportingMode.silent, false);
            CollectStatisticsQueueJobParameters collectStatisticsQueueJobParameters = new CollectStatisticsQueueJobParameters(
                    statisticsCollectorSearchFilters,
                    progressListener,
                    StatisticsDataScope.table,
                    false);
            runProfilersJob.setParameters(collectStatisticsQueueJobParameters);
            runProfilersJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runProfilersJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getRunChecks();
                CompletableFuture<StatisticsCollectionExecutionSummary> timeoutLimitedFuture = new CompletableFuture<StatisticsCollectionExecutionSummary>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<StatisticsCollectionExecutionSummary> timeoutOrFinishedFuture =
                        (CompletableFuture<StatisticsCollectionExecutionSummary>)(CompletableFuture<?>)
                                CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<CollectStatisticsQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .map(summary -> {
                            CollectStatisticsResult collectStatisticsResult = CollectStatisticsResult.fromStatisticsExecutionSummary(summary);
                            DqoJobCompletionStatus jobCompletionStatus = runProfilersJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(runProfilersJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new CollectStatisticsQueueJobResult(pushJobResult.getJobId(), collectStatisticsResult, dqoJobStatus);
                        });

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<CollectStatisticsQueueJobResult> resultWithOnlyJobId = Mono.just(new CollectStatisticsQueueJobResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Starts a new background job that will run selected data statistics collectors for each data stream separately. Uses the default data group on each table.
     * @param statisticsCollectorSearchFilters Data statistics collectors filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/collectstatistics/withgrouping", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "collectStatisticsOnDataGroups",
            notes = "Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping",
            response = CollectStatisticsQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data statistics collection was added to the queue", response = CollectStatisticsQueueJobResult.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<CollectStatisticsQueueJobResult>>> collectStatisticsOnDataGroups(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data statistics collectors filter") @RequestBody StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the statistic collection job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            CollectStatisticsQueueJob runProfilersJob = this.dqoQueueJobFactory.createCollectStatisticsJob();
            StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                    StatisticsCollectorExecutionReportingMode.silent, false);
            CollectStatisticsQueueJobParameters collectStatisticsQueueJobParameters = new CollectStatisticsQueueJobParameters(
                    statisticsCollectorSearchFilters,
                    progressListener,
                    StatisticsDataScope.data_group,
                    false);
            runProfilersJob.setParameters(collectStatisticsQueueJobParameters);
            runProfilersJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runProfilersJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getCollectStatistics();
                CompletableFuture<StatisticsCollectionExecutionSummary> timeoutLimitedFuture = new CompletableFuture<StatisticsCollectionExecutionSummary>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<StatisticsCollectionExecutionSummary> timeoutOrFinishedFuture =
                        (CompletableFuture<StatisticsCollectionExecutionSummary>)(CompletableFuture<?>)
                                CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<CollectStatisticsQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .map(summary -> {
                            CollectStatisticsResult collectStatisticsResult = CollectStatisticsResult.fromStatisticsExecutionSummary(summary);
                            DqoJobCompletionStatus jobCompletionStatus = runProfilersJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(runProfilersJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new CollectStatisticsQueueJobResult(pushJobResult.getJobId(), collectStatisticsResult, dqoJobStatus);
                        });

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<CollectStatisticsQueueJobResult> resultWithOnlyJobId = Mono.just(new CollectStatisticsQueueJobResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Retrieves a list of all queued and recently finished jobs.
     * @return List of all active or recently finished jobs on the queue.
     */
    @GetMapping(value = "/jobs", produces = "application/json")
    @ApiOperation(value = "getAllJobs", notes = "Retrieves a list of all queued and recently finished jobs.",
            response = DqoJobQueueInitialSnapshotModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all queued and finished jobs returned. Call jobchangessince/{changeSequence} to receive incremental changes.",
                    response = DqoJobQueueInitialSnapshotModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DqoJobQueueInitialSnapshotModel>>> getAllJobs(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            Mono<DqoJobQueueInitialSnapshotModel> initialJobList = this.jobQueueMonitoringService.getInitialJobList();
            return new ResponseEntity<>(initialJobList, HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the status of a single job.
     * @return Returns the model of a job.
     */
    @GetMapping(value = "/jobs/{jobId}", produces = "application/json")
    @ApiOperation(value = "getJob", notes = "Retrieves the current status of a single job, identified by a job id.",
            response = DqoJobHistoryEntryModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieves the current status of a single job, identified by a job id.",
                    response = DqoJobHistoryEntryModel.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DqoJobHistoryEntryModel>>> getJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable String jobId) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            DqoQueueJobId dqoQueueJobId = findJobId(jobId);

            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
            if (jobHistoryEntryModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            return new ResponseEntity<>(Mono.just(jobHistoryEntryModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.
     * @return Returns the model of a job. The model contains the result if the job finished before the wait timeout elapsed.
     */
    @GetMapping(value = "/jobs/{jobId}/wait", produces = "application/json")
    @ApiOperation(value = "waitForJob", notes = "Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.",
            response = DqoJobHistoryEntryModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The job status was returned. If the response is returned before the wait timeout, the response will contain information about a finished job. When the wait timeout has elapsed, the job status could be still queued or running.",
                    response = DqoJobHistoryEntryModel.class),
            @ApiResponse(code = 404, message = "The job was not found or it has finished and was already been removed from the job history store.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DqoJobHistoryEntryModel>>> waitForJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable String jobId,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            DqoQueueJobId dqoQueueJobId = findJobId(jobId);

            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
            if (jobHistoryEntryModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            DqoQueueJob<?> job = jobHistoryEntryModel.getJobQueueEntry().getJob();
            CompletableFuture<?> jobFinishedFuture = job.getFinishedFuture();
            long defaultWaitTimeout = this.dqoQueueWaitTimeoutsConfigurationProperties.getWaitTimeForJobType(jobHistoryEntryModel.getJobType());

            long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() : defaultWaitTimeout;
            CompletableFuture<?> timeoutLimitedFuture = new CompletableFuture<Void>()
                    .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
            CompletableFuture<?> timeoutOrFinishedFuture = CompletableFuture.anyOf(jobFinishedFuture, timeoutLimitedFuture);

            Mono<DqoJobHistoryEntryModel> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                    .map(_none -> {
                        DqoJobHistoryEntryModel mostRecentJobModel = this.jobQueueMonitoringService.getJob(dqoQueueJobId);
                        DqoJobCompletionStatus jobCompletionStatus = job.getCompletionStatus();
                        DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();

                        DqoJobHistoryEntryModel mostRecentStatusModel = mostRecentJobModel.clone();
                        if (mostRecentStatusModel.getStatus() != dqoJobStatus) {
                            mostRecentStatusModel.setStatus(dqoJobStatus);
                            mostRecentStatusModel.setStatusChangedAt(Instant.now());
                            if (dqoJobStatus == DqoJobStatus.failed && job.getJobExecutionException() != null) {
                                mostRecentStatusModel.setErrorMessage(job.getJobExecutionException().getMessage());
                            }
                        }

                        return mostRecentStatusModel;
                    });

            return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.OK); // 200
        }));
    }

    /**
     * Cancels a running job.
     * @param jobId Job id of a job to cancel.
     * @return Empty response.
     */
    @DeleteMapping(value = "/jobs/{jobId}", produces = "application/json")
    @ApiOperation(value = "cancelJob", notes = "Cancels a running job", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Job was cancelled", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> cancelJob(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Job id") @PathVariable String jobId) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            DqoQueueJobId dqoQueueJobId = findJobId(jobId);
            this.dqoJobQueue.cancelJob(dqoQueueJobId);
            this.parentDqoJobQueue.cancelJob(dqoQueueJobId);  // we don't know on which queue the job is running, but it cannot run on both and it is safe to cancel a missing job, so we cancel on both the queues

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Retrieves an incremental list of job changes (new jobs or job status changes).
     * @return List of jobs that have changed since the given sequence number.
     */
    @GetMapping(value = "/jobchangessince/{sequenceNumber}", produces = "application/json")
    @ApiOperation(value = "getJobChangesSince", notes = "Retrieves an incremental list of job changes (new jobs or job status changes)",
            response = DqoJobQueueIncrementalSnapshotModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all queued and finished jobs returned. Call jobchangessince/{sequenceNumber} to receive incremental changes.",
                    response = DqoJobQueueIncrementalSnapshotModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DqoJobQueueIncrementalSnapshotModel>>> getJobChangesSince(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Change sequence number to get job changes after that sequence") @PathVariable long sequenceNumber) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            try {
                Mono<DqoJobQueueIncrementalSnapshotModel> incrementalJobChanges = this.jobQueueMonitoringService.getIncrementalJobChanges(
                        sequenceNumber, this.dqoQueueConfigurationProperties.getGetJobChangesSinceWaitSeconds(), TimeUnit.SECONDS);
                Mono<DqoJobQueueIncrementalSnapshotModel> returnEmptyWhenError = incrementalJobChanges.doOnError(
                        error -> Mono.just(new DqoJobQueueIncrementalSnapshotModel(
                                new ArrayList<>(), this.synchronizationStatusTracker.getCurrentSynchronizationStatus(principal.getDataDomainIdentity().getDataDomainFolder()), sequenceNumber)));
                return new ResponseEntity<>(returnEmptyWhenError, HttpStatus.OK); // 200
            }
            catch (Exception ex) {
                log.error("Failed to retrieve recent jobs, error: " + ex.getMessage(), ex);
                return new ResponseEntity<>(Mono.just(new DqoJobQueueIncrementalSnapshotModel(
                        new ArrayList<>(), this.synchronizationStatusTracker.getCurrentSynchronizationStatus(principal.getDataDomainIdentity().getDataDomainFolder()), sequenceNumber)), HttpStatus.OK);
            }
        }));
    }

    /**
     * Starts a new background job that will import selected tables.
     * @param importParameters Import tables job parameters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/importtables", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "importTables", notes = "Starts a new background job that will import selected tables.", response = ImportTablesQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will import selected tables was added to the queue", response = ImportTablesQueueJobResult.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<ImportTablesQueueJobResult>>> importTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Import tables job parameters")
            @RequestBody ImportTablesQueueJobParameters importParameters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the import tables job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the import tables job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            ImportTablesQueueJob importTablesJob = this.dqoQueueJobFactory.createImportTablesJob();
            importTablesJob.setImportParameters(importParameters);
            importTablesJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<ImportTablesResult> pushJobResult = this.dqoJobQueue.pushJob(importTablesJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getImportTables();
                CompletableFuture<ImportTablesResult> timeoutLimitedFuture = new CompletableFuture<ImportTablesResult>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<ImportTablesResult> timeoutOrFinishedFuture =
                        (CompletableFuture<ImportTablesResult>)(CompletableFuture<?>)
                                CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<ImportTablesQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .map(importTablesResult -> {
                            DqoJobCompletionStatus jobCompletionStatus = importTablesJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(importTablesJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new ImportTablesQueueJobResult(pushJobResult.getJobId(), importTablesResult, dqoJobStatus);
                        });

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<ImportTablesQueueJobResult> resultWithOnlyJobId = Mono.just(new ImportTablesQueueJobResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Starts a new background job that will delete specified data from the .data folder.
     * @param deleteStoredDataParameters Delete stored data job parameters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping(value = "/deletestoreddata", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "deleteStoredData", notes = "Starts a new background job that will delete stored data about check results, sensor readouts etc.",
            response = DeleteStoredDataQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will delete stored registry data was added to the queue", response = DeleteStoredDataQueueJobResult.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<DeleteStoredDataQueueJobResult>>> deleteStoredData(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Delete stored data job parameters")
            @RequestBody DeleteStoredDataQueueJobParameters deleteStoredDataParameters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the import tables job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the delete stored data job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            DeleteStoredDataQueueJob deleteStoredDataJob = this.dqoQueueJobFactory.createDeleteStoredDataJob();
            deleteStoredDataJob.setDeletionParameters(deleteStoredDataParameters);
            deleteStoredDataJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<DeleteStoredDataResult> pushJobResult = this.dqoJobQueue.pushJob(deleteStoredDataJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getDeleteStoredData();
                CompletableFuture<DeleteStoredDataResult> timeoutLimitedFuture = new CompletableFuture<DeleteStoredDataResult>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<DeleteStoredDataResult> timeoutOrFinishedFuture =
                        (CompletableFuture<DeleteStoredDataResult>)(CompletableFuture<?>)
                                CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<DeleteStoredDataQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .map(deleteStoredDataResult -> {
                            DqoJobCompletionStatus jobCompletionStatus = deleteStoredDataJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(deleteStoredDataJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new DeleteStoredDataQueueJobResult(pushJobResult.getJobId(), deleteStoredDataResult, dqoJobStatus);
                        });

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<DeleteStoredDataQueueJobResult> resultWithOnlyJobId = Mono.just(new DeleteStoredDataQueueJobResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Starts a file synchronization job that will synchronize files to DQOps Cloud.
     * @param synchronizeFolderParameters Delete stored data job parameters.
     * @return Job summary response with the identity of the started jobs.
     */
    @PostMapping(value = "/synchronize",consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "synchronizeFolders", notes = "Starts multiple file synchronization jobs that will synchronize files from selected DQOps User home folders to the DQOps Cloud. " +
            "The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).",
            response = SynchronizeMultipleFoldersQueueJobResult.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New jobs that will synchronize folders were added to the queue", response = SynchronizeMultipleFoldersQueueJobResult.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<SynchronizeMultipleFoldersQueueJobResult>>> synchronizeFolders(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Selection of folders that should be synchronized to the DQOps Cloud")
            @RequestBody SynchronizeMultipleFoldersDqoQueueJobParameters synchronizeFolderParameters,
            @ApiParam(name = "jobBusinessKey", value = "Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.", required = false)
            @RequestParam(required = false) Optional<String> jobBusinessKey,
            @ApiParam(name = "wait", value = "Wait until the synchronize multiple folders job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)", required = false)
            @RequestParam(required = false) Optional<Boolean> wait,
            @ApiParam(name = "waitTimeout", value = "The wait timeout in seconds, when the wait timeout elapses and the synchronization with the DQOps Cloud is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the 'dqo' cli command documentation).", required = false)
            @RequestParam(required = false) Optional<Integer> waitTimeout) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            SynchronizeMultipleFoldersDqoQueueJob synchronizeMultipleFoldersJob = this.dqoQueueJobFactory.createSynchronizeMultipleFoldersJob();
            synchronizeMultipleFoldersJob.setParameters(synchronizeFolderParameters);
            synchronizeMultipleFoldersJob.setJobBusinessKey(jobBusinessKey.orElse(null));

            PushJobResult<Void> pushJobResult = this.parentDqoJobQueue.pushJob(synchronizeMultipleFoldersJob, principal);

            if (!wait.isPresent() || wait.get()) {
                // wait for the result
                long waitTimeoutSeconds = waitTimeout.isPresent() ? waitTimeout.get() :
                        this.dqoQueueWaitTimeoutsConfigurationProperties.getSynchronizeMultipleFolders();
                CompletableFuture<Void> timeoutLimitedFuture = new CompletableFuture<Void>()
                        .completeOnTimeout(null, waitTimeoutSeconds, TimeUnit.SECONDS);
                CompletableFuture<Void> timeoutOrFinishedFuture =
                        (CompletableFuture<Void>)(CompletableFuture<?>)
                                CompletableFuture.anyOf(pushJobResult.getFinishedFuture(), timeoutLimitedFuture);

                Mono<SynchronizeMultipleFoldersQueueJobResult> monoWithResultAndTimeout = Mono.fromFuture(timeoutOrFinishedFuture)
                        .then(Mono.fromCallable(() -> {
                            DqoJobCompletionStatus jobCompletionStatus = synchronizeMultipleFoldersJob.getCompletionStatus();
                            DqoJobHistoryEntryModel jobHistoryEntryModel = this.jobQueueMonitoringService.getJob(synchronizeMultipleFoldersJob.getJobId());
                            DqoJobStatus dqoJobStatus = jobCompletionStatus != null ? jobCompletionStatus.toJobStatus() : jobHistoryEntryModel.getStatus();
                            return new SynchronizeMultipleFoldersQueueJobResult(pushJobResult.getJobId(), dqoJobStatus);
                        }));

                return new ResponseEntity<>(monoWithResultAndTimeout, HttpStatus.CREATED); // 201
            }

            Mono<SynchronizeMultipleFoldersQueueJobResult> resultWithOnlyJobId = Mono.just(new SynchronizeMultipleFoldersQueueJobResult(pushJobResult.getJobId()));
            return new ResponseEntity<>(resultWithOnlyJobId, HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Retrieves the state of the job scheduler.
     * @return true when the cron scheduler is running, false when it is stopped.
     */
    @GetMapping(value = "/scheduler/isrunning", produces = "application/json")
    @ApiOperation(value = "isCronSchedulerRunning", notes = "Checks if the DQOps internal CRON scheduler is running and processing jobs scheduled using cron expressions.",
            response = Boolean.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler status was checked and returned",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<Boolean>>> isCronSchedulerRunning(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            Boolean started = this.jobSchedulerService.isStarted();
            return new ResponseEntity<>(Mono.just(started), HttpStatus.OK); // 200
        }));
    }

    /**
     * Starts the cron job scheduler (when it is not running).
     * @return Nothing.
     */
    @PostMapping(value = "/scheduler/status/start", produces = "application/json")
    @ApiOperation(value = "startCronScheduler", notes = "Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.",
            response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler was started or was already running",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> startCronScheduler(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (!this.jobSchedulerService.isStarted()) {
                this.jobSchedulerService.start(
                        this.dqoSchedulerConfigurationProperties.getSynchronizationMode(),
                        this.dqoSchedulerConfigurationProperties.getCheckRunMode());
                this.jobSchedulerService.triggerMetadataSynchronization();
            }
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }));
    }

    /**
     * Stops the cron job scheduler (when it is not running).
     * @return Nothing.
     */
    @PostMapping(value = "/scheduler/status/stop", produces = "application/json")
    @ApiOperation(value = "stopCronScheduler", notes = "Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.",
            response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler was stopped or was already not running",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> stopCronScheduler(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (this.jobSchedulerService.isStarted()) {
                this.jobSchedulerService.stop();
            }
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }));
    }
}
