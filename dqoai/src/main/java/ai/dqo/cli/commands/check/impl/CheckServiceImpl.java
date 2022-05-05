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

import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextFactory;
import ai.dqo.execution.checks.CheckExecutionService;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.metadata.search.CheckSearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service called from the "check" cli commands to run checks or operate on checks.
 */
@Service
public class CheckServiceImpl implements CheckService {
    private final CheckExecutionService checkExecutionService;
    private final CheckExecutionContextFactory checkExecutionContextFactory;

    /**
     * Default injection constructor.
     * @param checkExecutionService Check execution service.
     * @param checkExecutionContextFactory Check execution context factory.
     */
    @Autowired
    public CheckServiceImpl(CheckExecutionService checkExecutionService,
							CheckExecutionContextFactory checkExecutionContextFactory) {
        this.checkExecutionService = checkExecutionService;
        this.checkExecutionContextFactory = checkExecutionContextFactory;
    }

    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param checkExecutionProgressListener Progress listener that will report the progress to the console.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @return Check execution summary.
     */
    public CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters, CheckExecutionProgressListener checkExecutionProgressListener,
										   boolean dummyRun) {
        CheckExecutionContext checkExecutionContext = this.checkExecutionContextFactory.create();
        return this.checkExecutionService.executeChecks(checkExecutionContext, checkSearchFilters, checkExecutionProgressListener, dummyRun);
    }
}
