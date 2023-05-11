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
package ai.dqo.execution.statistics.progress;

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Statistics collector execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
@Component
public class StatisticsCollectorExecutionProgressListenerProviderImpl implements StatisticsCollectorExecutionProgressListenerProvider {
    private TerminalWriter terminalWriter;
    private JsonSerializer jsonSerializer;

    /**
     * Creates a profiler factory given the dependencies that are passed to each progress listener.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public StatisticsCollectorExecutionProgressListenerProviderImpl(TerminalWriter terminalWriter,
                                                                    JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Creates a new instance (prototype) of a progress listener given the reporting mode. The listener is unconfigured.
     * @param reportingMode Reporting mode.
     * @return Unconfigured progress listener.
     */
    protected StatisticsCollectorExecutionProgressListener createProgressListenerInstance(StatisticsCollectorExecutionReportingMode reportingMode) {
        switch (reportingMode) {
            case silent:
                return new SilentStatisticsCollectorExecutionProgressListener(this.terminalWriter, this.jsonSerializer);
            default:
                throw new IllegalArgumentException("reportingMode");
        }
    }

    /**
     * Returns a profiler execution progress listener for the requested reporting level.
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the profiler to the console.
     * @return Profiler execution progress listener.
     */
    @Override
    public StatisticsCollectorExecutionProgressListener getProgressListener(StatisticsCollectorExecutionReportingMode reportingMode,
                                                                            boolean writeSummaryToConsole) {
        StatisticsCollectorExecutionProgressListener progressListenerInstance = this.createProgressListenerInstance(reportingMode);
        progressListenerInstance.setShowSummary(writeSummaryToConsole);
        return progressListenerInstance;
    }
}
