/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.services.check;

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.RunChecksQueueJob;
import ai.dqo.execution.checks.RunChecksQueueJobParameters;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.mapping.UIAllChecksPatchFactory;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service called to run checks or operate on checks.
 */
@Service
public class CheckServiceImpl implements CheckService {
    private UIAllChecksPatchFactory uiAllChecksPatchFactory;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;

    /**
     * Default injection constructor.
     * @param uiAllChecksPatchFactory UI all checks patch factory for creating patches to be updated.
     * @param dqoQueueJobFactory Job factory used to create a new instance of a job.
     * @param dqoJobQueue DQO job queue to execute the operation.
     */
    @Autowired
    public CheckServiceImpl(UIAllChecksPatchFactory uiAllChecksPatchFactory,
                            DqoQueueJobFactory dqoQueueJobFactory,
                            DqoJobQueue dqoJobQueue) {
        this.uiAllChecksPatchFactory = uiAllChecksPatchFactory;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param checkExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @return Check execution summary.
     */
    @Override
    public CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                           TimeWindowFilterParameters timeWindowFilterParameters,
                                           CheckExecutionProgressListener checkExecutionProgressListener,
										   boolean dummyRun) {
        RunChecksQueueJob runChecksJob = this.dqoQueueJobFactory.createRunChecksJob();
        RunChecksQueueJobParameters parameters = new RunChecksQueueJobParameters(checkSearchFilters, timeWindowFilterParameters,
                checkExecutionProgressListener, dummyRun);
        runChecksJob.setParameters(parameters);

        this.dqoJobQueue.pushJob(runChecksJob);
        return runChecksJob.getResult();
    }

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    @Override
    public List<UIAllChecksModel> updateAllChecksPatch(UIAllChecksPatchParameters parameters) {
        if (parameters == null
                || parameters.getCheckSearchFilters() == null
                || Strings.isNullOrEmpty(parameters.getCheckSearchFilters().getConnectionName())
                || Strings.isNullOrEmpty(parameters.getCheckSearchFilters().getCheckName())
                || (
                        parameters.getWarningLevelOptions() == null
                                && parameters.getErrorLevelOptions() == null
                                && parameters.getFatalLevelOptions() == null
                )
        ) {
            // Successfully updated nothing.
            return new ArrayList<>();
        }

        List<UIAllChecksModel> patches = this.uiAllChecksPatchFactory.fromCheckSearchFilters(parameters.getCheckSearchFilters());
        // TODO: Dalej bierzemy co nam wyszło w tych patchach i chodzimy po drzewku, poprawiając co trzeba.
    }
}
