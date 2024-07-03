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
package com.dqops.execution.errorsampling.progress;

import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;

/**
 * Progress event raised after all error samples were collected. Returns the summary.
 */
public class ErrorSamplersExecutionFinishedEvent extends ErrorSamplerExecutionProgressEvent {
    private final ErrorSamplerExecutionSummary collectorsExecutionSummary;

    /**
     * Creates a progress event.
     *
     * @param collectorsExecutionSummary  Summary of all executed error samplers.
     */
    public ErrorSamplersExecutionFinishedEvent(ErrorSamplerExecutionSummary collectorsExecutionSummary) {
        this.collectorsExecutionSummary = collectorsExecutionSummary;
    }

    /**
     * Returns a summary of executed error samples.
     * @return Summary of executed error samples.
     */
    public ErrorSamplerExecutionSummary getCollectorsExecutionSummary() {
        return collectorsExecutionSummary;
    }
}
