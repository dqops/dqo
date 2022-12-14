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
package ai.dqo.cli.commands.check.impl;

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.RunChecksQueueJob;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.metadata.search.CheckSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service called from the "check" cli commands to run checks or operate on checks.
 */
@Service
public class CheckServiceImpl implements CheckService {
    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;

    /**
     * Default injection constructor.
     * @param dqoQueueJobFactory Job factory used to create a new instance of a job.
     * @param dqoJobQueue DQO job queue to execute the operation.
     */
    @Autowired
    public CheckServiceImpl(DqoQueueJobFactory dqoQueueJobFactory,
                            DqoJobQueue dqoJobQueue) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param checkExecutionProgressListener Progress listener that will report the progress to the console.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @return Check execution summary.
     */
    public CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                           CheckExecutionProgressListener checkExecutionProgressListener,
										   boolean dummyRun) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        RunChecksQueueJobParameters parameters = new RunChecksQueueJobParameters(checkSearchFilters, checkExecutionProgressListener, dummyRun);
        runChecksJob.setParameters(parameters);

        this.dqoJobQueue.pushJob(runChecksJob);
        return runChecksJob.getResult();
    }
}
