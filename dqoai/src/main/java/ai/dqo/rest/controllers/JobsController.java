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
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.DqoQueueJobId;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJob;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJob;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import ai.dqo.core.jobqueue.jobs.table.ImportTablesQueueJobResult;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueIncrementalSnapshotModel;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueInitialSnapshotModel;
import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringService;
import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.RunChecksQueueJob;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerProvider;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.execution.statistics.StatisticsCollectionExecutionSummary;
import ai.dqo.execution.statistics.CollectStatisticsCollectionQueueJob;
import ai.dqo.execution.statistics.RunStatisticsCollectionQueueJobParameters;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListenerProvider;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionReportingMode;
import ai.dqo.metadata.search.CheckSearchFilters;
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
    private CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider;
    private StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider;
    private final DqoJobQueueMonitoringService jobQueueMonitoringService;
    private final DqoQueueConfigurationProperties queueConfigurationProperties;

    /**
     * Creates a new controller, injecting dependencies.
     * @param dqoQueueJobFactory DQO queue job factory used to create new instances of jobs.
     * @param dqoJobQueue Job queue used to publish or review running jobs.
     * @param checkExecutionProgressListenerProvider Check execution progress listener provider used to create a valid progress listener when starting a "runchecks" job.
     * @param statisticsCollectorExecutionProgressListenerProvider Profiler execution progress listener provider used to create a valid progress listener when starting a "runprofilers" job.
     * @param jobQueueMonitoringService Job queue monitoring service.
     * @param queueConfigurationProperties Queue configuration parameters.
     */
    @Autowired
    public JobsController(DqoQueueJobFactory dqoQueueJobFactory,
                          DqoJobQueue dqoJobQueue,
                          CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider,
                          StatisticsCollectorExecutionProgressListenerProvider statisticsCollectorExecutionProgressListenerProvider,
                          DqoJobQueueMonitoringService jobQueueMonitoringService,
                          DqoQueueConfigurationProperties queueConfigurationProperties) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
        this.statisticsCollectorExecutionProgressListenerProvider = statisticsCollectorExecutionProgressListenerProvider;
        this.jobQueueMonitoringService = jobQueueMonitoringService;
        this.queueConfigurationProperties = queueConfigurationProperties;
    }

    /**
     * Starts a new background job that will run selected data quality checks.
     * @param checkSearchFilters Data quality checks filters.
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
            @ApiParam("Data quality checks filter") @RequestBody CheckSearchFilters checkSearchFilters) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                CheckRunReportingMode.silent, false);
        RunChecksQueueJobParameters runChecksQueueJobParameters = new RunChecksQueueJobParameters(
                checkSearchFilters,
                progressListener,
                false);
        runChecksJob.setParameters(runChecksQueueJobParameters);

        PushJobResult<CheckExecutionSummary> pushJobResult = this.dqoJobQueue.pushJob(runChecksJob);
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
        CollectStatisticsCollectionQueueJob runProfilersJob = this.dqoQueueJobFactory.createRunProfilersJob();
        StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                StatisticsCollectorExecutionReportingMode.silent, false);
        RunStatisticsCollectionQueueJobParameters runStatisticsCollectionQueueJobParameters = new RunStatisticsCollectionQueueJobParameters(
                statisticsCollectorSearchFilters,
                progressListener,
                StatisticsDataScope.table,
                false);
        runProfilersJob.setParameters(runStatisticsCollectionQueueJobParameters);

        PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.dqoJobQueue.pushJob(runProfilersJob);
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
        CollectStatisticsCollectionQueueJob runProfilersJob = this.dqoQueueJobFactory.createRunProfilersJob();
        StatisticsCollectorExecutionProgressListener progressListener = this.statisticsCollectorExecutionProgressListenerProvider.getProgressListener(
                StatisticsCollectorExecutionReportingMode.silent, false);
        RunStatisticsCollectionQueueJobParameters runStatisticsCollectionQueueJobParameters = new RunStatisticsCollectionQueueJobParameters(
                statisticsCollectorSearchFilters,
                progressListener,
                StatisticsDataScope.data_stream,
                false);
        runProfilersJob.setParameters(runStatisticsCollectionQueueJobParameters);

        PushJobResult<StatisticsCollectionExecutionSummary> pushJobResult = this.dqoJobQueue.pushJob(runProfilersJob);
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
                    error -> Mono.just(new DqoJobQueueIncrementalSnapshotModel(new ArrayList<>(), sequenceNumber)));
            return new ResponseEntity<>(returnEmptyWhenError, HttpStatus.OK); // 200
        }
        catch (Exception ex) {
            return new ResponseEntity<>(Mono.just(new DqoJobQueueIncrementalSnapshotModel(new ArrayList<>(), sequenceNumber)), HttpStatus.OK);
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
}
