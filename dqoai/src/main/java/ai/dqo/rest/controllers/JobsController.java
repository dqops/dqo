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
package ai.dqo.rest.controllers;

import ai.dqo.core.configuration.DqoQueueConfigurationProperties;
import ai.dqo.core.configuration.DqoSchedulerConfigurationProperties;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJob;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobResult;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueIncrementalSnapshotModel;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueInitialSnapshotModel;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.synchronization.jobs.*;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.synchronization.status.SynchronizationStatusTracker;
import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.jobs.RunChecksQueueJob;
import ai.dqo.execution.checks.jobs.RunChecksQueueJobParameters;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerProvider;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.execution.statistics.jobs.CollectStatisticsQueueJob;
import ai.dqo.execution.statistics.jobs.CollectStatisticsQueueJobParameters;
import ai.dqo.execution.statistics.StatisticsCollectionExecutionSummary;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListenerProvider;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Jobs controller that supports publishing new jobs.
 */
@RestController
@RequestMapping("/api/jobs")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Jobs", description = "Jobs management controller that supports starting new jobs, such as running selected data quality checks")
public class JobsController {
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private ParentDqoJobQueue parentDqoJobQueue;
    private JobSchedulerService jobSchedulerService;
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider;
    private final DqoJobQueueMonitoringService jobQueueMonitoringService;
    private final DqoQueueConfigurationProperties queueConfigurationProperties;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;
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
     * @param queueConfigurationProperties Queue configuration parameters.
     * @param dqoSchedulerConfigurationProperties DQO job scheduler configuration properties.
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
                          DqoQueueConfigurationProperties queueConfigurationProperties,
                          DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties,
                          SynchronizationStatusTracker synchronizationStatusTracker) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.parentDqoJobQueue = parentDqoJobQueue;
        this.jobSchedulerService = jobSchedulerService;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.statisticsCollectorExecutionProgressListenerProvider = statisticsCollectorExecutionProgressListenerProvider;
        this.jobQueueMonitoringService = jobQueueMonitoringService;
        this.queueConfigurationProperties = queueConfigurationProperties;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
    }

    /**
     * Starts a new background job that will run selected data quality checks.
     * @param runChecksParameters Run checks parameters with a check filter and an optional time range.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping("/runchecks")
    @ApiOperation(value = "runChecks", notes = "Starts a new background job that will run selected data quality checks", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data quality checks was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> runChecks(
            @ApiParam("Data quality check run configuration (target checks and an optional time range)")
            @RequestBody RunChecksQueueJobParameters runChecksParameters) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                CheckRunReportingMode.silent, false);
        runChecksParameters.setProgressListener(progressListener);

        runChecksJob.setParameters(runChecksParameters);

        PushJobResult<CheckExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runChecksJob);
        return new ResponseEntity<>(Mono.just(pushJobResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Starts a new background job that will run selected data statistics collectors on the whole table.
     * @param statisticsCollectorSearchFilters Data statistics collector filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping("/collectstatistics/table")
    @ApiOperation(value = "collectStatisticsOnTable", notes = "Starts a new background job that will run selected data statistics collectors on a whole table", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data statistics collection was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> collectStatisticsOnTable(
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
     * Starts a new background job that will run selected data statistics collectors for each data stream separately. Uses the default data stream mapping configured on each table.
     * @param statisticsCollectorSearchFilters Data statistics collectors filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping("/collectstatistics/datastreams")
    @ApiOperation(value = "collectStatisticsOnDataStreams", notes = "Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data stream", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data statistics collection was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> collectStatisticsOnDataStreams(
            @ApiParam("Data statistics collectors filter") @RequestBody StatisticsCollectorSearchFilters statisticsCollectorSearchFilters) {
        CollectStatisticsQueueJob runProfilersJob = this.dqoQueueJobFactory.createCollectStatisticsJob();
        StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                StatisticsCollectorExecutionReportingMode.silent, false);
        CollectStatisticsQueueJobParameters collectStatisticsQueueJobParameters = new CollectStatisticsQueueJobParameters(
                statisticsCollectorSearchFilters,
                progressListener,
                StatisticsDataScope.data_stream,
                false);
        runProfilersJob.setParameters(collectStatisticsQueueJobParameters);

        PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.parentDqoJobQueue.pushJob(runProfilersJob);
        return new ResponseEntity<>(Mono.just(pushJobResult.getJobId()), HttpStatus.CREATED); // 201
    }

    /**
     * Retrieves a list of all queued and recently finished jobs.
     * @return List of all active or recently finished jobs on the queue.
     */
    @GetMapping("/jobs")
    @ApiOperation(value = "getAllJobs", notes = "Retrieves a list of all queued and recently finished jobs.",
            response = DqoJobQueueInitialSnapshotModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all queued and finished jobs returned. Call jobchangessince/{changeSequence} to receive incremental changes.",
                    response = DqoJobQueueInitialSnapshotModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoJobQueueInitialSnapshotModel>> getAllJobs() {
        Mono<DqoJobQueueInitialSnapshotModel> initialJobList = this.jobQueueMonitoringService.getInitialJobList();
        return new ResponseEntity<>(initialJobList, HttpStatus.OK); // 200
    }

    /**
     * Cancels a running job.
     * @param jobId Job id of a job to cancel.
     * @return Empty response.
     */
    @DeleteMapping("/jobs/{jobId}")
    @ApiOperation(value = "cancelJob", notes = "Cancels a running job")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Job was cancelled"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> cancelJob(
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
    @GetMapping("/jobchangessince/{sequenceNumber}")
    @ApiOperation(value = "getJobChangesSince", notes = "Retrieves an incremental list of job changes (new jobs or job status changes)",
            response = DqoJobQueueIncrementalSnapshotModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all queued and finished jobs returned. Call jobchangessince/{sequenceNumber} to receive incremental changes.",
                    response = DqoJobQueueIncrementalSnapshotModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoJobQueueIncrementalSnapshotModel>> getJobChangesSince(
            @ApiParam("Change sequence number to get job changes after that sequence") @PathVariable long sequenceNumber) {
        try {
            Mono<DqoJobQueueIncrementalSnapshotModel> incrementalJobChanges = this.jobQueueMonitoringService.getIncrementalJobChanges(
                    sequenceNumber, this.queueConfigurationProperties.getGetJobChangesSinceWaitSeconds(), TimeUnit.SECONDS);
            Mono<DqoJobQueueIncrementalSnapshotModel> returnEmptyWhenError = incrementalJobChanges.doOnError(
                    error -> Mono.just(new DqoJobQueueIncrementalSnapshotModel(
                            new ArrayList<>(), this.synchronizationStatusTracker.getCurrentSynchronizationStatus(), sequenceNumber)));
            return new ResponseEntity<>(returnEmptyWhenError, HttpStatus.OK); // 200
        }
        catch (Exception ex) {
            return new ResponseEntity<>(Mono.just(new DqoJobQueueIncrementalSnapshotModel(
                    new ArrayList<>(), this.synchronizationStatusTracker.getCurrentSynchronizationStatus(), sequenceNumber)), HttpStatus.OK);
        }
    }

    /**
     * Starts a new background job that will import selected tables.
     * @param importParameters Import tables job parameters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping("/importtables")
    @ApiOperation(value = "importTables", notes = "Starts a new background job that will import selected tables.", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will import selected tables was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> importTables(
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
    @PostMapping("/deletestoreddata")
    @ApiOperation(value = "deleteStoredData", notes = "Starts a new background job that will delete stored data about check results, sensor readouts etc.", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will delete stored registry data was added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> deleteStoredData(
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
    @PostMapping("/synchronize")
    @ApiOperation(value = "synchronizeFolders", notes = "Starts multiple file synchronization jobs that will synchronize files from selected DQO User home folders to the DQO Cloud. " +
            "The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New jobs that will synchronize folders were added to the queue", response = DqoQueueJobId.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> synchronizeFolders(
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
    @GetMapping("/scheduler/isrunning")
    @ApiOperation(value = "isCronSchedulerRunning", notes = "Checks if the DQO internal CRON scheduler is running and processing jobs scheduled using cron expressions.",
            response = Boolean.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler status was checked and returned",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<Boolean>> isCronSchedulerRunning() {
        Boolean started = this.jobSchedulerService.isStarted();
        return new ResponseEntity<>(Mono.just(started), HttpStatus.OK); // 200
    }

    /**
     * Starts the cron job scheduler (when it is not running).
     * @return Nothing.
     */
    @PostMapping("/scheduler/status/start")
    @ApiOperation(value = "startCronScheduler", notes = "Starts the job scheduler that runs recurring jobs that are scheduled by assigning cron expressions.",
            response = Void.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler was started or was already running",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> startCronScheduler() {
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
    @PostMapping("/scheduler/status/stop")
    @ApiOperation(value = "stopCronScheduler", notes = "Stops the job scheduler that runs recurring jobs that are scheduled by assigning cron expressions.",
            response = Void.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The cron scheduler was stopped or was already not running",
                    response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> stopCronScheduler() {
        if (this.jobSchedulerService.isStarted()) {
            this.jobSchedulerService.shutdown();
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
    }
}
