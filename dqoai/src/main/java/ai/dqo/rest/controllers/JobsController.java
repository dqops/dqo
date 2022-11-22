package ai.dqo.rest.controllers;

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.execution.checks.RunChecksQueueJob;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerProvider;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    /**
     * Creates a new controller, injecting dependencies.
     * @param dqoQueueJobFactory DQO queue job factory used to create new instances of jobs.
     * @param dqoJobQueue Job queue used to publish or review running jobs.
     * @param checkExecutionProgressListenerProvider Check execution progress listener provider used to create a valid progress listener when starting a "runchecks" job.
     */
    @Autowired
    public JobsController(DqoQueueJobFactory dqoQueueJobFactory,
                          DqoJobQueue dqoJobQueue,
                          CheckExecutionProgressListenerProvider checkExecutionProgressListenerProvider) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.checkExecutionProgressListenerProvider = checkExecutionProgressListenerProvider;
    }

    /**
     * Starts a new background job that will run selected data quality checks.
     * @param checkSearchFilters Data quality checks filters.
     * @return Job summary response with the identity of the started job.
     */
    @PostMapping("/runchecks")
    @ApiOperation(value = "runChecks", notes = "Starts a new background job that will run selected data quality checks")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New job that will run data quality checks was added to the queue"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> runChecks(
            @Parameter(description = "Data quality checks filter") @RequestBody CheckSearchFilters checkSearchFilters) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        CheckExecutionProgressListener progressListener = this.checkExecutionProgressListenerProvider.getProgressListener(
                CheckRunReportingMode.silent, false);
        RunChecksQueueJobParameters runChecksQueueJobParameters = new RunChecksQueueJobParameters(
                checkSearchFilters,
                progressListener,
                false);
        runChecksJob.setParameters(runChecksQueueJobParameters);

        this.dqoJobQueue.pushJob(runChecksJob);

        // TODO: return a job summary with the jobId and a start time

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }
}
