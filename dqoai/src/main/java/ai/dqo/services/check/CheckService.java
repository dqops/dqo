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

import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;

import java.util.List;

/**
 * Service called to run checks or operate on checks.
 */
public interface CheckService {
    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param checkExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @return Check execution summary.
     */
    CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                    TimeWindowFilterParameters timeWindowFilterParameters,
                                    CheckExecutionProgressListener checkExecutionProgressListener,
                                    boolean dummyRun);

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    List<UIAllChecksModel> updateAllChecksPatch(UIAllChecksPatchParameters parameters);
}
