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
package ai.dqo.execution.checks.progress;

import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderInput;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderOutput;

/**
 * Progress event raised after checks were executed. Returns the summary.
 */
public class CheckExecutionFinishedEvent extends CheckExecutionProgressEvent {
    private final CheckExecutionSummary checkExecutionSummary;

    /**
     * Creates a progress event.
     *
     * @param checkExecutionSummary  Summary of all executed checks.
     */
    public CheckExecutionFinishedEvent(CheckExecutionSummary checkExecutionSummary) {
        this.checkExecutionSummary = checkExecutionSummary;
    }

    /**
     * Returns a summary of executed checks.
     * @return Summary of executed checks.
     */
    public CheckExecutionSummary getCheckExecutionSummary() {
        return checkExecutionSummary;
    }
}
