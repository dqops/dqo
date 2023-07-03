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
package com.dqops.execution.checks.progress;

import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Returns a correct implementation of the check run progress listener that prints the progress to the screen.
 */
@Component
public class CheckExecutionProgressListenerProviderImpl implements CheckExecutionProgressListenerProvider {
    private TerminalWriter terminalWriter;
    private JsonSerializer jsonSerializer;

    /**
     * Creates a new instance of a listener provider.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public CheckExecutionProgressListenerProviderImpl(TerminalWriter terminalWriter,
                                                      JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Creates a new instance (prototype) of a progress listener given the reporting mode. The listener is unconfigured.
     * @param reportingMode Reporting mode.
     * @return Unconfigured progress listener.
     */
    protected CheckExecutionProgressListener createProgressListenerInstance(CheckRunReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return new SilentCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            case summary:
                return new SummaryCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            case info:
                return new InfoCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            case debug:
                return new DebugCheckExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }

    /**
     * Returns a check execution progress listener for the requested reporting level.
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the checks to the console.
     * @return Check execution progress listener.
     */
    @Override
    public CheckExecutionProgressListener getProgressListener(CheckRunReportingMode reportingMode, boolean writeSummaryToConsole) {
        CheckExecutionProgressListener progressListenerInstance = this.createProgressListenerInstance(reportingMode);
        progressListenerInstance.setShowSummary(writeSummaryToConsole);
        return progressListenerInstance;
    }
}
