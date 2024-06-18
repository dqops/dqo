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

import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Error sampler execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
@Component
public class ErrorSamplerExecutionProgressListenerProviderImpl implements ErrorSamplerExecutionProgressListenerProvider {
    private TerminalWriter terminalWriter;
    private JsonSerializer jsonSerializer;

    /**
     * Creates a profiler factory given the dependencies that are passed to each progress listener.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public ErrorSamplerExecutionProgressListenerProviderImpl(TerminalWriter terminalWriter,
                                                             JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Creates a new instance (prototype) of a progress listener given the reporting mode. The listener is unconfigured.
     * @param reportingMode Reporting mode.
     * @return Unconfigured progress listener.
     */
    protected ErrorSamplerExecutionProgressListener createProgressListenerInstance(ErrorSamplerExecutionReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return new SilentErrorSamplerExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }

    /**
     * Returns an error sampler execution progress listener for the requested reporting level.
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the error sampler to the console.
     * @return Error sampler execution progress listener.
     */
    @Override
    public ErrorSamplerExecutionProgressListener getProgressListener(ErrorSamplerExecutionReportingMode reportingMode,
                                                                     boolean writeSummaryToConsole) {
        ErrorSamplerExecutionProgressListener progressListenerInstance = this.createProgressListenerInstance(reportingMode);
        progressListenerInstance.setShowSummary(writeSummaryToConsole);
        return progressListenerInstance;
    }
}
